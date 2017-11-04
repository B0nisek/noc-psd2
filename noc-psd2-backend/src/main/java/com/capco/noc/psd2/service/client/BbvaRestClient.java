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
public class BbvaRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FidorRestClient.class);

    private static final String BBVA_BASE_URL = "http://localhost:8080/bbva";
    private static final String JSON_EL_DATA = "data";
    private static final String JSON_EL_RESULT = "result";
    private static final String JSON_EL_CODE = "code";

    private final RestTemplate restTemplate;
    private final JsonParser jsonParser;

    @Autowired
    BbvaRestClient(){
        this.restTemplate = new RestTemplate();
        this.jsonParser = new JsonParser();
    }

    public String getUser(String userId){
        String response = restTemplate.getForObject(BBVA_BASE_URL + "/" + userId + "/me-full", String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return jsonObject.get(JSON_EL_DATA).toString();
    }

    public List<BankAccount> getUserAccounts(String userId){
        String response = restTemplate.getForObject(BBVA_BASE_URL + "/" + userId + "/accounts", String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        JsonArray accountJsonArray = jsonObject.getAsJsonArray(JSON_EL_DATA);
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (JsonElement jsonAccount : accountJsonArray) {
            bankAccounts.add(parseBankAccountFromJson(jsonAccount, userId));
        }

        return bankAccounts;
    }

    public BankAccount getUserAccount(String userId, String accountId){
        String response = restTemplate.getForObject(BBVA_BASE_URL + "/" + userId + "/account/" + accountId, String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return parseBankAccountFromJson(jsonObject.get(JSON_EL_DATA), userId);
    }

    public BankAccount getUserAccountByIban(String userId, String iban){
        String response = restTemplate.getForObject(BBVA_BASE_URL + "/" + userId + "/account/ib/" + iban, String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        return parseBankAccountFromJson(jsonObject.get(JSON_EL_DATA), userId);
    }

    public List<Transaction> getUserAccountTransactions(String userId, String accountId){
        String response = restTemplate.getForObject(BBVA_BASE_URL + "/" + userId + "/account/" + accountId + "/transactions", String.class);
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if(!isResponseOk(jsonObject)){
            return null;
        }

        JsonArray transactionsJsonArray = jsonObject.getAsJsonArray(JSON_EL_DATA);
        List<Transaction> jsonTransactions = new ArrayList<>();
        for (JsonElement jsonTransaction : transactionsJsonArray) {


            jsonTransactions.add(parseTransactionFromJson(jsonTransaction));
        }

        return jsonTransactions;
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

    private BankAccount parseBankAccountFromJson(JsonElement accountElement, String userId){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setExternalAccountId(accountElement.getAsJsonObject().get("id").getAsString());
        bankAccount.setAlias(accountElement.getAsJsonObject().get("alias").getAsString());
        bankAccount.setBank("BBVA");
        bankAccount.setBalance(Double.parseDouble(accountElement.getAsJsonObject().get("balance").getAsString()));
        bankAccount.setCurrency(Currency.valueOf(accountElement.getAsJsonObject().get("currency").getAsString()));

        JsonElement formatsElement = accountElement.getAsJsonObject().get("formats");
        bankAccount.setIban(formatsElement.getAsJsonObject().get("iban").getAsString());

        String userJson = getUser(userId);
        JsonObject userJsonObject = jsonParser.parse(userJson).getAsJsonObject();
        String firstName = userJsonObject.get("firstName").getAsString();
        String secondSurname = userJsonObject.get("secondSurname").getAsString();
        String surname = userJsonObject.get("surname").getAsString();

        bankAccount.setAccountHolderName(firstName + " " + secondSurname + " " + surname);
        return bankAccount;
    }

    private Transaction parseTransactionFromJson(JsonElement transactionElement){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionElement.getAsJsonObject().get("id").getAsString());
        transaction.setAmount(Double.parseDouble(transactionElement.getAsJsonObject().get("amount").getAsString()));
        transaction.setDate(Long.parseLong(transactionElement.getAsJsonObject().get("operationDate").getAsString()));
        transaction.setCounterParty("-");
        transaction.setDescription(transactionElement.getAsJsonObject().get("description").getAsString());

        return transaction;
    }
}
