package com.capco.noc.psd2.server.fidor.service;

import com.capco.noc.psd2.server.fidor.domain.FidorAccount;
import com.capco.noc.psd2.server.fidor.domain.FidorCustomer;
import com.capco.noc.psd2.server.fidor.domain.FidorTransaction;
import com.capco.noc.psd2.server.fidor.repo.FidorAccountRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorCustomerRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fidor")
public class FidorRestController {

    private final FidorCustomerRepository fidorCustomerRepository;
    private final FidorAccountRepository fidorAccountRepository;
    private final FidorTransactionRepository fidorTransactionRepository;

    @Autowired
    FidorRestController(FidorCustomerRepository fidorCustomerRepository,
                        FidorAccountRepository fidorAccountRepository,
                        FidorTransactionRepository fidorTransactionRepository){

        this.fidorCustomerRepository = fidorCustomerRepository;
        this.fidorAccountRepository = fidorAccountRepository;
        this.fidorTransactionRepository = fidorTransactionRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{name}")
    ResponseEntity<FidorCustomer> getCustomer(@PathVariable String name){
        FidorCustomer customer = fidorCustomerRepository.findByCustomerName(name);

        if(customer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{name}/accounts")
    ResponseEntity<List<FidorAccount>> getAccounts(@PathVariable String name){
        List<FidorAccount> accounts = fidorAccountRepository.findByFidorCustomersCustomerName(name);

        if(accounts == null || accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/{iban}")
    ResponseEntity<FidorAccount> getAccountByIban(@PathVariable String iban){
        FidorAccount account = fidorAccountRepository.findByIban(iban);

        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/{iban}/transactions")
    ResponseEntity<List<FidorTransaction>> getAccountTransactions(@PathVariable String iban){
        FidorAccount fidorAccount = fidorAccountRepository.findByIban(iban);

        if(fidorAccount == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fidorTransactionRepository.findByAccountId(fidorAccount.getId()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction/{transactionId}")
    ResponseEntity<FidorTransaction> getTransactionById(@PathVariable String transactionId){
        FidorTransaction transaction = fidorTransactionRepository.findOne(transactionId);

        if(transaction == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}