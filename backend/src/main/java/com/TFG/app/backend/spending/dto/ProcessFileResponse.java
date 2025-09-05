package com.TFG.app.backend.spending.dto;

import java.time.LocalDate;

public class ProcessFileResponse {
    private String supplierName;
    private LocalDate receiptDate;
    private Float totalAmount;
    private Integer idEstablishment;
    private String establishmentName;
    private Integer idCategory;

    public ProcessFileResponse(String supplierName, LocalDate receiptDate, Float totalAmount, Integer idEstablishment, String establishmentName, Integer idCategory) {
        this.supplierName = supplierName;
        this.receiptDate = receiptDate;
        this.totalAmount = totalAmount;
        this.idEstablishment = idEstablishment;
        this.establishmentName = establishmentName;
        this.idCategory = idCategory;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public Integer getIdEstablishment() {
        return idEstablishment;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public Integer getIdCategory() {
        return idCategory;
    }
}