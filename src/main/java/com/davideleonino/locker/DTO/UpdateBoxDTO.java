package com.davideleonino.locker.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateBoxDTO {

    @NotNull
    @Min(1)
    private Integer numBoxAttuale;

    @NotNull
    @Min(1)
    private Integer nuovoNumBox;

    public Integer getNumBoxAttuale() {
        return numBoxAttuale;
    }

    public void setNumBoxAttuale(Integer numBoxAttuale) {
        this.numBoxAttuale = numBoxAttuale;
    }

    public Integer getNuovoNumBox() {
        return nuovoNumBox;
    }

    public void setNuovoNumBox(Integer nuovoNumBox) {
        this.nuovoNumBox = nuovoNumBox;
    }
}