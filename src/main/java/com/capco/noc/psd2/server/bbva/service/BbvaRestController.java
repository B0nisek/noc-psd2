package com.capco.noc.psd2.server.bbva.service;

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
    ResponseEntity<BbvaUser> getUserFull(@PathVariable String userId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/accounts")
    ResponseEntity<List<BbvaAccount>> getUserAccounts(@PathVariable String userId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<BbvaAccount> accounts = accountRepository.findByUserId(userId);
        if(accounts == null || accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/account/{accountId}")
    ResponseEntity<BbvaAccount> getUserAccount(@PathVariable String userId, @PathVariable String accountId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BbvaAccount account = accountRepository.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/account/{accountId}/transactions")
    ResponseEntity<List<BbvaTransaction>> getUserAccountTransactions(@PathVariable String userId, @PathVariable String accountId){
        BbvaUser user = userRepository.findByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BbvaAccount account = accountRepository.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<BbvaTransaction> transactions = transactionRepository.findByAccountId(accountId);
        if(transactions == null || transactions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
