package com.davideleonino.locker.DTO;

public class UpdateBoxDTO {
    private Integer numBoxAttuale;
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