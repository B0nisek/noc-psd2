package com.capco.noc.psd2.repository;

import com.capco.noc.psd2.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByUsername(String username);
}
