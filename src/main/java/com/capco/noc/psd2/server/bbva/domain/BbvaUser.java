package com.capco.noc.psd2.server.bbva.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class BbvaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //External user identifier
    private String userId;
    private String firstName;
    private String surname;
    private String secondSurname;
    private String sex;
    private long birthdate;

    @Embedded
    private IdentityDocument identityDocument;

    @Embedded
    private Address address;

    private String email;
    private String phone;

    public BbvaUser() {}

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public IdentityDocument getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(IdentityDocument identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class IdentityDocument {

        private IdentityDocumentType type;
        private String number;

        public IdentityDocument() {}

        public IdentityDocumentType getType() {
            return type;
        }

        public void setType(IdentityDocumentType type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public enum IdentityDocumentType{
            NATIONALID,
            FOREIGNEDID,
            PASSPORT
        }
    }

    /**
     * TODO - document
     * */
    @Embeddable
    public static class Address {

        @JsonProperty("id")
        private String addressId;
        private String additionalData;
        private String door;
        private String floor;
        private String city;
        private String streetName;
        private String streetNumber;
        private String streetType;
        private String zipcode;
        private String country;

        @JsonProperty("type")
        private String addressType;

        public Address() {}

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(String additionalData) {
            this.additionalData = additionalData;
        }

        public String getDoor() {
            return door;
        }

        public void setDoor(String door) {
            this.door = door;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getStreetType() {
            return streetType;
        }

        public void setStreetType(String streetType) {
            this.streetType = streetType;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }
    }
}
