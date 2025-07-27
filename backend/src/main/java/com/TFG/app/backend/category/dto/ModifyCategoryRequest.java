package com.TFG.app.backend.category.dto;

public class ModifyCategoryRequest {
    
    private Integer id;

    private Integer idUser;

    private String name;

    private String icon;

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
    
}

