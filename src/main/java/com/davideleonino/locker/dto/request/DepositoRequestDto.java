// DepositoRequestDto.java (deposit request)
package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.NotNull;

public class DepositoRequestDto {
    @NotNull
    private Integer numBox;

    public Integer getNumBox() { return numBox; }
    public void setNumBox(Integer numBox) { this.numBox = numBox; }
}
