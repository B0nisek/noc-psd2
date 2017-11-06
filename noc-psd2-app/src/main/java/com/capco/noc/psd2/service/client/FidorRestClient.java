package com.capco.noc.psd2.service.client;

import com.capco.noc.psd2.domain.BankAccount;
import com.capco.noc.psd2.domain.Currency;
import com.capco.noc.psd2.domain.Transaction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FidorRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FidorRestClient.class);

    private static final String FIDOR_BASE_URL = "http://localhost:8080/fidor";
    private static final String JSON_EL_DATA = "data";
    private static final String JSON_EL_RESULT = "result";
    private static final String JSON_EL_CODE = "code";

    private final RestTemplate restTemplate;
    private final JsonParser jsonParser;

    @Autowired
    FidorRestClient(){
        this.restTemplate = new RestTemplate();
        this.jsonParser = new JsonParser();
    }

    public String getCustomer(String name){
        String response = restTemplate.getForObject(FIDOR_BASE_URL + "/customer/" + name, String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return jsonObject.get(JSON_EL_DATA).toString();
    }

    public List<BankAccount> getAccounts(String name){
        String response = restTemplate.getForObject(FIDOR_BASE_URL + "/customer/" + name + "/accounts", String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        JsonArray accountJsonArray = jsonObject.getAsJsonArray(JSON_EL_DATA);
        List<BankAccount> jsonAccounts = new ArrayList<>();
        for (JsonElement jsonAccount : accountJsonArray) {
            jsonAccounts.add(parseBankAccountFromJson(jsonAccount));
        }

        return jsonAccounts;
    }

    public BankAccount getAccountByIban(String iban){
        String response;
        try {
            response = restTemplate.getForObject(FIDOR_BASE_URL + "/account/" + iban, String.class);
        } catch (Exception e){
            return null;
        }

        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return parseBankAccountFromJson(jsonObject.get(JSON_EL_DATA));
    }

    public List<Transaction> getAccountTransactions(String iban){
        String response = restTemplate.getForObject(FIDOR_BASE_URL + "/account/" + iban + "/transactions", String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        JsonArray transactionsJsonArray = jsonObject.getAsJsonArray(JSON_EL_DATA);
        List<Transaction> transactions = new ArrayList<>();
        for (JsonElement jsonTransaction : transactionsJsonArray) {
            transactions.add(parseTransactionFromJson(jsonTransaction));
        }

        return transactions;
    }

    public Transaction getTransactionById(String transactionId){
        String response = restTemplate.getForObject(FIDOR_BASE_URL + "/transaction/" + transactionId, String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return parseTransactionFromJson(jsonObject.get(JSON_EL_DATA));
    }

    private boolean isResponseOk(JsonObject jsonObject){
        JsonObject result = jsonObject.get(JSON_EL_RESULT).getAsJsonObject();
        String code = result.get(JSON_EL_CODE).getAsString();

        if(Integer.valueOf(200).equals(Integer.parseInt(code))){
            return true;
        } else {
            LOGGER.warn("Request not processed correctly. Response data: ", jsonObject.getAsString());
            return false;
        }
    }

    private BankAccount parseBankAccountFromJson(JsonElement accountElement){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setExternalAccountId(accountElement.getAsJsonObject().get("id").getAsString());
        bankAccount.setAlias("Fidor account");
        bankAccount.setBalance(Double.parseDouble(accountElement.getAsJsonObject().get("balance").getAsString())/100.0);
        bankAccount.setIban(accountElement.getAsJsonObject().get("iban").getAsString());
        bankAccount.setBank("Fidor Bank");
        bankAccount.setCurrency(Currency.valueOf(accountElement.getAsJsonObject().get("currency").getAsString()));

        JsonElement accountHolderElement = accountElement.getAsJsonObject().getAsJsonArray("fidorCustomers").get(0);
        String accountHolderTitle = accountHolderElement.getAsJsonObject().get("title").getAsString();
        String accountHolderFirstName = accountHolderElement.getAsJsonObject().get("first_name").getAsString();
        String accountHolderLastName = accountHolderElement.getAsJsonObject().get("last_name").getAsString();
        bankAccount.setAccountHolderName(accountHolderTitle + " " + accountHolderFirstName + " " + accountHolderLastName);

        return bankAccount;
    }

    private Transaction parseTransactionFromJson(JsonElement transactionElement){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionElement.getAsJsonObject().get("id").getAsString());
        transaction.setAmount(Double.parseDouble(transactionElement.getAsJsonObject().get("amount").getAsString())/100.0);
        transaction.setDate(Long.parseLong(transactionElement.getAsJsonObject().get("booking_date").getAsString()));
        transaction.setCounterParty(transactionElement.getAsJsonObject().get("subject").getAsString());

        JsonElement transactionDetailsElement = transactionElement.getAsJsonObject().get("transaction_type_details");
        String counterPartyName = transactionDetailsElement.getAsJsonObject().get("remote_name").getAsString();
        transaction.setDescription(transaction.getCounterParty() + " - " + counterPartyName);

        return transaction;
    }
}
