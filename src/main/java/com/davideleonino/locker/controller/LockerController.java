package com.davideleonino.locker.controller;

import com.davideleonino.locker.dto.request.CreaBoxRequestDto;
import com.davideleonino.locker.dto.request.DepositoRequestDto;
import com.davideleonino.locker.dto.request.RitiroRequestDto;
import com.davideleonino.locker.dto.request.UpdateBoxRequestDto;
import com.davideleonino.locker.dto.response.DepositoResponseDto;
import com.davideleonino.locker.dto.response.RitiroResponseDto;
import com.davideleonino.locker.dto.response.UpdateBoxResponseDto;
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

    @PostMapping("/boxes")      //con dto
    public ResponseEntity<Box> creaBox(@RequestBody @Valid CreaBoxRequestDto dto) {
        Box box = lockerService.creaBox(dto.getNumBox());
        return ResponseEntity.ok(box);
    }


    //query totale endpoint
    @GetMapping("/boxes")
    public ResponseEntity<List<Box>> getAllBoxes() {
        List<Box> boxes = lockerService.getAllBoxes();
        return ResponseEntity.ok(boxes);
    }

    //query box liberi endpoint
    @GetMapping("/boxes/liberi")
    public ResponseEntity<List<Box>> getBoxLiberi(){
        return ResponseEntity.ok(lockerService.getBoxLibero());
    }

    //query sui box occupati endpoint
    @GetMapping("/boxes/occupati")
    public ResponseEntity <List<Box>> getBoxOccupati(){
        return ResponseEntity.ok(lockerService.getBoxOccupati());
    }

    //query box occupati ordine decres endpoint
    @GetMapping("/boxes/occupati_ordinati")
    public ResponseEntity <List<Box>> getBoxOccupatiOrdinatiD(){
        return  ResponseEntity.ok(lockerService.getBoxOccupatiOrdinatiD());
    }

    @GetMapping("/boxes/{numBox}")
    public ResponseEntity<Box> getBoxByNumBox(@PathVariable Integer numBox) {
        return lockerService.getBoxByNumBox(numBox)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/deposita")
    public ResponseEntity<DepositoResponseDto> deposita(@RequestBody @Valid DepositoRequestDto dto) {
        try {
            String codiceAccesso = lockerService.deposita(dto.getNumBox());
            DepositoResponseDto response = new DepositoResponseDto("Deposito effettuato con successo", codiceAccesso);
            return ResponseEntity.ok(response);
        } catch (RuntimeException err) {
            DepositoResponseDto response = new DepositoResponseDto("Errore nel deposito: " + err.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/ritira")
    public ResponseEntity<RitiroResponseDto> ritira(@RequestBody @Valid RitiroRequestDto dto) {
        try {
            lockerService.ritira(dto.getCodiceAccesso());
            RitiroResponseDto response = new RitiroResponseDto("Ritiro effettuato con successo.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException err) {
            if (err.getMessage().equals("Codice non valido")) {
                RitiroResponseDto response = new RitiroResponseDto("Errore nel ritiro: " + err.getMessage());
                return ResponseEntity.status(404).body(response);
            }
            RitiroResponseDto response = new RitiroResponseDto("Errore nel ritiro: " + err.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/boxes")
    public ResponseEntity<UpdateBoxResponseDto> updateBox(@RequestBody @Valid UpdateBoxRequestDto dto) {
        try {
            lockerService.updateBox(dto.getNumBoxAttuale(), dto.getNuovoNumBox());
            UpdateBoxResponseDto response = new UpdateBoxResponseDto("Box aggiornato con successo.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            UpdateBoxResponseDto response = new UpdateBoxResponseDto("Errore: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/boxes/{numBox}")
    public ResponseEntity<Map<String, String>> deleteBox(@PathVariable Integer numBox) {
        try {
            lockerService.deleteBox(numBox);
            return ResponseEntity.ok(Map.of("messaggio", "Box eliminato con successo."));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("occupato")) {
                return ResponseEntity.badRequest().body(Map.of("errore", e.getMessage()));
            } else {
                return ResponseEntity.status(404).body(Map.of("errore", "Box non trovato."));
            }
        }
    }



}
