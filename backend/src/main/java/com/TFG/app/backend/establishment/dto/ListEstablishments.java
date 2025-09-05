package com.TFG.app.backend.establishment.dto;

import java.util.List;

public class ListEstablishments {
    private List<EstablishmentResponse> establishments;

    public ListEstablishments(List<EstablishmentResponse> establishments) {
        this.establishments = establishments;
    }

    public List<EstablishmentResponse> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<EstablishmentResponse> establishments) {
        this.establishments = establishments;
    }
}
