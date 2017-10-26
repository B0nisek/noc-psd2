package com.capco.noc.psd2.server.fidor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FidorAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private List<FidorCustomer> fidorCustomers = new ArrayList<>();

    @JsonProperty("account_number")
    private String accountNumber;
    private String iban;

    //Last 2 digits are cents, so 12300 is 123,00
    private int balance;

    @JsonProperty("balance_available")
    private int balanceAvailable;
    private int overdraft;

    @JsonProperty("preauth_amount")
    private int preAuthorizedAmount;

    private String currency;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("updated_at")
    private long updatedAt;

    @JsonProperty("is_debit_note_enabled")
    private boolean isDebitNoteEnabled;

    @JsonProperty("is_locked")
    private boolean isLocked;

    @JsonProperty("is_trusted")
    private boolean isTrusted;

    public FidorAccount() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FidorCustomer> getFidorCustomers() {
        return fidorCustomers;
    }

    public void setFidorCustomers(List<FidorCustomer> fidorCustomers) {
        this.fidorCustomers = fidorCustomers;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(int balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }

    public int getPreAuthorizedAmount() {
        return preAuthorizedAmount;
    }

    public void setPreAuthorizedAmount(int preAuthorizedAmount) {
        this.preAuthorizedAmount = preAuthorizedAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDebitNoteEnabled() {
        return isDebitNoteEnabled;
    }

    public void setDebitNoteEnabled(boolean debitNoteEnabled) {
        isDebitNoteEnabled = debitNoteEnabled;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isTrusted() {
        return isTrusted;
    }

    public void setTrusted(boolean trusted) {
        isTrusted = trusted;
    }
}
