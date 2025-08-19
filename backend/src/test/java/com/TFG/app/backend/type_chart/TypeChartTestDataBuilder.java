package com.TFG.app.backend.type_chart;

import com.TFG.app.backend.type_chart.entity.Type_Chart;

public class TypeChartTestDataBuilder {
    private String name = "bar";

    public Type_Chart build() {
        Type_Chart typeChart = new Type_Chart();
        typeChart.setName(name);
        return typeChart;
    }
}
