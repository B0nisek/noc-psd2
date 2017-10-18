package com.capco.noc.psd2.server.fidor.service;

import com.capco.noc.psd2.server.fidor.domain.FidorAccount;
import com.capco.noc.psd2.server.fidor.domain.FidorCustomer;
import com.capco.noc.psd2.server.fidor.domain.FidorTransaction;
import com.capco.noc.psd2.server.fidor.repo.FidorAccountRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorCustomerRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String hello(){
        return "Hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{email}")
    FidorCustomer getCustomer(@PathVariable String email){
        return fidorCustomerRepository.findByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{email}/accounts")
    List<FidorAccount> getAccounts(@PathVariable String email){
        return fidorAccountRepository.findByFidorCustomersEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/{iban}")
    List<FidorAccount> getAccountByIban(@PathVariable String iban){
        return fidorAccountRepository.findByIban(iban);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/{iban}/transactions")
    List<FidorTransaction> getAccountTransactions(@PathVariable String iban){
        FidorAccount fidorAccount = fidorAccountRepository.findByIban(iban).get(0);
        return fidorTransactionRepository.findByAccountId(fidorAccount.getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction/{transactionId}")
    FidorTransaction getTransactionById(@PathVariable String transactionId){
        return fidorTransactionRepository.findOne(transactionId);
    }
}