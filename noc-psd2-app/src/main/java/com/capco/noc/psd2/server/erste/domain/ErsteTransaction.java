package com.capco.noc.psd2.server.erste.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ErsteTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonIgnore
    private String accountId;

    @Embedded
    private AccountParty accountParty;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "amountValue")),
            @AttributeOverride(name = "precision", column = @Column(name = "amountPrecision")),
            @AttributeOverride(name = "currency", column = @Column(name = "amountCurrency"))
    })
    private ErsteAccountBalance amount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "amountSenderValue")),
            @AttributeOverride(name = "precision", column = @Column(name = "amountSenderPrecision")),
            @AttributeOverride(name = "currency", column = @Column(name = "amountSenderCurrency"))
    })
    private ErsteAccountBalance amountSender;

    private long bookingDate;
    private String cardNumber;
    private String constantSymbol;
    private String description;
    private boolean descriptionEditable;
    private String payeeNote;
    private String payerNote;
    private String specificSymbol;
    private String transactionType;
    private long valuationDate;
    private String variableSymbol;

    public ErsteTransaction() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AccountParty getAccountParty() {
        return accountParty;
    }

    public void setAccountParty(AccountParty accountParty) {
        this.accountParty = accountParty;
    }

    public ErsteAccountBalance getAmount() {
        return amount;
    }

    public void setAmount(ErsteAccountBalance amount) {
        this.amount = amount;
    }

    public ErsteAccountBalance getAmountSender() {
        return amountSender;
    }

    public void setAmountSender(ErsteAccountBalance amountSender) {
        this.amountSender = amountSender;
    }

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDescriptionEditable() {
        return descriptionEditable;
    }

    public void setDescriptionEditable(boolean descriptionEditable) {
        this.descriptionEditable = descriptionEditable;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }

    public String getPayerNote() {
        return payerNote;
    }

    public void setPayerNote(String payerNote) {
        this.payerNote = payerNote;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public long getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(long valuationDate) {
        this.valuationDate = valuationDate;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class AccountParty{
        private String accountNumber;
        private String accountPrefix;
        private String bankCode;
        private String bic;
        private String iban;
        private String partyInfo;
        private String partyDescription;

        public AccountParty() {}

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountPrefix() {
            return accountPrefix;
        }

        public void setAccountPrefix(String accountPrefix) {
            this.accountPrefix = accountPrefix;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBic() {
            return bic;
        }

        public void setBic(String bic) {
            this.bic = bic;
        }

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getPartyInfo() {
            return partyInfo;
        }

        public void setPartyInfo(String partyInfo) {
            this.partyInfo = partyInfo;
        }

        public String getPartyDescription() {
            return partyDescription;
        }

        public void setPartyDescription(String partyDescription) {
            this.partyDescription = partyDescription;
        }
    }
}
