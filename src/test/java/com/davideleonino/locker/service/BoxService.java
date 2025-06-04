package com.davideleonino.locker.service;


import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    public Box findById(int id) {
        return boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box non trovato con id: " + id));
    }

    public Box findByNumBox(int numBox) {
        return boxRepository.findByNumBox(numBox)
                .orElseThrow(() -> new RuntimeException("Box non trovato con numBox: " + numBox));
    }

    public List<Box> findByIsUsedFalse() {
        return boxRepository.findByIsUsedFalse();
    }

    public List<Box> findByIsUsedTrue() {
        return boxRepository.findByIsUsedTrue();
    }

    public List<Box> findBoxOccupatiOrdinatiDecrescente() {
        return boxRepository.findBoxOccupatiOrdinatiDecrescente();
    }

    public long countAllBoxes() {
        return boxRepository.countAllBoxes();
    }

    public long countByIsUsedFalse() {
        return boxRepository.countByIsUsedFalse();
    }

    public long countByIsUsedTrue() {
        return boxRepository.countByIsUsedTrue();
    }

    public List<Box> findBoxOccupatiDaPiuTempo() {
        return boxRepository.findBoxOccupatiDaPiuTempo();
    }
}
