package com.TFG.app.backend.spending.dto;

import java.time.LocalDate;

public class ProcessFileResponse {
    private String supplierName;
    private LocalDate receiptDate;
    private Float totalAmount;
    private Integer idEstablishment;
    private String establishmentName;

    public ProcessFileResponse(String supplierName, LocalDate receiptDate, Float totalAmount, Integer idEstablishment, String establishmentName) {
        this.supplierName = supplierName;
        this.receiptDate = receiptDate;
        this.totalAmount = totalAmount;
        this.idEstablishment = idEstablishment;
        this.establishmentName = establishmentName;
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
}