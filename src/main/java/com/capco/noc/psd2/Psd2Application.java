package com.capco.noc.psd2;

import com.capco.noc.psd2.domain.Account;
import com.capco.noc.psd2.domain.BankAccount;
import com.capco.noc.psd2.domain.Currency;
import com.capco.noc.psd2.domain.Transaction;
import com.capco.noc.psd2.repository.AccountRepository;
import com.capco.noc.psd2.repository.BankAccountRepository;
import com.capco.noc.psd2.repository.TransactionRepository;
import com.capco.noc.psd2.server.fidor.domain.FidorCustomer;
import com.capco.noc.psd2.server.fidor.domain.FidorAccount;
import com.capco.noc.psd2.server.fidor.domain.FidorTransaction;
import com.capco.noc.psd2.server.fidor.repo.FidorAccountRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorCustomerRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorTransactionRepository;
import com.capco.noc.psd2.server.fidor.service.FidorRestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
public class Psd2Application {

    //Domain repositories
	private static AccountRepository accountRepository;
	private static BankAccountRepository bankAccountRepository;
	private static TransactionRepository transactionRepository;

	//Fidor repositories
    private static FidorAccountRepository fidorAccountRepository;
    private static FidorCustomerRepository fidorCustomerRepository;
    private static FidorTransactionRepository fidorTransactionRepository;

