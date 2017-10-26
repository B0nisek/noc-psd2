package com.capco.noc.psd2.server.bbva.repo;

import com.capco.noc.psd2.server.bbva.domain.BbvaAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BbvaAccountRepository extends CrudRepository<BbvaAccount, String>{

    List<BbvaAccount> findByUserId(String userId);
}
