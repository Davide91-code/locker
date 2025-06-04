package com.davideleonino.locker.service;

import com.davideleonino.locker.model.Box;
import com.davideleonino.locker.repository.BoxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;       // per assertNotNull, assertEquals, assertFalse
import static org.mockito.Mockito.*;                    // per when, verify, times


import java.util.List;
import java.util.Optional;

public class BoxServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxService boxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // inizializza i mock
    }

    @Test
    void testFindById() {
        Box mockBox = new Box();
        mockBox.setId(99);
        mockBox.setNumBox(10);
        mockBox.setUsed(false);

        when(boxRepository.findById(99)).thenReturn(Optional.of(mockBox));

        Box found = boxService.findById(99);

        assertNotNull(found);
        assertEquals(10, found.getNumBox());
        assertFalse(found.isUsed());

        verify(boxRepository, times(1)).findById(99);
    }

    @Test
    void testFindByNumBox() {
        Box mockBox = new Box(1, false, 10);

        when(boxRepository.findByNumBox(10)).thenReturn(Optional.of(mockBox));

        Box found = boxService.findByNumBox(10);

        assertNotNull(found);
        assertEquals(10, found.getNumBox());
        assertFalse(found.isUsed());

        verify(boxRepository, times(1)).findByNumBox(10);
    }

    @Test
    void testFindByIsUsedFalse() {
        Box box1 = new Box(1, false, 10);
        Box box2 = new Box(2, false, 11);

        List<Box> mockList = List.of(box1, box2);

        when(boxRepository.findByIsUsedFalse()).thenReturn(mockList);

        List<Box> foundList = boxService.findByIsUsedFalse();

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertFalse(foundList.get(0).isUsed());
        assertFalse(foundList.get(1).isUsed());

        verify(boxRepository, times(1)).findByIsUsedFalse();
    }

    @Test
    void testFindByIsUsedTrue() {
        List<Box> mockList = List.of(new Box(3, true, 12), new Box(4, true, 13));
        when(boxRepository.findByIsUsedTrue()).thenReturn(mockList);

        List<Box> foundList = boxService.findByIsUsedTrue();

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertTrue(foundList.get(0).isUsed());
        assertTrue(foundList.get(1).isUsed());
        verify(boxRepository, times(1)).findByIsUsedTrue();
    }

    @Test
    void testFindBoxOccupatiOrdinatiDecrescente() {
        List<Box> mockList = List.of(new Box(5, true, 20), new Box(6, true, 18));
        when(boxRepository.findBoxOccupatiOrdinatiDecrescente()).thenReturn(mockList);

        List<Box> foundList = boxService.findBoxOccupatiOrdinatiDecrescente();

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertTrue(foundList.get(0).isUsed());
        verify(boxRepository, times(1)).findBoxOccupatiOrdinatiDecrescente();
    }

    @Test
    void testCountAllBoxes() {
        when(boxRepository.countAllBoxes()).thenReturn(10L);

        long count = boxService.countAllBoxes();

        assertEquals(10L, count);
        verify(boxRepository, times(1)).countAllBoxes();
    }

    @Test
    void testCountByIsUsedFalse() {
        when(boxRepository.countByIsUsedFalse()).thenReturn(4L);

        long count = boxService.countByIsUsedFalse();

        assertEquals(4L, count);
        verify(boxRepository, times(1)).countByIsUsedFalse();
    }

    @Test
    void testCountByIsUsedTrue() {
        when(boxRepository.countByIsUsedTrue()).thenReturn(6L);

        long count = boxService.countByIsUsedTrue();

        assertEquals(6L, count);
        verify(boxRepository, times(1)).countByIsUsedTrue();
    }

    @Test
    void testFindBoxOccupatiDaPiuTempo() {
        List<Box> mockList = List.of(new Box(7, true, 15), new Box(8, true, 16));
        when(boxRepository.findBoxOccupatiDaPiuTempo()).thenReturn(mockList);

        List<Box> foundList = boxService.findBoxOccupatiDaPiuTempo();

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertTrue(foundList.get(0).isUsed());
        verify(boxRepository, times(1)).findBoxOccupatiDaPiuTempo();
    }
}



