package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Operazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperazioneRepository extends JpaRepository<Operazione, Integer> {

    Optional<Operazione> findByCodiceAccesso(String codiceAcceso);

    @Query("SELECT o FROM Operazione o WHERE o.codiceAccesso = :codice AND o.tipoOperazione = 'DEPOSITO' ORDER BY o.dataOrario DESC")
    Optional<Operazione> findUltimoDepositoByCodice(@Param("codice") String codice);
}
