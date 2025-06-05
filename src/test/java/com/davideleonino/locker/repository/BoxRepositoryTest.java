package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Box;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // Assicura che venga usato application-test.properties
@Transactional  // Rollback automatico a fine test
public class BoxRepositoryTest {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    // test di salvataggio prova
    @Test
    void testSaveSimple() {
        Box box = new Box();
        box.setNumBox(1);
        box.setUsed(false);

        Box savedBox = boxRepository.save(box);

        assertNotNull(savedBox.getId());
        assertEquals(1, savedBox.getNumBox());
        assertFalse(savedBox.isUsed());

        // Recupero per sicurezza
        Optional<Box> found = boxRepository.findById(savedBox.getId());
        assertTrue(found.isPresent());
    }

    // test profili attivi
    @Test
    void testCheckActiveProfileAndDatabase() throws SQLException {
        // ✅ Verifica che il profilo "test" sia attivo
        System.out.println(">>> Profili attivi: " + Arrays.toString(environment.getActiveProfiles()));
        assertTrue(Arrays.asList(environment.getActiveProfiles()).contains("test"));

        // ✅ Verifica che si stia usando H2
        try (Connection conn = dataSource.getConnection()) {
            String dbUrl = conn.getMetaData().getURL();
            System.out.println(">>> Connessione al DB: " + dbUrl);
            assertTrue(dbUrl.contains("h2"), "Il test non sta usando H2 come database");
        }
    }

    // verifica se trova la box giusta
    @Test
    void testFindByNumBox() {
        Box box = new Box();
        box.setNumBox(7);
        box.setUsed(false);
        boxRepository.save(box);

        Optional<Box> found = boxRepository.findByNumBox(7);

        assertTrue(found.isPresent());
        assertEquals(7, found.get().getNumBox());
    }

    // trova tutti i box disponibili
    @Test
    void testFindByUsedFalse() {
        Box box1 = new Box(); box1.setNumBox(1); box1.setUsed(false);
        Box box2 = new Box(); box2.setNumBox(2); box2.setUsed(true);
        Box box3 = new Box(); box3.setNumBox(3); box3.setUsed(false);
        boxRepository.saveAll(List.of(box1, box2, box3));

        List<Box> freeBoxes = boxRepository.findByIsUsedFalse();

        assertEquals(2, freeBoxes.size());
        assertTrue(freeBoxes.stream().anyMatch(b -> b.getNumBox() == 1));
        assertTrue(freeBoxes.stream().anyMatch(b -> b.getNumBox() == 3));
    }

    //Ricerca di un box non esistente
    @Test
    void testFindByNumBox_NotFound() {
        Optional<Box> notFound = boxRepository.findByNumBox(999);
        assertTrue(notFound.isEmpty());
    }


    //Salvataggio e poi lettura
    @Test
    void testSaveAndReload() {
        Box box = new Box();
        box.setNumBox(42);
        box.setUsed(true);
        Box saved = boxRepository.save(box);

        Optional<Box> loaded = boxRepository.findById(saved.getId());

        assertTrue(loaded.isPresent());
        assertEquals(42, loaded.get().getNumBox());
        assertTrue(loaded.get().isUsed());
    }
}
