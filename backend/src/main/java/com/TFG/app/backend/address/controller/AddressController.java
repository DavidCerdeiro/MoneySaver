package com.TFG.app.backend.address.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.address.service.AddressService;
import com.TFG.app.backend.address.entity.Address;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public Address create(@RequestBody Address address) {
        return addressService.createAddress(address);
    }
}
