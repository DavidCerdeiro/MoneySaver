package com.TFG.app.backend.type_chart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;

@Service
public class Type_ChartService {

    private final Type_ChartRepository typeChartRepository;

    public Type_ChartService(Type_ChartRepository typeChartRepository) {
        this.typeChartRepository = typeChartRepository;
    }

    public List<Type_Chart> getAllTypeCharts() {
        return typeChartRepository.findAll();
    }

    public Type_Chart getTypeChartById(Integer id) {
        return typeChartRepository.findById(id).orElse(null);
    }
}
