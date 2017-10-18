package com.capco.noc.psd2.server.fidor.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
public class FidorTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("transaction_type")
    private String transactionType;
    private String subject;
    private int amount;
    private String currency;

    @SerializedName("booking_code")
    private String bookingCode;

    @SerializedName("booking_date")
    private long bookingDate;

    @SerializedName("value_date")
    private long valueDate;

    @SerializedName("return_transaction_id")
    private String returnTransactionId;

    @SerializedName("created_at")
    private long createdAt;

    @SerializedName("updated_at")
    private long updatedAt;

    @Embedded
    @SerializedName("transaction_type_details")
    private TransactionTypeDetails transactionTypeDetails;

    public FidorTransaction() {}

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public long getValueDate() {
        return valueDate;
    }

    public void setValueDate(long valueDate) {
        this.valueDate = valueDate;
    }

    public String getReturnTransactionId() {
        return returnTransactionId;
    }

    public void setReturnTransactionId(String returnTransactionId) {
        this.returnTransactionId = returnTransactionId;
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

    public TransactionTypeDetails getTransactionTypeDetails() {
        return transactionTypeDetails;
    }

    public void setTransactionTypeDetails(TransactionTypeDetails transactionTypeDetails) {
        this.transactionTypeDetails = transactionTypeDetails;
    }

    @Override
    public String toString() {
        return "FidorTransaction{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", subject='" + subject + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", bookingCode='" + bookingCode + '\'' +
                ", bookingDate=" + bookingDate +
                ", valueDate=" + valueDate +
                ", returnTransactionId='" + returnTransactionId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", transactionTypeDetails=" + transactionTypeDetails +
                '}';
    }

    /**
     * TODO - documentation
     * */
    @Embeddable
    public static class TransactionTypeDetails {

        @SerializedName("sepa_credit_transfer_id")
        private String sepaCreditTransferId;

        @SerializedName("remote_name")
        private String remoteName;

        @SerializedName("remote_iban")
        private String remoteIban;

        @SerializedName("remote_bic")
        private String remoteBic;

        public TransactionTypeDetails() {}

        public String getSepaCreditTransferId() {
            return sepaCreditTransferId;
        }

        public void setSepaCreditTransferId(String sepaCreditTransferId) {
            this.sepaCreditTransferId = sepaCreditTransferId;
        }

        public String getRemoteName() {
            return remoteName;
        }

        public void setRemoteName(String remoteName) {
            this.remoteName = remoteName;
        }

        public String getRemoteIban() {
            return remoteIban;
        }

        public void setRemoteIban(String remoteIban) {
            this.remoteIban = remoteIban;
        }

        public String getRemoteBic() {
            return remoteBic;
        }

        public void setRemoteBic(String remoteBic) {
            this.remoteBic = remoteBic;
        }

        @Override
        public String toString() {
            return "TransactionTypeDetails{" +
                    "sepaCreditTransferId='" + sepaCreditTransferId + '\'' +
                    ", remoteName='" + remoteName + '\'' +
                    ", remoteIban='" + remoteIban + '\'' +
                    ", remoteBic='" + remoteBic + '\'' +
                    '}';
        }
    }
}
