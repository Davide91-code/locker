// DeleteBoxDTO.java (delete single box)
package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.NotNull;

public class DeleteBoxDTO {
    @NotNull
    private Integer numBox;

    public Integer getNumBox() { return numBox; }
    public void setNumBox(Integer numBox) { this.numBox = numBox; }
}
