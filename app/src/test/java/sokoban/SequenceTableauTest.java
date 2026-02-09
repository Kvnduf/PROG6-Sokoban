package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe SequenceTableau
 */
class SequenceTableauTest {
    private SequenceTableau sequence;

    @BeforeEach
    void setUp() {
        sequence = new SequenceTableau();
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
    void testDebordementInsereTete() {
        // La capacité maximale est 4
        sequence.insereTete(42);
        sequence.insereTete(42);
        sequence.insereTete(42);
        sequence.insereTete(42);
        
        // Le 5ème élément doit lancer une exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sequence.insereTete(42);
        });
        assertEquals("Séquence pleine", exception.getMessage());
    }

    @Test
    void testDebordementInsereQueue() {
        // La capacité maximale est 4
        sequence.insereQueue(42);
        sequence.insereQueue(42);
        sequence.insereQueue(42);
        sequence.insereQueue(42);
        
        // Le 5ème élément doit lancer une exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sequence.insereQueue(42);
        });
        assertEquals("Séquence pleine", exception.getMessage());
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
    void testCapaciteMaximale() {
        // Vérifie qu'on peut remplir exactement jusqu'à 4 éléments
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        sequence.insereQueue(4);
        
        assertEquals("[1, 2, 3, 4]", sequence.toString());
        
        // Mais pas 5
        assertThrows(RuntimeException.class, () -> {
            sequence.insereQueue(5);
        });
    }
}
