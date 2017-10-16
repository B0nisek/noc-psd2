package com.capco.noc.psd2.repository;

import com.capco.noc.psd2.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
