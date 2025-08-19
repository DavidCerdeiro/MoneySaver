package com.TFG.app.backend.type_chart;

import org.junit.jupiter.api.Test;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import org.junit.jupiter.api.Assertions;

public class Type_ChartUnitTest {
    @Test
    public void testTypeChartAttributes() {
        Type_Chart typeChart = new Type_Chart();

        typeChart.setName("Bar Chart");

        Assertions.assertEquals("Bar Chart", typeChart.getName());
    }
}
