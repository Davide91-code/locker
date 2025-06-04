package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.NotNull;

public class DeleteBoxesRangeRequestDto {
    @NotNull
    private Integer start;

    @NotNull
    private Integer end;

    public Integer getStart() { return start; }
    public void setStart(Integer start) { this.start = start; }

    public Integer getEnd() { return end; }
    public void setEnd(Integer end) { this.end = end; }
}