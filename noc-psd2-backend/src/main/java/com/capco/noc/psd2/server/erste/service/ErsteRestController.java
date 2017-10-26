package com.capco.noc.psd2.server.erste.service;

import com.capco.noc.psd2.server.erste.domain.ErsteAccount;
import com.capco.noc.psd2.server.erste.domain.ErsteTransaction;
import com.capco.noc.psd2.server.erste.repo.ErsteAccountRepository;
import com.capco.noc.psd2.server.erste.repo.ErsteTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/erste/netbanking")
public class ErsteRestController {

    private final ErsteAccountRepository ersteAccountRepository;
    private final ErsteTransactionRepository ersteTransactionRepository;

    @Autowired
    ErsteRestController(ErsteAccountRepository ersteAccountRepository,
                        ErsteTransactionRepository ersteTransactionRepository){

        this.ersteAccountRepository = ersteAccountRepository;
        this.ersteTransactionRepository = ersteTransactionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/my/accounts")
    ResponseEntity<List<ErsteAccount>> getUserAccounts(@PathVariable String userId){
        List<ErsteAccount> accounts = ersteAccountRepository.findByUserId(userId);
        if(accounts == null || accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/my/accounts/{accountId}")
    ResponseEntity<ErsteAccount> getUserAccount(@PathVariable String userId, @PathVariable String accountId){
        ErsteAccount account = ersteAccountRepository.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!userId.equals(account.getUserId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/my/accounts/{accountId}/transactions")
    ResponseEntity<List<ErsteTransaction>> getAccountTransactions(@PathVariable String userId, @PathVariable String accountId){
        ErsteAccount account = ersteAccountRepository.findOne(accountId);

        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!userId.equals(account.getUserId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ErsteTransaction> transactions = ersteTransactionRepository.findByAccountId(accountId);
        if(transactions == null || transactions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
