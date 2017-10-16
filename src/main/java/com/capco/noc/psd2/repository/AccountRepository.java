package com.capco.noc.psd2.repository;

import com.capco.noc.psd2.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByUsername(String username);
}
