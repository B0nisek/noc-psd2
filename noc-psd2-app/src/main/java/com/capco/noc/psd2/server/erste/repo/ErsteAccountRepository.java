package com.capco.noc.psd2.server.erste.repo;

import com.capco.noc.psd2.server.erste.domain.ErsteAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ErsteAccountRepository extends CrudRepository<ErsteAccount, String> {

    List<ErsteAccount> findByUserId(String userId);

    ErsteAccount findByAccountNumberCzIban(String iban);
}
