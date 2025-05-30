package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RitiroRequestDto {

    @NotBlank
    private String codiceAccesso;

    public String getCodiceAccesso() {
        return codiceAccesso;
    }

    public void setCodiceAccesso(String codiceAccesso) {
        this.codiceAccesso = codiceAccesso;
    }
}

