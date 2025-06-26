package com.davideleonino.locker.service;

import com.davideleonino.locker.dto.request.*;
import com.davideleonino.locker.dto.response.BoxDto;

import java.util.List;

public interface BoxService {
    List<BoxDto> getAllBoxes();

    BoxDto getBoxById(Integer id);

    BoxDto createBox(BoxCreateRequestDto createRequest);

    BoxDto updateBox(BoxUpdateRequestDto updateRequest);

    void deleteBox(DeleteBoxDTO deleteBoxDTO);

    List<BoxDto> getBoxesByStatus(String status);

    BoxDto deposito(DepositoRequestDto depositoRequest);

    BoxDto ritiro(RitiroRequestDto ritiroRequest);
}
