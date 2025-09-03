package com.TFG.app.backend.type_chart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;

@DataJpaTest
@ActiveProfiles("test")
public class Type_ChartIntegrationTest {

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testCreateTypeChart() {
        Type_Chart typeChart = new Type_Chart();
        typeChart.setName("Bars");
        typeChartRepository.save(typeChart);

        Assertions.assertTrue(typeChartRepository.existsByName("bars"));
    }
}
