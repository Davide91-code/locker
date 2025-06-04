package com.davideleonino.locker.repository;

import com.davideleonino.locker.model.Box;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoxRepositoryMockTest {

    private BoxRepository boxRepository;

    @BeforeEach
    void setUp() {
        // Crea un mock del repository
        boxRepository = mock(BoxRepository.class);
    }

    @Test
    void testSaveBoxWithMock() {
        // Box con valori non validi per il database, ma validi per un mock
        Box inputBox = new Box();
        // Non impostiamo numBox volutamente

        // Risposta simulata: Hibernate non verrà chiamato, non serve un ID vero
        Box mockSavedBox = new Box();
        mockSavedBox.setId(98);
        mockSavedBox.setNumBox(null);  // Non causa errore perché è un mock
        mockSavedBox.setUsed(false);

        // Diciamo al mock come comportarsi
        when(boxRepository.save(inputBox)).thenReturn(mockSavedBox);

        // Azione
        Box result = boxRepository.save(inputBox);

        // Verifica
        assertNotNull(result);
        assertEquals(98, result.getId());
        assertNull(result.getNumBox());
        assertFalse(result.isUsed());

        // Debug visivo
        System.out.println("MockBox salvato: ID=" + result.getId() + ", numBox=" + result.getNumBox());
    }
}