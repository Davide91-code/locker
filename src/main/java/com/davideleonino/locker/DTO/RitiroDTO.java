package com.davideleonino.locker.DTO;

import jakarta.validation.constraints.NotBlank;

public class RitiroDTO {

    @NotBlank
    private String codiceAccesso;

    public String getCodiceAccesso() {
        return codiceAccesso;
    }

    public void setCodiceAccesso(String codiceAccesso) {
        this.codiceAccesso = codiceAccesso;
    }
}

