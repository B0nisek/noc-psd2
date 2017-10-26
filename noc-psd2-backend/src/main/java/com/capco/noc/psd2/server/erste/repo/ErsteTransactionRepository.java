package com.capco.noc.psd2.server.erste.repo;

import com.capco.noc.psd2.server.erste.domain.ErsteTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ErsteTransactionRepository extends CrudRepository<ErsteTransaction, String>{

    List<ErsteTransaction> findByAccountId(String accountId);
}
