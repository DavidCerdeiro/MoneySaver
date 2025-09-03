package com.TFG.app.backend.type_periodic.controller;
import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
import com.TFG.app.backend.type_periodic.service.Type_PeriodicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/type-periodic")
public class Type_PeriodicController {
    private final Type_PeriodicService typePeriodicService;

    public Type_PeriodicController(Type_PeriodicService typePeriodicService) {
        this.typePeriodicService = typePeriodicService;
    }

    /**
     * Endpoint to get all type periodics
     * @return
     * - 200: OK with the list of type periodics
     */
    @GetMapping
    public ResponseEntity<List<Type_Periodic>> getAllTypePeriodics() {
        List<Type_Periodic> typePeriodics = typePeriodicService.getAllTypePeriodics();
        return ResponseEntity.ok(typePeriodics);
    }
}
