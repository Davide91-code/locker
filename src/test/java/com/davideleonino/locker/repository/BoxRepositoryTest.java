package com.davideleonino.locker.repository;


import com.davideleonino.locker.model.Box;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // <-- questa riga forza il profilo "test"
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class BoxRepositoryTest {

    @Autowired
    private BoxRepository boxRepository;


    /*
    @Test
    void testSaveAndFindByNumBox() {
        Box box = new Box(null, false, 101);
        boxRepository.save(box);

        Optional<Box> result = boxRepository.findByNumBox(101);
        assertTrue(result.isPresent());
        assertEquals(101, result.get().getNumBox());
    }

    @Test
    void testFindByIsUsedFalse() {
        Box libero = new Box(null, false, 201);
        Box usato = new Box(null, true, 202);

        boxRepository.save(libero);
        boxRepository.save(usato);

        List<Box> liberi = boxRepository.findByIsUsedFalse();
        assertEquals(1, liberi.size());
        assertFalse(liberi.get(0).isUsed());
    }

    @Test
    void testCountByIsUsedTrue() {
        boxRepository.save(new Box(null, true, 301));
        boxRepository.save(new Box(null, true, 302));
        boxRepository.save(new Box(null, false, 303));

        long count = boxRepository.countByIsUsedTrue();
        assertEquals(2, count);
    }

     */

    @Test
    void testSaveSimple() {
        Box box = new Box();
        box.setNumBox(1);
        box.setUsed(false);  // imposta anche isUsed

        Box savedBox = boxRepository.save(box);

        assertNotNull(savedBox.getId());
        assertEquals(1, savedBox.getNumBox());
        assertFalse(savedBox.isUsed());
    }
}