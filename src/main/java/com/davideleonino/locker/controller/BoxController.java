package com.davideleonino.locker.controller;

import com.davideleonino.locker.dto.request.*;
import com.davideleonino.locker.dto.response.ApiResponseDto;
import com.davideleonino.locker.dto.response.BoxDto;
import com.davideleonino.locker.service.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto> getAllBoxes() {
        List<BoxDto> boxes = boxService.getAllBoxes();
        return ResponseEntity.ok(new ApiResponseDto(true, "Lista box", boxes));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createBox(@Valid @RequestBody BoxCreateRequestDto request) {
        BoxDto box = boxService.createBox(request);
        return ResponseEntity.ok(new ApiResponseDto(true, "Box creato", box));
    }

    @PutMapping
    public ResponseEntity<ApiResponseDto> updateBox(@Valid @RequestBody BoxUpdateRequestDto request) {
        BoxDto box = boxService.updateBox(request);
        return ResponseEntity.ok(new ApiResponseDto(true, "Box aggiornato", box));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDto> deleteBox(@Valid @RequestBody DeleteBoxDTO request) {
        boxService.deleteBox(request);
        return ResponseEntity.ok(new ApiResponseDto(true, "Box eliminato", null));
    }

    @PostMapping("/deposito")
    public ResponseEntity<ApiResponseDto> deposito(@Valid @RequestBody DepositoRequestDto request) {
        BoxDto result = boxService.deposito(request);
        return ResponseEntity.ok(new ApiResponseDto(true, "Deposito effettuato", result));
    }

    @PostMapping("/ritiro")
    public ResponseEntity<ApiResponseDto> ritiro(@Valid @
