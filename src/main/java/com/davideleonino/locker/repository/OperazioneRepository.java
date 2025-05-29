package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Operazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperazioneRepository extends JpaRepository<Operazione, Integer> {

    Optional<Operazione> findByCodiceAccesso(String codiceAcceso);
}
