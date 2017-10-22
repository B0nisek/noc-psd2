package com.capco.noc.psd2.server.bbva.repo;

import com.capco.noc.psd2.server.bbva.domain.BbvaUser;
import org.springframework.data.repository.CrudRepository;

public interface BbvaUserRepository extends CrudRepository<BbvaUser, String>{

    BbvaUser findByUserId(String userId);
}
