package com.davideleonino.locker.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreaBoxesBatchRequestDto {
    @NotEmpty(message = "La lista dei box non può essere vuota")
    @Valid
    private List<CreaBoxRequestDto> boxes;

    public List<CreaBoxRequestDto> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<CreaBoxRequestDto> boxes) {
        this.boxes = boxes;
    }
}