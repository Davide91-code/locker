package com.davideleonino.locker.controller;

import com.davideleonino.locker.DTO.*;
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
    public ResponseEntity<Box> creaBox(@RequestBody @Valid CreaBoxDTO dto) {
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
    public ResponseEntity <List<Box>> getBoxUsatiOrdineD(){
        return  ResponseEntity.ok(lockerService.getBoxUsatiOrdineD());
    }

    @GetMapping("/boxes/{numBox}")
    public ResponseEntity<Box> getBoxByNumBox(@PathVariable Integer numBox) {
        return lockerService.getBoxByNumBox(numBox)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/deposita")
    public ResponseEntity<String> deposita(@RequestBody @Valid DepositoDTO dto) {
        try {
            String codiceAccesso = lockerService.deposita(dto.getNumBox());
            return ResponseEntity.ok("Deposito effettuato. Codice di accesso per il ritiro:" + codiceAccesso);
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body("Errore nel deposito: " + err.getMessage());
        }

    }


    @PostMapping("/ritira")
    public ResponseEntity<String> ritira(@RequestBody @Valid RitiroDTO dto){
        try {
            lockerService.ritira(dto.getCodiceAccesso());
            return ResponseEntity.ok("Ritiro effettuato con successo.");
        } catch (RuntimeException err) {

            if (err.getMessage().equals("Codice non valido")) {
                return ResponseEntity.status(404).body("Errore nel ritiro: " + err.getMessage());
            }

            return ResponseEntity.badRequest().body("Errore nel ritiro: " + err.getMessage());
        }
    }

    @PutMapping("/boxes")
    public ResponseEntity<Map<String, String>> updateBox(@RequestBody @Valid UpdateBoxDTO dto) {
        try {
            lockerService.updateBox(dto.getNumBoxAttuale(), dto.getNuovoNumBox());
            return ResponseEntity.ok(Map.of("messaggio", "Box aggiornato con successo."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("errore", e.getMessage()));
        }
    }

    @DeleteMapping("/boxes/{numBox}")
    public ResponseEntity<Map<String, String>>  deleteBox(@PathVariable Integer numBox) {
        try {
            lockerService.deleteBox(numBox);
            return ResponseEntity.ok(Map.of("messaggio", "Box eliminato con successo."));
        } catch (RuntimeException e) {
            return e.getMessage().contains("occupato")
                    ? ResponseEntity.badRequest().body(Map.of("errore", e.getMessage()))
                    : ResponseEntity.status(404).body(Map.of("errore", "Box non trovato."));
        }
    }


}
