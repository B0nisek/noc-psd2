package com.capco.noc.psd2.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
public class BankAccount {

    @Embedded
    private List<Transaction> transactions = new ArrayList<>();

    private String accountHolderName;
    private String alias;
    private String bank;
    private String iban;
    private double balance;
    private Currency currency;
}
