package com.davideleonino.locker.dto.response;

public class UpdateBoxResponseDto {

    private String message;

    public UpdateBoxResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
