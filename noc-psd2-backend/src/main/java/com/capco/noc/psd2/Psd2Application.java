package com.capco.noc.psd2;

import com.capco.noc.psd2.domain.Account;
import com.capco.noc.psd2.domain.BankAccount;
import com.capco.noc.psd2.domain.Transaction;
import com.capco.noc.psd2.repository.AccountRepository;
import com.capco.noc.psd2.repository.BankAccountRepository;
import com.capco.noc.psd2.repository.TransactionRepository;
import com.capco.noc.psd2.server.bbva.domain.BbvaAccount;
import com.capco.noc.psd2.server.bbva.domain.BbvaTransaction;
import com.capco.noc.psd2.server.bbva.domain.BbvaUser;
import com.capco.noc.psd2.server.bbva.repo.BbvaAccountRepository;
import com.capco.noc.psd2.server.bbva.repo.BbvaTransactionRepository;
import com.capco.noc.psd2.server.bbva.repo.BbvaUserRepository;
import com.capco.noc.psd2.server.erste.domain.ErsteAccount;
import com.capco.noc.psd2.server.erste.domain.ErsteAccountBalance;
import com.capco.noc.psd2.server.erste.domain.ErsteTransaction;
import com.capco.noc.psd2.server.erste.repo.ErsteAccountRepository;
import com.capco.noc.psd2.server.erste.repo.ErsteTransactionRepository;
import com.capco.noc.psd2.server.fidor.domain.FidorCustomer;
import com.capco.noc.psd2.server.fidor.domain.FidorAccount;
import com.capco.noc.psd2.server.fidor.domain.FidorTransaction;
import com.capco.noc.psd2.server.fidor.repo.FidorAccountRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorCustomerRepository;
import com.capco.noc.psd2.server.fidor.repo.FidorTransactionRepository;
import com.capco.noc.psd2.service.client.BbvaRestClient;
import com.capco.noc.psd2.service.client.FidorRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
public class Psd2Application {

    private static final String USER_NAME = "johndoe";

    //Domain repositories
	private static AccountRepository accountRepository;
	private static BankAccountRepository bankAccountRepository;
	private static TransactionRepository transactionRepository;

	//Fidor repositories
    private static FidorAccountRepository fidorAccountRepository;
    private static FidorCustomerRepository fidorCustomerRepository;
    private static FidorTransactionRepository fidorTransactionRepository;

    //Bbva repositories
    private static BbvaUserRepository bbvaUserRepository;
    private static BbvaAccountRepository bbvaAccountRepository;
    private static BbvaTransactionRepository bbvaTransactionRepository;

    //Erste repositories
    private static ErsteAccountRepository ersteAccountRepository;
    private static ErsteTransactionRepository ersteTransactionRepository;

