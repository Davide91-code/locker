package com.davideleonino.locker.service;

import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.model.Operazione;
import com.davideleonino.locker.repository.BoxRepository;
import com.davideleonino.locker.repository.OperazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class LockerService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private OperazioneRepository operazioneRepository;


    //query totale
    public List<Box> getAllBoxes() {
        return boxRepository.findAll();
    }

    // query sui box liberi
    public List<Box> getBoxLibero(){
        return boxRepository.findByIsUsedFalse();
    }

    //query sui box occupati
    public List<Box> getBoxOccupati(){
        return boxRepository.findByIsUsedTrue();
    }

    //query box usati in ordine decrecs
    public  List<Box> getBoxOccupatiOrdinatiD(){
        return  boxRepository.findBoxOccupatiOrdinatiDecrescente();
    }

    public Map<String, Object> getStatisticheBox() {
        long totale = boxRepository.countAllBoxes();
        long liberi = boxRepository.countByIsUsedFalse();
        long occupati = boxRepository.countByIsUsedTrue();
        double percentualeOccupazione = totale > 0 ? (occupati * 100.0) / totale : 0.0;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totaleBox", totale);
        stats.put("boxLiberi", liberi);
        stats.put("boxOccupati", occupati);
        stats.put("percentualeOccupazione", percentualeOccupazione);

        return stats;
    }

    public List<Box> getBoxOccupatiDaPiuTempo() {
        return boxRepository.findBoxOccupatiDaPiuTempo();
    }


    @Transactional
    public Box creaBox(Integer numBox) {
        if (boxRepository.findByNumBox(numBox).isPresent()) {
            throw new RuntimeException("Box con questo numero già esistente");
        }
        Box box = new Box(null, false, numBox);
        return boxRepository.save(box);
    }

    /*
    public Optional<Box> getBoxByNumBox(Integer numBox){
        return boxRepository.findByNumBox(numBox);
    }

     */

    public Box getBoxByNumBox(Integer numBox) {
        return boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box con numero " + numBox + " non trovato"));
    }

    @Transactional
    public List<Box> creaBoxesDaRange(Integer start, Integer end) {
        if (start > end) {
            throw new RuntimeException("Start non può essere maggiore di end");
        }

        List<Box> boxesCreati = new ArrayList<>();
        for (int num = start; num <= end; num++) {
            if (boxRepository.findByNumBox(num).isPresent()) {
                throw new RuntimeException("Box con numero " + num + " già esistente");
            }
            Box box = new Box(null, false, num);
            boxesCreati.add(boxRepository.save(box));
        }
        return boxesCreati;
    }

    @Transactional
    public String deposita(Integer numBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));

        if (box.isUsed()) {
            throw new RuntimeException("Box già occupato");
        }

        String codice = generaCodiceAccesso();
        box.setUsed(true);
        boxRepository.save(box);

        Operazione op = new Operazione();
        op.setCodiceAccesso(codice);
        op.setTipoOperazione("DEPOSITO");
        op.setDataOrario(LocalDateTime.now());
        op.setBoxAssociato(box);

        operazioneRepository.save(op);

        return codice;
    }

    @Transactional
    public void ritira(String codiceAccesso) {
        Operazione op = operazioneRepository.findByCodiceAccesso(codiceAccesso)
                .orElseThrow(() -> new RuntimeException("Codice non valido"));

        Box box = op.getBoxAssociato();

        if (!box.isUsed()) {
            throw new RuntimeException("Box già ritirato.");
        }

        box.setUsed(false);
        boxRepository.save(box);

        Operazione operazioneRitiro = new Operazione();
        operazioneRitiro.setCodiceAccesso(codiceAccesso);
        operazioneRitiro.setTipoOperazione("RITIRO");
        operazioneRitiro.setDataOrario(LocalDateTime.now());
        operazioneRitiro.setBoxAssociato(box);

        operazioneRepository.save(operazioneRitiro);
    }

    @Transactional
    public Box updateBox(Integer numBox, Integer nuovoNumBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));

        if (boxRepository.findByNumBox(nuovoNumBox).isPresent()) {
            throw new RuntimeException("Numero già esistente.");
        }
        box.setNumBox(nuovoNumBox);

        return boxRepository.save(box);
    }

    @Transactional
    public void deleteBox(Integer numBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));
        if (box.isUsed()) {
            throw new RuntimeException("Impossibile eliminare un box occupato");
        }

        boxRepository.delete(box);
    }

    @Transactional
    public void deleteBoxesDaRange(Integer start, Integer end) {
        if (start > end) {
            throw new RuntimeException("Start non può essere maggiore di end");
        }

        // IntStream.rangeClosed genera i numeri da start a end inclusi
        IntStream.rangeClosed(start, end).forEach(num -> {
            Box box = boxRepository.findByNumBox(num)
                    .orElseThrow(() -> new RuntimeException("Box non trovato: " + num));
            if (box.isUsed()) {
                throw new RuntimeException("Impossibile eliminare box occupato: " + num);
            }
            boxRepository.delete(box);
        });
    }

    private String generaCodiceAccesso() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }


}