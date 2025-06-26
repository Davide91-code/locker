package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.model.BoxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

    Optional<Box> findByNumBox(Integer numBox);

    List<Box> findByStatus(BoxStatus status);

    @Query("SELECT b FROM Box b WHERE b.status = com.davideleonino.locker.model.BoxStatus.OCCUPIED ORDER BY b.numBox DESC")
    List<Box> findBoxOccupatiOrdinatiDecrescente();

    long count();

    long countByStatus(BoxStatus status);

    @Query(value = """
        SELECT b.*
        FROM box b
        JOIN statusbox o ON o.box_id = b.id
        WHERE b.status = 'OCCUPIED'
          AND o.tipo_operazione = 'DEPOSITO'
          AND o.data_orario = (
            SELECT MAX(o2.data_orario)
            FROM statusbox o2
            WHERE o2.box_id = b.id
              AND o2.tipo_operazione = 'DEPOSITO'
          )
          AND NOT EXISTS (
            SELECT 1
            FROM statusbox r
            WHERE r.box_id = b.id
              AND r.tipo_operazione = 'RITIRO'
              AND r.data_orario > o.data_orario
          )
        ORDER BY o.data_orario ASC
        """, nativeQuery = true)
    List<Box> findBoxOccupatiDaPiuTempo();

}
