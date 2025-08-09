package com.TFG.app.backend.establishment.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.establishment.dto.EstablishmentResponse;
import java.util.List;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping("all")
    public List<EstablishmentResponse> findAllByUser() {
        List<Establishment> establishments = establishmentService.findAll();
        List<EstablishmentResponse> response = new ArrayList<>();
        establishments.forEach(establishment -> response.add(new EstablishmentResponse(
                establishment.getId(),
                establishment.getName(),
                establishment.getRegion().getCountry(),
                establishment.getRegion().getCity())));
        return response;
    }
}
