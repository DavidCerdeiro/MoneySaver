package com.TFG.app.backend.spending.dto;

import org.springframework.web.multipart.MultipartFile;

import com.TFG.app.backend.establishment.dto.EstablishmentResponse;

public class AddSpendingRequest {
    private String name;
    private Integer idCategory;
    private Integer idUser;
    private double amount;
    private String date;
    private String expirationDate;
    private boolean isPeriodic;
    private Integer typePeriodic;
    private EstablishmentResponse establishment;
    private MultipartFile file;

    // Getters
    public String getName() { return name; }
    public Integer getIdCategory() { return idCategory; }
    public Integer getIdUser() { return idUser; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getExpirationDate() { return expirationDate; }
    public boolean isPeriodic() { return isPeriodic; }
    public Integer getTypePeriodic() { return typePeriodic; }
    public EstablishmentResponse getEstablishment() { return establishment; }
    public MultipartFile getFile() { return file; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setIdCategory(Integer idCategory) { this.idCategory = idCategory; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(String date) { this.date = date; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
    public void setIsPeriodic(boolean isPeriodic) { this.isPeriodic = isPeriodic; }
    public void setTypePeriodic(Integer typePeriodic) { this.typePeriodic = typePeriodic; }
    public void setEstablishment(EstablishmentResponse establishment) { this.establishment = establishment; }
    public void setFile(MultipartFile file) { this.file = file; }
}
