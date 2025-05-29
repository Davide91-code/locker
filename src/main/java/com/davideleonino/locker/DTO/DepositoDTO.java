package com.davideleonino.locker.DTO;

import jakarta.validation.constraints.NotNull;

public class DepositoDTO {

    @NotNull
    private Integer numBox;

    public Integer getNumBox() {
        return numBox;
    }

    public void setNumBox(Integer numBox) {
        this.numBox = numBox;
    }
}