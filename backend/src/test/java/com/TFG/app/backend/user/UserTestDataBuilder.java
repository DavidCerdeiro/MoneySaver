package com.TFG.app.backend.user;

import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.user.entity.User;

public class UserTestDataBuilder {
    private String name = "David";
    private String password = "Hola2002.";
    private String email = "david@gmail.com";
    private String surname = "Cerdeiro";
    private boolean isAuthenticated = true;
    private Type_Chart typeChart;

    public UserTestDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserTestDataBuilder withTypeChart(Type_Chart typeChart) {
        this.typeChart = typeChart;
        return this;
    }

    public User build() {
        User user = new User();

        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setSurname(surname);
        user.setIsAuthenticated(isAuthenticated);
        user.setTypeChart(typeChart);
        return user;
    }
}
