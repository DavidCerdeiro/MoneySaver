package com.TFG.app.backend.type_chart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.service.Type_ChartService;

@RestController
@RequestMapping("/api/type-charts")
public class Type_ChartController {

    private final Type_ChartService typeChartService;

    public Type_ChartController(Type_ChartService typeChartService) {
        this.typeChartService = typeChartService;
    }

    /**
     * Endpoint to get all type charts
     * @return
     * - 200: OK with the list of type charts
     */
    @GetMapping
    public ResponseEntity<List<Type_Chart>> getAllTypeCharts() {

        List<Type_Chart> typeCharts = typeChartService.getAllTypeCharts();
        return ResponseEntity.ok(typeCharts);
    }
}
