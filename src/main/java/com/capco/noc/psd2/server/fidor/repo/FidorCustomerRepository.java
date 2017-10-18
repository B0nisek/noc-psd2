package com.capco.noc.psd2.server.fidor.repo;

import com.capco.noc.psd2.server.fidor.domain.FidorCustomer;
import org.springframework.data.repository.CrudRepository;

public interface FidorCustomerRepository extends CrudRepository<FidorCustomer, String>{

    FidorCustomer findByEmail(String email);
}
