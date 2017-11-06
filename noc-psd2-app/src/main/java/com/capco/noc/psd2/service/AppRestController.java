package com.capco.noc.psd2.service;

import com.capco.noc.psd2.domain.Account;
import com.capco.noc.psd2.domain.BankAccount;
import com.capco.noc.psd2.domain.Transaction;
import com.capco.noc.psd2.repository.AccountRepository;
import com.capco.noc.psd2.repository.BankAccountRepository;
import com.capco.noc.psd2.repository.TransactionRepository;
import com.capco.noc.psd2.service.client.BbvaRestClient;
import com.capco.noc.psd2.service.client.FidorRestClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/psd")
public class AppRestController {

    private static final String AUTH_TOKEN_COOKIE_PARAM = "auth-token";

    private final AccountRepository accountRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    private final BbvaRestClient bbvaRestClient;
    private final FidorRestClient fidorRestClient;

    private final JsonParser jsonParser;

    //Key - authToken, value - userName
    private static Map<String, String> loggedInUserMap = new HashMap<>();

    @Autowired
    AppRestController(AccountRepository accountRepository, BbvaRestClient bbvaRestClient,
                      FidorRestClient fidorRestClient, BankAccountRepository bankAccountRepository,
                      TransactionRepository transactionRepository){

        this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.bbvaRestClient = bbvaRestClient;
        this.fidorRestClient = fidorRestClient;

        this.jsonParser = new JsonParser();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    ResponseEntity<String> login(@RequestBody String loginInformation){
        JsonElement jsonElement = jsonParser.parse(loginInformation);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        Account account = accountRepository.findByUsername(username);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!password.equals(account.getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String authToken = UUID.randomUUID().toString();
        loggedInUserMap.put(authToken, account.getUsername());

        return new ResponseEntity<>("{\"token\": \""+authToken+"\"}" , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    ResponseEntity<?> logout(@RequestHeader(AUTH_TOKEN_COOKIE_PARAM) String authToken){
        loggedInUserMap.remove(authToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/acc")
    ResponseEntity<Account> getAccount(@RequestHeader(AUTH_TOKEN_COOKIE_PARAM) String authToken){
        if(!loggedInUserMap.containsKey(authToken)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Account account = accountRepository.findByUsername(loggedInUserMap.get(authToken));
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/acc/add")
    ResponseEntity<String> getAccount(@RequestHeader(AUTH_TOKEN_COOKIE_PARAM) String authToken,
                                       @RequestBody String accountInformation){

        if(!loggedInUserMap.containsKey(authToken)){
            return new ResponseEntity<>("{\"message\": \"You need to be logged in to link new bank account.\"}", HttpStatus.UNAUTHORIZED);
        }

        boolean accountFound = false;
        JsonElement jsonElement = jsonParser.parse(accountInformation);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String iban = jsonObject.get("iban").getAsString();
        String alias = jsonObject.get("alias").getAsString();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        Account account = accountRepository.findByUsername(username);
        if(account == null){
            return new ResponseEntity<>("{\"message\": \"Incorrect credentials..\"}", HttpStatus.BAD_REQUEST);
        }

        if(!password.equals(account.getPassword())){
            return new ResponseEntity<>("{\"message\": \"Incorrect credentials..\"}", HttpStatus.UNAUTHORIZED);
        }

        for(BankAccount bankAccount: account.getBankAccounts()){
            if(iban.equals(bankAccount.getIban())){
                return new ResponseEntity<>("{\"message\": \"Account with IBAN: '" + iban + "' already linked.\"}", HttpStatus.BAD_REQUEST);
            }
        }

        //Check Fidor for account with specified IBAN
        BankAccount fidorBankAccount = fidorRestClient.getAccountByIban(iban);
        if(fidorBankAccount != null){
            accountFound = true;
            linkFidorBankAccount(fidorBankAccount, alias, account);
        }

        //Check BBVA for account with specified IBAN
        BankAccount bbvaBankAccount = bbvaRestClient.getUserAccountByIban(username, iban);
        if(bbvaBankAccount != null){
            accountFound = true;
            linkBbvaBankAccount(bbvaBankAccount, alias, account);
        }

        //TODO - check Erste for account with specified IBAN

        if(!accountFound){
            return new ResponseEntity<>("{\"message\": \"Account with IBAN: '" + iban + "' not found in partner banks.\"}", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("{\"message\": \"Account with IBAN: '" + iban + "' linked.\"}", HttpStatus.OK);
    }

    private void linkFidorBankAccount(BankAccount bankAccount, String alias, Account ownerAccount){
        bankAccount.setOwnerAccount(ownerAccount);
        bankAccount.setAlias(alias);
        bankAccountRepository.save(bankAccount);

        //Retrieve and link transactions
        List<Transaction> transactions = fidorRestClient.getAccountTransactions(bankAccount.getIban());
        for(Transaction transaction: transactions){
            transaction.setBankAccount(bankAccount);
            transactionRepository.save(transaction);
        }
        bankAccount.setTransactions(transactions);
        bankAccountRepository.save(bankAccount);

        ownerAccount.getBankAccounts().add(bankAccount);
        accountRepository.save(ownerAccount);
    }

    private void linkBbvaBankAccount(BankAccount bankAccount, String alias, Account ownerAccount){
        bankAccount.setOwnerAccount(ownerAccount);
        bankAccount.setAlias(alias);
        bankAccountRepository.save(bankAccount);

        //Retrieve and link transactions
        List<Transaction> transactions = bbvaRestClient.getUserAccountTransactions(ownerAccount.getUsername(), bankAccount.getExternalAccountId());
        for(Transaction transaction: transactions){
            transaction.setBankAccount(bankAccount);
            transactionRepository.save(transaction);
        }
        bankAccount.setTransactions(transactions);
        bankAccountRepository.save(bankAccount);

        ownerAccount.getBankAccounts().add(bankAccount);
        accountRepository.save(ownerAccount);
    }
}
