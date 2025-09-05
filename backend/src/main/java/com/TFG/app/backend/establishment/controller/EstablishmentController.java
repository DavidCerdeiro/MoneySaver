package com.TFG.app.backend.establishment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.establishment.dto.EstablishmentResponse;
import com.TFG.app.backend.establishment.dto.ListEstablishments;

import java.util.List;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final JwtService jwtService;
    private final UserService userService;
    private final EstablishmentService establishmentService;
    private final SpendingService spendingService;

    public EstablishmentController(EstablishmentService establishmentService, JwtService jwtService, UserService userService, SpendingService spendingService) {
        this.establishmentService = establishmentService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.spendingService = spendingService;
    }

    /**
     * Endpoint to get all establishments
     * @return:
     * - 200: OK with the list of establishments if successful
     * - 204: No Content if there are no establishments
     */
    @GetMapping
    public ResponseEntity<ListEstablishments> getEstablishments(@CookieValue(name = "accessToken", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Establishment> establishments = establishmentService.findAll();
        establishments = spendingService.getEstablishmentsOrderedByUsage(user, establishments);
        List<EstablishmentResponse> response = new ArrayList<>();
        establishments.forEach(establishment -> response.add(new EstablishmentResponse(
                establishment.getId(),
                establishment.getName()
        )));
        if(response.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(new ListEstablishments(response));
    }
}
