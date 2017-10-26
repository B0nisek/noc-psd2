package com.capco.noc.psd2.server.erste.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class ErsteAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonIgnore
    private String userId;

    @Embedded
    @JsonProperty("accountno")
    private AccountNumber accountNumber;

    private String alias;
    private String type;
    private String subtype;
    private String productI18N;
    private String product;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "balanceValue")),
            @AttributeOverride(name = "precision", column = @Column(name = "balancePrecision")),
            @AttributeOverride(name = "currency", column = @Column(name = "balanceCurrency"))
    })
    private ErsteAccountBalance balance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "disposableValue")),
            @AttributeOverride(name = "precision", column = @Column(name = "disposablePrecision")),
            @AttributeOverride(name = "currency", column = @Column(name = "disposableCurrency"))
    })
    private ErsteAccountBalance disposable;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "overdraftValue")),
            @AttributeOverride(name = "precision", column = @Column(name = "overdraftPrecision")),
            @AttributeOverride(name = "currency", column = @Column(name = "overdraftCurrency"))
    })
    private ErsteAccountBalance overdraft;
    private long overdraftDueDate;

    public ErsteAccount() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getProductI18N() {
        return productI18N;
    }

    public void setProductI18N(String productI18N) {
        this.productI18N = productI18N;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public ErsteAccountBalance getBalance() {
        return balance;
    }

    public void setBalance(ErsteAccountBalance balance) {
        this.balance = balance;
    }

    public ErsteAccountBalance getDisposable() {
        return disposable;
    }

    public void setDisposable(ErsteAccountBalance disposable) {
        this.disposable = disposable;
    }

    public ErsteAccountBalance getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(ErsteAccountBalance overdraft) {
        this.overdraft = overdraft;
    }

    public long getOverdraftDueDate() {
        return overdraftDueDate;
    }

    public void setOverdraftDueDate(long overdraftDueDate) {
        this.overdraftDueDate = overdraftDueDate;
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class AccountNumber{
        private String number;
        private String bankCode;
        private String countryCode;

        @JsonProperty("cz_iban")
        private String czIban;

        @JsonProperty("cz_bic")
        private String czBic;

        public AccountNumber() {}

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCzIban() {
            return czIban;
        }

        public void setCzIban(String czIban) {
            this.czIban = czIban;
        }

        public String getCzBic() {
            return czBic;
        }

        public void setCzBic(String czBic) {
            this.czBic = czBic;
        }
    }
}
