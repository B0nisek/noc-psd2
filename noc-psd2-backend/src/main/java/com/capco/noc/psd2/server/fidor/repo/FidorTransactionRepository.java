package com.capco.noc.psd2.server.fidor.repo;

import com.capco.noc.psd2.server.fidor.domain.FidorTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FidorTransactionRepository extends CrudRepository<FidorTransaction, String>{

    List<FidorTransaction> findByAccountId(String accountId);
}
