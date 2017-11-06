package com.capco.noc.psd2.server.bbva.repo;

import com.capco.noc.psd2.server.bbva.domain.BbvaTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BbvaTransactionRepository extends CrudRepository<BbvaTransaction, String>{

    List<BbvaTransaction> findByAccountId(String accountId);
}
