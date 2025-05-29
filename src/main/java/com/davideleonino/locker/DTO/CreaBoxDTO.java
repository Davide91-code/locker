package com.davideleonino.locker.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class CreaBoxDTO {

    @NotNull
    @Min(1)
    private Integer numBox;

    public Integer getNumBox() {
        return numBox;
    }

    public void setNumBox(Integer numBox) {
        this.numBox = numBox;
    }
}
