package com.TFG.app.backend.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.address.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