	private final static Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(Psd2Application.class, args);
    }

	/*
	* Initialise embedded H2 database with some initial data
	* */
	@Bean
	CommandLineRunner loadData(AccountRepository accountRepository,
                               BankAccountRepository bankAccountRepository,
                               TransactionRepository transactionRepository,
                               FidorCustomerRepository fidorCustomerRepository,
                               FidorAccountRepository fidorAccountRepository,
                               FidorTransactionRepository fidorTransactionRepository) {

		Psd2Application.accountRepository = accountRepository;
        Psd2Application.bankAccountRepository = bankAccountRepository;
        Psd2Application.transactionRepository = transactionRepository;
        Psd2Application.fidorCustomerRepository = fidorCustomerRepository;
        Psd2Application.fidorAccountRepository = fidorAccountRepository;
        Psd2Application.fidorTransactionRepository = fidorTransactionRepository;

		return (args) -> {
		    initDomainModel();
		    initFidor();
		};
	}

	private static void initDomainModel(){
        Account account = new Account();
        account.setEmail("john.doe@mail.com");
        account.setPassword("12345");
        accountRepository.save(account);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountHolderName("John Doe");
        bankAccount1.setAlias("Checking account");
        bankAccount1.setBank("First International Bank, GmbH.");
        bankAccount1.setIban("AT174481184921362929");
        bankAccount1.setBalance(2389.45);
        bankAccount1.setCurrency(Currency.EUR);
        bankAccount1.setOwnerAccount(account);
        bankAccountRepository.save(bankAccount1);
        bankAccount1.setTransactions(generateRandomTransactions(20, bankAccount1));

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountHolderName("John Doe");
        bankAccount2.setAlias("Checking account 2");
        bankAccount2.setBank("BBVA");
        bankAccount2.setIban("ES174481181234362929");
        bankAccount2.setBalance(1238.45);
        bankAccount2.setCurrency(Currency.EUR);
        bankAccount2.setOwnerAccount(account);
        bankAccountRepository.save(bankAccount2);
        bankAccount2.setTransactions(generateRandomTransactions(15, bankAccount2));

        BankAccount bankAccount3 = new BankAccount();
        bankAccount3.setAccountHolderName("John Doe");
        bankAccount3.setAlias("Checking account");
        bankAccount3.setBank("Deutsche Bank, GmbH.");
        bankAccount3.setIban("DE1744567894921362929");
        bankAccount3.setBalance(189.43);
        bankAccount3.setCurrency(Currency.EUR);
        bankAccount3.setOwnerAccount(account);
        bankAccountRepository.save(bankAccount3);
        bankAccount3.setTransactions(generateRandomTransactions(25, bankAccount3));

        account.setBankAccounts(new ArrayList<BankAccount>(){{
            add(bankAccount1);
            add(bankAccount2);
            add(bankAccount3);
        }});

        bankAccountRepository.save(bankAccount1);
        bankAccountRepository.save(bankAccount2);
        bankAccountRepository.save(bankAccount3);
        accountRepository.save(account);
    }

    private static void initFidor(){
        FidorCustomer fidorCustomer = new FidorCustomer();
        fidorCustomer.setEmail("john.doe@mail.com");
        fidorCustomer.setCreatedAt(generateRandomDate());
        fidorCustomer.setLastSignInAt(generateRandomDate());
        fidorCustomer.setUpdatedAt(generateRandomDate());
        fidorCustomer.setCity("Bratislava");
        fidorCustomer.setCountry("Slovakia");
        fidorCustomer.setMobile("+421123456789");
        fidorCustomer.setPostalCode("882109");
        fidorCustomer.setStreet("Karadzicova");
        fidorCustomer.setStreetNumber("8/A");
        fidorCustomer.setBirthday(generateRandomDate());
        fidorCustomer.setFirstName("John");
        fidorCustomer.setLastName("Doe");
        fidorCustomer.setNick("johnie");
        fidorCustomer.setTitle("Herr");
        fidorCustomer.setGender("m");
        fidorCustomer.setVerified(true);
        fidorCustomerRepository.save(fidorCustomer);

        FidorAccount fidorAccount = new FidorAccount();
        fidorAccount.setAccountNumber("9510260101");
        fidorAccount.setIban("DE32700222009510260101");
        fidorAccount.setBalance(250025);
        fidorAccount.setBalanceAvailable(250025);
        fidorAccount.setOverdraft(0);
        fidorAccount.setPreAuthorizedAmount(0);
        fidorAccount.setCurrency("EUR");
        fidorAccount.setCreatedAt(generateRandomDate());
        fidorAccount.setUpdatedAt(generateRandomDate());
        fidorAccount.setDebitNoteEnabled(true);
        fidorAccount.setLocked(false);
        fidorAccount.setTrusted(true);
        fidorAccountRepository.save(fidorAccount);

        fidorCustomer.setFidorAccounts(new ArrayList<FidorAccount>(){{add(fidorAccount);}});
        fidorAccount.setFidorCustomers(new ArrayList<FidorCustomer>(){{add(fidorCustomer);}});
        fidorCustomerRepository.save(fidorCustomer);
        fidorAccountRepository.save(fidorAccount);

        generateRandomFidorTransactions(30, fidorAccount.getId());
    }

    private static void generateRandomFidorTransactions(int num, String accountId){
        for(int i = 0; i < num; i++){
            FidorTransaction fidorTransaction = new FidorTransaction();
            fidorTransaction.setAccountId(accountId);
            fidorTransaction.setTransactionType("sepa_core_direct_debit");
            fidorTransaction.setSubject("Subject " + i);
            fidorTransaction.setAmount(5 + (500 - 5) * random.nextInt());
            fidorTransaction.setCurrency("EUR");
            fidorTransaction.setBookingCode(Integer.toString(i));
            fidorTransaction.setBookingDate(generateRandomDate());
            fidorTransaction.setValueDate(generateRandomDate());
            fidorTransaction.setCreatedAt(generateRandomDate());
            fidorTransaction.setUpdatedAt(generateRandomDate());

            FidorTransaction.TransactionTypeDetails details =
                    new FidorTransaction.TransactionTypeDetails();
            details.setSepaCreditTransferId("38940180");
            details.setRemoteName("API School");
            details.setRemoteIban("DE38520604100008321014");
            details.setRemoteBic("GENODDF2EJ1");
            fidorTransaction.setTransactionTypeDetails(details);
            fidorTransactionRepository.save(fidorTransaction);
        }
    }

	private static List<Transaction> generateRandomTransactions(int num, BankAccount bankAccount){
		List<Transaction> transactionList = new ArrayList<>();

		for(int i = 0; i < num; i++){
			Transaction transaction = new Transaction();
			transaction.setTransactionId("transaction-id-25lkUia-" + bankAccount.getIban().substring(0,5) + i);
			transaction.setAmount(10.0 + (500.0 - 10.0) * random.nextDouble());
			transaction.setDate(generateRandomDate());
			transaction.setCounterParty("counterParty-" + i);
			transaction.setDescription("transaction-description-" + i);
			transaction.setBankAccount(bankAccount);
            transactionRepository.save(transaction);
			transactionList.add(transaction);
		}

		return transactionList;
	}

	private static long generateRandomDate(){
		long offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2017-10-10 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long)(Math.random() * diff)).getTime();
	}
}
