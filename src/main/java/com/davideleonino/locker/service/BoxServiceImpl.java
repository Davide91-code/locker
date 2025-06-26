package com.davideleonino.locker.service.impl;

import com.davideleonino.locker.dto.request.*;
import com.davideleonino.locker.dto.response.BoxDto;
import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.model.BoxStatus;
import com.davideleonino.locker.model.Operazione;
import com.davideleonino.locker.repository.BoxRepository;
import com.davideleonino.locker.repository.OperazioneRepository;
import com.davideleonino.locker.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final OperazioneRepository operazioneRepository;

    @Autowired
    public BoxServiceImpl(BoxRepository boxRepository, OperazioneRepository operazioneRepository) {
        this.boxRepository = boxRepository;
        this.operazioneRepository = operazioneRepository;
    }

    @Override
    public List<BoxDto> getAllBoxes() {
        return boxRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BoxDto getBoxById(Integer id) {
        Box box = boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box con ID " + id + " non trovato"));
        return convertToDto(box);
    }

    @Override
    public BoxDto createBox(BoxCreateRequestDto request) {
        if (boxRepository.findByNumBox(request.getNumBox()).isPresent()) {
            throw new RuntimeException("Box con numero " + request.getNumBox() + " già esistente");
        }

        Box box = new Box();
        box.setNumBox(request.getNumBox());
        box.setStatus(BoxStatus.FREE);

        boxRepository.save(box);
        return convertToDto(box);
    }

    @Override
    public BoxDto updateBox(BoxUpdateRequestDto request) {
        Box box = boxRepository.findByNumBox(request.getNumBoxAttuale())
                .orElseThrow(() -> new RuntimeException("Box con numero " + request.getNumBoxAttuale() + " non trovato"));

        if (!request.getNumBoxAttuale().equals(request.getNuovoNumBox())) {
            if (boxRepository.findByNumBox(request.getNuovoNumBox()).isPresent()) {
                throw new RuntimeException("Il nuovo numero di box è già in uso");
            }
            box.setNumBox(request.getNuovoNumBox());
        }

        boxRepository.save(box);
        return convertToDto(box);
    }

    @Override
    public void deleteBox(DeleteBoxDTO dto) {
        Box box = boxRepository.findByNumBox(dto.getNumBox())
                .orElseThrow(() -> new RuntimeException("Box con numero " + dto.getNumBox() + " non trovato"));
        boxRepository.delete(box);
    }

    @Override
    public List<BoxDto> getBoxesByStatus(String status) {
        BoxStatus boxStatus;
        try {
            boxStatus = BoxStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Stato non valido: " + status);
        }

        return boxRepository.findByStatus(boxStatus)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BoxDto deposito(DepositoRequestDto request) {
        Box box = boxRepository.findByNumBox(request.getNumBox())
                .orElseThrow(() -> new RuntimeException("Box non trovato"));

        if (box.getStatus() != BoxStatus.FREE) {
            throw new RuntimeException("Il box non è disponibile per il deposito");
        }

        box.setStatus(BoxStatus.OCCUPIED);
        boxRepository.save(box);

        Operazione operazione = new Operazione();
        operazione.setBoxAssociato(box);
        operazione.setTipoOperazione("DEPOSITO");
        operazione.setDataOrario(LocalDateTime.now());
        operazione.setCodiceAccesso(UUID.randomUUID().toString());

        operazioneRepository.save(o
