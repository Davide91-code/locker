// CreaBoxRequestDto.java (create single box)
package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreaBoxRequestDto {
    @NotNull
    @Min(1)
    private Integer numBox;

    public Integer getNumBox() { return numBox; }
    public void setNumBox(Integer numBox) { this.numBox = numBox; }
}
