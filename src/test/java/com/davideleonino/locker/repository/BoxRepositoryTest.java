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
}
