package com.TFG.app.backend.goal.dto;

import java.math.BigDecimal;

public class GoalResponse {
    private Integer id;
    private String name;
    private BigDecimal targetAmount;
    private Integer idCategory;
    private String nameCategory;
    private BigDecimal amountCategory;
    private BigDecimal percent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public BigDecimal getAmountCategory() {
        return amountCategory;
    }

    public void setAmountCategory(BigDecimal amountCategory) {
        this.amountCategory = amountCategory;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
