package com.TFG.app.backend.bill.dto;

public class BillResponse {
    private Integer idBill;
    private String url;
    private Integer idSpending;

    public BillResponse(Integer idBill, String url, Integer idSpending) {
        this.idBill = idBill;
        this.url = url;
        this.idSpending = idSpending;
    }

    public Integer getIdBill() {
        return idBill;
    }

    public String getUrl() {
        return url;
    }

    public Integer getIdSpending() {
        return idSpending;
    }
}
