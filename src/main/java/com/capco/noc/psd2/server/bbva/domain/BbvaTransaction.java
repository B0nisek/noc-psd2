package com.capco.noc.psd2.server.bbva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class BbvaTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonIgnore
    private String accountId;

    private long operationDate;
    private long valueDate;
    private double amount;
    private String currency;
    private String description;

    @Embedded
    private Category category;

    @Embedded
    private SubCategory subCategory;

    @Embedded
    private ClientNote clientNote;

    @Embedded
    private AttachedInfo attachedInfo;

    public BbvaTransaction() {}

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

    public long getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(long operationDate) {
        this.operationDate = operationDate;
    }

    public long getValueDate() {
        return valueDate;
    }

    public void setValueDate(long valueDate) {
        this.valueDate = valueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public ClientNote getClientNote() {
        return clientNote;
    }

    public void setClientNote(ClientNote clientNote) {
        this.clientNote = clientNote;
    }

    public AttachedInfo getAttachedInfo() {
        return attachedInfo;
    }

    public void setAttachedInfo(AttachedInfo attachedInfo) {
        this.attachedInfo = attachedInfo;
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class Category{

        @JsonProperty("id")
        private String catId;

        @JsonProperty("name")
        private String catName;

        public Category() {}

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String name) {
            this.catName = name;
        }
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class SubCategory{

        @JsonProperty("id")
        private String subCatId;

        @JsonProperty("name")
        private String subCatName;

        public SubCategory() {}

        public String getSubCatId() {
            return subCatId;
        }

        public void setSubCatId(String subCatId) {
            this.subCatId = subCatId;
        }

        public String getSubCatName() {
            return subCatName;
        }

        public void setSubCatName(String name) {
            this.subCatName = name;
        }
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class ClientNote{
        private String text;

        @JsonProperty("date")
        private long clientNoteDate;

        public ClientNote() {}

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public long getClientNoteDate() {
            return clientNoteDate;
        }

        public void setClientNoteDate(long date) {
            this.clientNoteDate = date;
        }
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class AttachedInfo{
        private String name;
        private String type;
        private int size;
        private long date;

        public AttachedInfo() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }
    }
}
