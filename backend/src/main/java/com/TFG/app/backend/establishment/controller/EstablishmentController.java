package com.TFG.app.backend.establishment.controller;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establishment")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }
    
}
