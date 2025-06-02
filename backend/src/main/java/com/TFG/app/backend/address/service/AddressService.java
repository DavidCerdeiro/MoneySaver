package com.TFG.app.backend.address.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.address.repository.AddressRepository;
import com.TFG.app.backend.address.entity.Address;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }   

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }
}
