package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateBoxRequestDto {

    @NotNull(message = "Il numero attuale del box non può essere nullo")
    @Min(value = 1, message = "Il numero attuale del box deve essere almeno 1")
    private Integer numBoxAttuale;

    @NotNull(message = "Il nuovo numero del box non può essere nullo")
    @Min(value = 1, message = "Il nuovo numero del box deve essere almeno 1")
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