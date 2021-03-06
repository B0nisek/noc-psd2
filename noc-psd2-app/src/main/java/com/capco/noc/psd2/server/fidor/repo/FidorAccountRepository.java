package com.capco.noc.psd2.server.fidor.repo;

import com.capco.noc.psd2.server.fidor.domain.FidorAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FidorAccountRepository extends CrudRepository<FidorAccount, String> {

    List<FidorAccount> findByFidorCustomersCustomerName(String name);

    FidorAccount findByIban(String iban);
}
