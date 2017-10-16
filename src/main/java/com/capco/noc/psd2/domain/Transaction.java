package com.capco.noc.psd2.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Data
@NoArgsConstructor
public class Transaction {

    private String transactionId;
    private double amount;
    private long date;
    private String counterParty;
    private String description;
}