    //Rest clients
    private static FidorRestClient fidorRestClient;
    private static BbvaRestClient bbvaRestClient;

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
                               FidorTransactionRepository fidorTransactionRepository,
                               BbvaUserRepository bbvaUserRepository,
                               BbvaAccountRepository bbvaAccountRepository,
                               BbvaTransactionRepository bbvaTransactionRepository,
                               ErsteAccountRepository ersteAccountRepository,
                               ErsteTransactionRepository ersteTransactionRepository,
                               FidorRestClient fidorRestClient,
                               BbvaRestClient bbvaRestClient) {

		Psd2Application.accountRepository = accountRepository;
        Psd2Application.bankAccountRepository = bankAccountRepository;
        Psd2Application.transactionRepository = transactionRepository;
        Psd2Application.fidorCustomerRepository = fidorCustomerRepository;
        Psd2Application.fidorAccountRepository = fidorAccountRepository;
        Psd2Application.fidorTransactionRepository = fidorTransactionRepository;
        Psd2Application.bbvaUserRepository = bbvaUserRepository;
        Psd2Application.bbvaAccountRepository = bbvaAccountRepository;
        Psd2Application.bbvaTransactionRepository = bbvaTransactionRepository;
        Psd2Application.ersteAccountRepository = ersteAccountRepository;
        Psd2Application.ersteTransactionRepository = ersteTransactionRepository;

        Psd2Application.fidorRestClient = fidorRestClient;
        Psd2Application.bbvaRestClient = bbvaRestClient;

		return (args) -> {
		    initDomainModel();
		    initFidor();
		    initBbva();
		    initErste();
		    retrieveAccounts();
		};
	}
	
	private static void retrieveAccounts(){
	    Account ownerAccount = accountRepository.findByUsername(USER_NAME);

        //Retrieve and persist Fidor accounts
        List<BankAccount> fidorAccounts = fidorRestClient.getAccounts(USER_NAME);
        for(BankAccount bankAccount: fidorAccounts){
            bankAccount.setOwnerAccount(ownerAccount);
            bankAccountRepository.save(bankAccount);

            List<Transaction> transactions = fidorRestClient.getAccountTransactions(bankAccount.getIban());
            for(Transaction transaction: transactions){
                transaction.setBankAccount(bankAccount);
                transactionRepository.save(transaction);
            }
            bankAccount.setTransactions(transactions);
            bankAccountRepository.save(bankAccount);
        }

        ownerAccount.getBankAccounts().addAll(fidorAccounts);
        accountRepository.save(ownerAccount);

        //Retrieve and persist BBVA accounts
        List<BankAccount> bbvaAccounts = bbvaRestClient.getUserAccounts(USER_NAME);
        for(BankAccount bankAccount: bbvaAccounts){
            bankAccount.setOwnerAccount(ownerAccount);
            bankAccountRepository.save(bankAccount);

            List<Transaction> transactions = bbvaRestClient.getUserAccountTransactions(USER_NAME, bankAccount.getExternalAccountId());
            for(Transaction transaction: transactions){
                transaction.setBankAccount(bankAccount);
                transactionRepository.save(transaction);
            }
            bankAccount.setTransactions(transactions);
            bankAccountRepository.save(bankAccount);
        }

        ownerAccount.getBankAccounts().addAll(bbvaAccounts);
        accountRepository.save(ownerAccount);

        System.out.println();
    }

	private static void initDomainModel(){
        Account account = new Account();
        account.setUsername(USER_NAME);
        account.setPassword("12345");
        accountRepository.save(account);
    }

    private static void initFidor(){
        FidorCustomer fidorCustomer = new FidorCustomer();
        fidorCustomer.setCustomerName(USER_NAME);
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

    private static void initBbva(){
        BbvaUser bbvaUser = new BbvaUser();
        bbvaUser.setUserId(USER_NAME);
        bbvaUser.setFirstName("John");
        bbvaUser.setSurname("Doe");
        bbvaUser.setSecondSurname("Juanito");
        bbvaUser.setSex("MALE");
        bbvaUser.setBirthdate(generateRandomDate());
        bbvaUser.setEmail("john.doe@mail.com");
        bbvaUser.setPhone("+421123456789");

        BbvaUser.IdentityDocument identityDocument = new BbvaUser.IdentityDocument();
        identityDocument.setNumber("00000000-A");
        identityDocument.setType(BbvaUser.IdentityDocument.IdentityDocumentType.PASSPORT);
        bbvaUser.setIdentityDocument(identityDocument);

        BbvaUser.Address address = new BbvaUser.Address();
        address.setAddressId("000111");
        address.setAdditionalData("2 I");
        address.setDoor("E");
        address.setFloor("7");
        address.setCity("Malaga");
        address.setStreetName("Calle San Miguel");
        address.setStreetNumber("0024");
        address.setStreetType("CL");
        address.setZipcode("29001");
        address.setCountry("Spain");
        address.setAddressType("fiscal");
        bbvaUser.setAddress(address);
        bbvaUserRepository.save(bbvaUser);

        //Generate First Account
        BbvaAccount bbvaAccountFirst = new BbvaAccount();
        bbvaAccountFirst.setUserId(USER_NAME);
        bbvaAccountFirst.setAlias("My Awesome Spanish Account");
        bbvaAccountFirst.setType(BbvaAccount.Type.CREDIT);
        bbvaAccountFirst.setNumber("1785");
        bbvaAccountFirst.setBalance(2685.2);
        bbvaAccountFirst.setCurrency("EUR");

        BbvaAccount.Formats formats = new BbvaAccount.Formats();
        formats.setIban("ES6968867819113534431785");
        formats.setCcc("68867819113534431785");
        bbvaAccountFirst.setFormats(formats);
        bbvaAccountRepository.save(bbvaAccountFirst);

        generateRandomBbvaTransactions(20, bbvaAccountFirst.getId());

        //Generate secound account
        BbvaAccount bbvaAccountSecond = new BbvaAccount();
        bbvaAccountSecond.setUserId(USER_NAME);
        bbvaAccountSecond.setAlias("My Spanish Consumer Loan Account");
        bbvaAccountSecond.setType(BbvaAccount.Type.CHECKING);
        bbvaAccountSecond.setNumber("1175");
        bbvaAccountSecond.setBalance(4781.5);
        bbvaAccountSecond.setCurrency("EUR");

        BbvaAccount.Formats formatsSecond = new BbvaAccount.Formats();
        formatsSecond.setIban("ES6968875699113531111175");
        formatsSecond.setCcc("68875699113531111175");
        bbvaAccountSecond.setFormats(formatsSecond);
        bbvaAccountRepository.save(bbvaAccountSecond);

        generateRandomBbvaTransactions(15, bbvaAccountSecond.getId());
    }

    private static void initErste(){
        ErsteAccount ersteAccount = new ErsteAccount();
        ersteAccount.setUserId(USER_NAME);
        ersteAccount.setAlias("moj osobny ucet s kasickou");
        ersteAccount.setType("CURRENT");
        ersteAccount.setSubtype("CURRENT_ACCOUNT");
        ersteAccount.setProductI18N("Osobní účet ČS II");
        ersteAccount.setProduct("54");

        ErsteAccount.AccountNumber accountNumber = new ErsteAccount.AccountNumber();
        accountNumber.setNumber("1019382023");
        accountNumber.setBankCode("0800");
        accountNumber.setCountryCode("CZ");
        accountNumber.setCzIban("CZ0708000000001019382023");
        accountNumber.setCzBic("GIBACZPX");
        ersteAccount.setAccountNumber(accountNumber);

        ErsteAccountBalance balance = new ErsteAccountBalance();
        balance.setValue(8965200);
        balance.setPrecision((byte)2);
        balance.setCurrency("CZK");
        ersteAccount.setBalance(balance);

        ErsteAccountBalance disposable = new ErsteAccountBalance();
        disposable.setValue(0);
        disposable.setPrecision((byte)2);
        disposable.setCurrency("CZK");
        ersteAccount.setBalance(disposable);

        ErsteAccountBalance overdraft = new ErsteAccountBalance();
        overdraft.setValue(0);
        overdraft.setPrecision((byte)2);
        overdraft.setCurrency("CZK");
        ersteAccount.setBalance(overdraft);
        ersteAccount.setOverdraftDueDate(generateRandomDate());
        ersteAccountRepository.save(ersteAccount);

        generateRandomErsteTransactions(35, ersteAccount.getId());
    }

    private static void generateRandomFidorTransactions(int num, String accountId){
        for(int i = 0; i < num; i++){
            FidorTransaction fidorTransaction = new FidorTransaction();
            fidorTransaction.setAccountId(accountId);
            fidorTransaction.setTransactionType("sepa_core_direct_debit");
            fidorTransaction.setSubject("Subject " + i);
            fidorTransaction.setAmount(5 + (500 - 5) * random.nextInt(100));
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

    private static void generateRandomBbvaTransactions(int num, String accountId){
        for(int i = 0; i < num; i++){
            BbvaTransaction bbvaTransaction = new BbvaTransaction();
            bbvaTransaction.setAccountId(accountId);
            bbvaTransaction.setOperationDate(generateRandomDate());
            bbvaTransaction.setValueDate(generateRandomDate());
            bbvaTransaction.setAmount(15.0 + (250.0 - 15.0) * random.nextDouble());
            bbvaTransaction.setCurrency("EUR");
            bbvaTransaction.setDescription("Transaction-" + i + "-description");

            BbvaTransaction.Category category = new BbvaTransaction.Category();
            category.setCatId("12");
            category.setCatName("PARTY");
            bbvaTransaction.setCategory(category);

            BbvaTransaction.SubCategory subCategory = new BbvaTransaction.SubCategory();
            subCategory.setSubCatId("62");
            subCategory.setSubCatName("SENT TRANSFER");
            bbvaTransaction.setSubCategory(subCategory);

            BbvaTransaction.ClientNote clientNote = new BbvaTransaction.ClientNote();
            clientNote.setClientNoteDate(bbvaTransaction.getOperationDate());
            clientNote.setText("La nota del alquiler de la party");
            bbvaTransaction.setClientNote(clientNote);

            BbvaTransaction.AttachedInfo attachedInfo = new BbvaTransaction.AttachedInfo();
            attachedInfo.setDate(bbvaTransaction.getOperationDate());
            attachedInfo.setSize(1234);
            attachedInfo.setName("70"+i+".png");
            attachedInfo.setType("image/png");
            bbvaTransaction.setAttachedInfo(attachedInfo);

            bbvaTransactionRepository.save(bbvaTransaction);
        }
    }

    private static void generateRandomErsteTransactions(int num, String accountId){
        for(int i = 0; i < num; i++){
            ErsteTransaction ersteTransaction = new ErsteTransaction();
            ersteTransaction.setAccountId(accountId);
            ersteTransaction.setBookingDate(generateRandomDate());
            ersteTransaction.setCardNumber("0");
            ersteTransaction.setConstantSymbol("0558");
            ersteTransaction.setDescription("domácí platba");
            ersteTransaction.setDescriptionEditable(false);
            ersteTransaction.setPayeeNote("note for payee");
            ersteTransaction.setPayerNote("note for payer");
            ersteTransaction.setSpecificSymbol("55");
            ersteTransaction.setTransactionType("54");
            ersteTransaction.setValuationDate(generateRandomDate());
            ersteTransaction.setVariableSymbol("0000000009");


            int value = 1000 + (500000 - 1000) * random.nextInt();
            ErsteAccountBalance amount = new ErsteAccountBalance();
            amount.setValue(value);
            amount.setPrecision((byte)2);
            amount.setCurrency("CZK");
            ersteTransaction.setAmount(amount);

            ErsteAccountBalance amountSender = new ErsteAccountBalance();
            amountSender.setValue(value);
            amountSender.setPrecision((byte)2);
            amountSender.setCurrency("CZK");
            ersteTransaction.setAmountSender(amountSender);

            ErsteTransaction.AccountParty accountParty = new ErsteTransaction.AccountParty();
            accountParty.setAccountNumber("2812275553");
            accountParty.setAccountPrefix("0");
            accountParty.setBankCode("0800");
            accountParty.setBic("GIBACZPX");
            accountParty.setIban("CZ2908000000002812275553");
            accountParty.setPartyInfo("Peter Maly");
            accountParty.setPartyDescription("2812275553/0800");
            ersteTransaction.setAccountParty(accountParty);

            ersteTransactionRepository.save(ersteTransaction);
        }
    }

	private static long generateRandomDate(){
		long offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2017-10-10 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long)(Math.random() * diff)).getTime();
	}
}
