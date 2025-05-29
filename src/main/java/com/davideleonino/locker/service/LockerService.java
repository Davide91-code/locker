package com.davideleonino.locker.service;

import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.model.Operazione;
import com.davideleonino.locker.repository.BoxRepository;
import com.davideleonino.locker.repository.OperazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public  List<Box> getBoxUsatiOrdineD(){
        return  boxRepository.findBoxUsatiOrdineDecrescente();
    }

    public Box creaBox(Integer numBox) {
        if (boxRepository.findByNumBox(numBox).isPresent()) {
            throw new RuntimeException("Box con questo numero già esistente");
        }
        Box box = new Box(null, false, numBox);
        return boxRepository.save(box);
    }

    public Optional<Box> getBoxByNumBox(Integer numBox){
        return boxRepository.findByNumBox(numBox);
    }

    public String deposita(Integer numBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));

        if (box.isUsed()) {
            throw new RuntimeException("Box già occupato");
        }

        String codice = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
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

    public void ritira(String codiceAccesso) {
        Operazione op = operazioneRepository.findByCodiceAccesso(codiceAccesso)
                .orElseThrow(() -> new RuntimeException("Codice non valido"));

        Box box = op.getBoxAssociato();
        box.setUsed(false);
        boxRepository.save(box);

        Operazione operazioneRitiro = new Operazione();
        operazioneRitiro.setCodiceAccesso(codiceAccesso);
        operazioneRitiro.setTipoOperazione("RITIRO");
        operazioneRitiro.setDataOrario(LocalDateTime.now());
        operazioneRitiro.setBoxAssociato(box);

        operazioneRepository.save(operazioneRitiro);
    }

    public Box updateBox(Integer numBox, Integer nuovoNumBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));

        box.setNumBox(nuovoNumBox);
        return boxRepository.save(box);
    }

    public void deleteBox(Integer numBox) {
        Box box = boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato"));
        if (box.isUsed()) {
            throw new RuntimeException("Impossibile eliminare un box occupato");
        }

        boxRepository.delete(box);
    }


}