// BoxDto.java (response)
package com.davideleonino.locker.dto.response;

public class BoxDto {
    private Integer numBox;
    private String status;

    public BoxDto() {}

    public BoxDto(Integer numBox, String status) {
        this.numBox = numBox;
        this.status = status;
    }

    public Integer getNumBox() { return numBox; }
    public String getStatus() { return status; }

    public void setNumBox(Integer numBox) { this.numBox = numBox; }
    public void setStatus(String status) { this.status = status; }
}
