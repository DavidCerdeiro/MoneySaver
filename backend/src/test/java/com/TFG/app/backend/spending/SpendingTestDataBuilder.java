package com.TFG.app.backend.spending;
import com.TFG.app.backend.spending.entity.Spending;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;
public class SpendingTestDataBuilder {
    private String name = "EA FC 25";
    private BigDecimal amount = BigDecimal.valueOf(69.99);
    private Category category;
    private LocalDate date = LocalDate.now();
    private boolean isPeriodic = false;
    private Establishment establishment;

    public SpendingTestDataBuilder withIsPeriodic(boolean isPeriodic) {
        this.isPeriodic = isPeriodic;
        return this;
    }

    public SpendingTestDataBuilder withEstablishment(Establishment establishment) {
        this.establishment = establishment;
        return this;
    }

    public SpendingTestDataBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public Spending build() {
        Spending spending = new Spending();
        spending.setName(name);
        spending.setAmount(amount);
        spending.setCategory(category);
        spending.setDate(date);
        spending.setIsPeriodic(isPeriodic);
        spending.setEstablishment(establishment);
        return spending;
    }
}
