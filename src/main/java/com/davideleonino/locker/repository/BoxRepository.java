package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer>{

    Optional<Box> findByNumBox(Integer numBox);

    List<Box> findByIsUsedFalse();

    List<Box> findByIsUsedTrue();

    @Query("SELECT b FROM Box b WHERE b.isUsed = true ORDER BY b.numBox DESC")
    List<Box> findBoxUsatiOrdineDecrescente();
}

