package com.capco.noc.psd2;

import com.capco.noc.psd2.domain.Account;
import com.capco.noc.psd2.domain.BankAccount;
import com.capco.noc.psd2.domain.Currency;
import com.capco.noc.psd2.domain.Transaction;
import com.capco.noc.psd2.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
public class Psd2Application {

	private static AccountRepository accountRepository;

	private final static Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(Psd2Application.class, args);


		System.out.println();
	}

	/*
	* Initialise embedded H2 database with some initial data
	* */
	@Bean
	CommandLineRunner loadData(AccountRepository accountRepository) {

		Psd2Application.accountRepository = accountRepository;

		return (args) -> {
			Account account = new Account();
			account.setUsername("admin");
			account.setPassword("12345");

			BankAccount bankAccount1 = new BankAccount();
			bankAccount1.setAccountHolderName("John Doe");
			bankAccount1.setAlias("Checking account");
			bankAccount1.setBank("First International Bank, GmbH.");
			bankAccount1.setIban("AT174481184921362929");
			bankAccount1.setBalance(2389.45);
			bankAccount1.setCurrency(Currency.EUR);
			bankAccount1.setTransactions(generateRandomTransactions(20, bankAccount1));

			BankAccount bankAccount2 = new BankAccount();
			bankAccount2.setAccountHolderName("John Doe");
			bankAccount2.setAlias("Checking account 2");
			bankAccount2.setBank("BBVA");
			bankAccount2.setIban("ES174481181234362929");
			bankAccount2.setBalance(1238.45);
			bankAccount2.setCurrency(Currency.EUR);
			bankAccount2.setTransactions(generateRandomTransactions(15, bankAccount2));

			BankAccount bankAccount3 = new BankAccount();
			bankAccount3.setAccountHolderName("John Doe");
			bankAccount3.setAlias("Checking account");
			bankAccount3.setBank("Deutsche Bank, GmbH.");
			bankAccount3.setIban("DE1744567894921362929");
			bankAccount3.setBalance(189.43);
			bankAccount3.setCurrency(Currency.EUR);
			bankAccount3.setTransactions(generateRandomTransactions(25, bankAccount3));

			account.setBankAccounts(new ArrayList<BankAccount>(){{
				add(bankAccount1);
				add(bankAccount2);
				add(bankAccount3);
			}});

			accountRepository.save(account);
		};
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
