package com.davideleonino.locker.controller;

import com.davideleonino.locker.dto.request.*;
import com.davideleonino.locker.dto.response.ApiResponseDto;
import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.service.LockerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locker")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @PostMapping("/boxes")
    public ResponseEntity<ApiResponseDto> creaBox(@RequestBody @Valid CreaBoxRequestDto dto) {
        try {
            Box box = lockerService.creaBox(dto.getNumBox());
            return ResponseEntity.ok(new ApiResponseDto(true, "Box creato con successo", box));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage(), null));
        }
    }

    @PostMapping("/boxes/range")
    public ResponseEntity<ApiResponseDto> creaBoxesRange(@RequestBody @Valid CreaBoxRangeRequestDto dto) {
        try {
            List<Box> boxes = lockerService.creaBoxesDaRange(dto.getStart(), dto.getEnd());
            return ResponseEntity.ok(new ApiResponseDto(true, "Box creati con successo", boxes));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage(), null));
        }
    }


    //query totale endpoint
    @GetMapping("/boxes")
    public ResponseEntity<ApiResponseDto> getAllBoxes() {
        List<Box> boxes = lockerService.getAllBoxes();
        return ResponseEntity.ok(new ApiResponseDto(true, "Lista di tutti i box", boxes));
    }

    //query box liberi endpoint
    @GetMapping("/boxes/liberi")
    public ResponseEntity<ApiResponseDto> getBoxLiberi() {
        List<Box> liberi = lockerService.getBoxLibero();
        return ResponseEntity.ok(new ApiResponseDto(true, "Box liberi trovati", liberi));
    }

    @GetMapping("/boxes/occupati")
    public ResponseEntity<ApiResponseDto> getBoxOccupati() {
        List<Box> occupati = lockerService.getBoxOccupati();
        return ResponseEntity.ok(new ApiResponseDto(true, "Box occupati trovati", occupati));
    }

    //query box occupati ordine decres endpoint
    @GetMapping("/boxes/occupati_ordinati")
    public ResponseEntity<ApiResponseDto> getBoxOccupatiOrdinatiD() {
        List<Box> ordinati = lockerService.getBoxOccupatiOrdinatiD();
        return ResponseEntity.ok(new ApiResponseDto(true, "Box occupati ordinati in ordine decrescente", ordinati));
    }




    /*
    @GetMapping("/boxes/{numBox}")
    public ResponseEntity<Map<String, Object>> getBoxByNumBox(@PathVariable Integer numBox) {
        try {
            Box box = lockerService.getBoxByNumBox(numBox);  // ora ritorna direttamente Box, non Optionali
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Box trovato",
                    "data", box
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

     */

    @GetMapping("/boxes/{numBox}")
    public ResponseEntity<ApiResponseDto> getBoxByNumBox(@PathVariable Integer numBox) {
        try {
            Box box = lockerService.getBoxByNumBox(numBox);
            return ResponseEntity.ok(new ApiResponseDto(true, "Box trovato", box));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponseDto(false, e.getMessage(), null));
        }
    }

    @GetMapping("/boxes/statistiche")
    public ResponseEntity<ApiResponseDto> getStatisticheBox() {
        Map<String, Object> stats = lockerService.getStatisticheBox();
        return ResponseEntity.ok(new ApiResponseDto(true, "Statistiche relative ai box", stats));
    }

    @GetMapping("/boxes/occupati_piu_tempo")
    public ResponseEntity<ApiResponseDto> getBoxOccupatiDaPiuTempo() {
        List<Box> boxVecchi = lockerService.getBoxOccupatiDaPiuTempo();
        return ResponseEntity.ok(new ApiResponseDto(true, "Box occupati da più tempo", boxVecchi));
    }



    @PostMapping("/deposita")
    public ResponseEntity<ApiResponseDto> deposita(@RequestBody @Valid DepositoRequestDto dto) {
        try {
            String codiceAccesso = lockerService.deposita(dto.getNumBox());
            return ResponseEntity.ok(new ApiResponseDto(true, "Deposito effettuato con successo", codiceAccesso));
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Errore nel deposito: " + err.getMessage(), null));
        }
    }


    @PostMapping("/ritira")
    public ResponseEntity<ApiResponseDto> ritira(@RequestBody @Valid RitiroRequestDto dto) {
        try {
            lockerService.ritira(dto.getCodiceAccesso());
            return ResponseEntity.ok(new ApiResponseDto(true, "Ritiro effettuato con successo", null));
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Errore nel ritiro: " + err.getLocalizedMessage(), null));
        }
    }

    @PutMapping("/boxes")
    public ResponseEntity<ApiResponseDto> updateBox(@RequestBody @Valid UpdateBoxRequestDto dto) {
        try {
            Box updatedBox = lockerService.updateBox(dto.getNumBoxAttuale(), dto.getNuovoNumBox());
            return ResponseEntity.ok(new ApiResponseDto(true, "Box aggiornato con successo", updatedBox));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Errore: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/boxes/{numBox}")
    public ResponseEntity<ApiResponseDto> deleteBox(@PathVariable Integer numBox) {
        try {
            lockerService.deleteBox(numBox);
            return ResponseEntity.ok(new ApiResponseDto(true, "Box eliminato con successo", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDto(false, "Errore: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/boxes/range")
    public ResponseEntity<ApiResponseDto> deleteBoxesRange(@RequestBody @Valid DeleteBoxesRangeRequestDto dto) {
        try {
            lockerService.deleteBoxesDaRange(dto.getStart(), dto.getEnd());
            return ResponseEntity.ok(new ApiResponseDto(true, "Box eliminati con successo", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDto(false, "Errore: " + e.getLocalizedMessage(), null));
        }
    }



}
