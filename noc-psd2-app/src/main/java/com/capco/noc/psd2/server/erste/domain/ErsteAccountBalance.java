package com.capco.noc.psd2.server.erste.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ErsteAccountBalance {

    private int value;
    private byte precision;
    private String currency;

    public ErsteAccountBalance() {}

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public byte getPrecision() {
        return precision;
    }

    public void setPrecision(byte precision) {
        this.precision = precision;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
