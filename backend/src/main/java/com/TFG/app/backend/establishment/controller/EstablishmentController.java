package com.TFG.app.backend.establishment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Endpoint to get all establishments
     * @return:
     * - 200: OK with the list of establishments if successful
     * - 204: No Content if there are no establishments
     */
    @GetMapping
    public ResponseEntity<List<EstablishmentResponse>> getEstablishments() {
        List<Establishment> establishments = establishmentService.findAll();
        List<EstablishmentResponse> response = new ArrayList<>();
        establishments.forEach(establishment -> response.add(new EstablishmentResponse(
                establishment.getId(),
                establishment.getName()
        )));
        if(response.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(response);
    }
}
