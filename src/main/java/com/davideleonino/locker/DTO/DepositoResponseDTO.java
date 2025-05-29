package com.davideleonino.locker.DTO;

public class DepositoResponseDTO {

    private String message;
    private String codiceAccesso;

    public DepositoResponseDTO(String message, String codiceAccesso) {
        this.message = message;
        this.codiceAccesso = codiceAccesso;
    }

    public String getMessage() {
        return message;
    }

    public String getCodiceAccesso() {
        return codiceAccesso;
    }
}
