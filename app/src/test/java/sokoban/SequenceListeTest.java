package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe SequenceListe
 */
class SequenceListeTest {
    private SequenceListe sequence;

    @BeforeEach
    void setUp() {
        sequence = new SequenceListe();
    }

    @Test
    void testSequenceVideInitiale() {
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @Test
    void testInsereTeteUnElement() {
        sequence.insereTete(0);
        assertFalse(sequence.estVide());
        assertEquals("[0]", sequence.toString());
    }

    @Test
    void testExtraitTeteUnElement() {
        sequence.insereTete(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @Test
    void testInsereQueueUnElement() {
        sequence.insereQueue(0);
        assertFalse(sequence.estVide());
        assertEquals("[0]", sequence.toString());
    }

    @Test
    void testExtraitTeteApresInsereQueue() {
        sequence.insereQueue(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @Test
    void testInsereTetePlusieursElements() {
        sequence.insereTete(1);
        sequence.insereTete(2);
        sequence.insereTete(3);
        
        assertEquals("[3, 2, 1]", sequence.toString());
        assertEquals(3, sequence.extraitTete());
        assertEquals("[2, 1]", sequence.toString());
        assertEquals(2, sequence.extraitTete());
        assertEquals("[1]", sequence.toString());
        assertEquals(1, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testInsereQueuePlusieursElements() {
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        
        assertEquals("[1, 2, 3]", sequence.toString());
        assertEquals(1, sequence.extraitTete());
        assertEquals("[2, 3]", sequence.toString());
        assertEquals(2, sequence.extraitTete());
        assertEquals("[3]", sequence.toString());
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testExtraitTeteSequenceVide() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sequence.extraitTete();
        });
        assertEquals("Séquence vide", exception.getMessage());
    }

    @Test
    void testMelangeInsertionsTeteQueue() {
        sequence.insereTete(1);
        sequence.insereQueue(2);
        sequence.insereTete(0);
        sequence.insereQueue(3);
        
        assertEquals("[0, 1, 2, 3]", sequence.toString());
        
        assertEquals(0, sequence.extraitTete());
        assertEquals(1, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testInsertionsExtractionsAlternees() {
        sequence.insereTete(1);
        assertEquals(1, sequence.extraitTete());
        sequence.insereQueue(2);
        assertEquals(2, sequence.extraitTete());
        sequence.insereTete(3);
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testGrandeQuantiteElements() {
        // Insère beaucoup d'éléments (liste chaînée = pas de limite)
        for (int i = 0; i < 100; i++) {
            sequence.insereQueue(i);
        }
        
        // Vérifie l'extraction
        for (int i = 0; i < 100; i++) {
            assertEquals(i, sequence.extraitTete());
        }
        assertTrue(sequence.estVide());
    }
}
