package com.TFG.app.backend.establishment.controller;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }
    
    @PostMapping
    public Establishment create(@RequestBody Establishment establishment) {
        return establishmentService.createEstablishment(establishment);
    }
}
