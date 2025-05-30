package com.davideleonino.locker.dto.response;

public class DepositoResponseDto {

    private String message;
    private String codiceAccesso;

    public DepositoResponseDto(String message, String codiceAccesso) {
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
