// CreaBoxRangeRequestDto.java (create range boxes)
package com.davideleonino.locker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreaBoxRangeRequestDto {
    @NotNull
    @Min(1)
    private Integer start;

    @NotNull
    @Min(1)
    private Integer end;

    public Integer getStart() { return start; }
    public void setStart(Integer start) { this.start = start; }

    public Integer getEnd() { return end; }
    public void setEnd(Integer end) { this.end = end; }
}
