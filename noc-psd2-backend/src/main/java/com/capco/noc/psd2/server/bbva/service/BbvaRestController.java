package com.capco.noc.psd2.server.bbva.service;

import com.capco.noc.psd2.server.RestResponseWrapper;
import com.capco.noc.psd2.server.bbva.domain.BbvaAccount;
import com.capco.noc.psd2.server.bbva.domain.BbvaTransaction;
import com.capco.noc.psd2.server.bbva.domain.BbvaUser;
import com.capco.noc.psd2.server.bbva.repo.BbvaAccountRepository;
import com.capco.noc.psd2.server.bbva.repo.BbvaTransactionRepository;
import com.capco.noc.psd2.server.bbva.repo.BbvaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bbva")
public class BbvaRestController {

    private final BbvaUserRepository userRepository;
    private final BbvaAccountRepository accountRepository;
    private final BbvaTransactionRepository transactionRepository;

    @Autowired
    BbvaRestController(BbvaUserRepository userRepository,
                       BbvaAccountRepository accountRepository,
                       BbvaTransactionRepository transactionRepository){

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/me-full")
    ResponseEntity<RestResponseWrapper<BbvaUser>> getUserFull(@PathVariable String userId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "Invalid user ID")), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.OK.value(), "OK"), user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/accounts")
    ResponseEntity<RestResponseWrapper<List<BbvaAccount>>> getUserAccounts(@PathVariable String userId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "Invalid user ID")), HttpStatus.BAD_REQUEST);
        }

        List<BbvaAccount> accounts = accountRepository.findByUserId(userId);
        if(accounts == null || accounts.isEmpty()){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "No accounts for provided account ID")), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.OK.value(), "OK"), accounts), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/account/{accountId}")
    ResponseEntity<RestResponseWrapper<BbvaAccount>> getUserAccount(@PathVariable String userId, @PathVariable String accountId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "Invalid user ID")), HttpStatus.BAD_REQUEST);
        }

        BbvaAccount account = accountRepository.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "No such account")), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.OK.value(), "OK"), account), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/account/ib/{iban}")
    ResponseEntity<RestResponseWrapper<BbvaAccount>> getUserAccountByIban(@PathVariable String userId, @PathVariable String iban){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "Invalid user ID")), HttpStatus.BAD_REQUEST);
        }

        BbvaAccount account = accountRepository.findByFormatsIban(iban);
        if(account == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "No account for provided IBAN")), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.OK.value(), "OK"), account), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/account/{accountId}/transactions")
    ResponseEntity<RestResponseWrapper<List<BbvaTransaction>>> getUserAccountTransactions(@PathVariable String userId, @PathVariable String accountId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "Invalid user ID")), HttpStatus.BAD_REQUEST);
        }

        BbvaAccount account = accountRepository.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "No such account")), HttpStatus.BAD_REQUEST);
        }

        List<BbvaTransaction> transactions = transactionRepository.findByAccountId(accountId);
        if(transactions == null || transactions.isEmpty()){
            return new ResponseEntity<>(new RestResponseWrapper<>(
                    new RestResponseWrapper.Result(HttpStatus.BAD_REQUEST.value(), "No transactions found for account with provided ID")), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(new RestResponseWrapper<>(
                new RestResponseWrapper.Result(HttpStatus.OK.value(), "OK"), transactions), HttpStatus.OK);
    }
}
