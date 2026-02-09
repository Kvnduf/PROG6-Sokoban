package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe SequenceTableauCirculaire
 */
class SequenceTableauCirculaireTest {
    private SequenceTableauCirculaire sequence;

    @BeforeEach
    void setUp() {
        sequence = new SequenceTableauCirculaire();
    }

    @Test
    void testSequenceVideInitiale() {
        assertTrue(sequence.estVide());
        assertEquals(0, sequence.taille());
    }

    @Test
    void testInsereTeteUnElement() {
        sequence.insereTete(0);
        assertFalse(sequence.estVide());
        assertEquals(1, sequence.taille());
    }

    @Test
    void testExtraitTeteUnElement() {
        sequence.insereTete(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
        assertEquals(0, sequence.taille());
    }

    @Test
    void testInsereQueueUnElement() {
        sequence.insereQueue(0);
        assertFalse(sequence.estVide());
        assertEquals(1, sequence.taille());
    }

    @Test
    void testExtraitTeteApresInsereQueue() {
        sequence.insereQueue(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testInsereTetePlusieursElements() {
        sequence.insereTete(1);
        sequence.insereTete(2);
        sequence.insereTete(3);
        
        assertEquals(3, sequence.taille());
        assertEquals(3, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
        assertEquals(1, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testInsereQueuePlusieursElements() {
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        
        assertEquals(3, sequence.taille());
        assertEquals(1, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
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
        
        assertEquals(4, sequence.taille());
        assertEquals(0, sequence.extraitTete());
        assertEquals(1, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testInsertionsExtractionsAlternees() {
        // Test comme dans MesTestsSequence
        for (int i = 0; i < 10; i++) {
            sequence.insereQueue(i);
            assertEquals(i, sequence.extraitTete());
            assertTrue(sequence.estVide());
        }
    }

    @Test
    void testRedimensionnementAutomatique() {
        // Insère 40 éléments en tête (dépassement de la capacité initiale de 4)
        for (int i = 0; i < 40; i++) {
            sequence.insereTete(i);
        }
        
        assertEquals(40, sequence.taille());
        
        // Vérifie l'extraction dans le bon ordre (LIFO pour insereTete)
        for (int i = 39; i >= 0; i--) {
            assertEquals(i, sequence.extraitTete());
        }
        
        assertTrue(sequence.estVide());
    }

    @Test
    void testGrandeQuantiteElementsQueue() {
        // Insère 100 éléments en queue
        for (int i = 0; i < 100; i++) {
            sequence.insereQueue(i);
        }
        
        assertEquals(100, sequence.taille());
        
        // Vérifie l'extraction dans le bon ordre (FIFO)
        for (int i = 0; i < 100; i++) {
            assertEquals(i, sequence.extraitTete());
        }
        
        assertTrue(sequence.estVide());
    }

    @Test
    void testCapaciteInitialeEtCroissance() {
        // Capacité initiale = 4, max utilisable = 3
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        assertEquals(3, sequence.taille());
        
        // Ajouter un 4ème devrait déclencher le redimensionnement
        sequence.insereQueue(4);
        assertEquals(4, sequence.taille());
        
        // Vérifie que tout est bien en place
        assertEquals(1, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
        assertEquals(3, sequence.extraitTete());
        assertEquals(4, sequence.extraitTete());
    }

    @Test
    void testRedimensionnementAvecChevauchement() {
        // Scénario où e < s (le tableau est circulaire)
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        
        // Extrait quelques éléments pour décaler s
        sequence.extraitTete();
        sequence.extraitTete();
        
        // Insère beaucoup d'éléments pour forcer un redimensionnement
        for (int i = 4; i <= 10; i++) {
            sequence.insereQueue(i);
        }
        
        // Vérifie que tout est correct
        assertEquals(3, sequence.extraitTete()); // Le 3 était resté
        for (int i = 4; i <= 10; i++) {
            assertEquals(i, sequence.extraitTete());
        }
        assertTrue(sequence.estVide());
    }

    @Test
    void testComportementCirculaire() {
        // Rempli, vide, rempli à nouveau pour tester le comportement circulaire
        for (int cycle = 0; cycle < 3; cycle++) {
            for (int i = 0; i < 5; i++) {
                sequence.insereQueue(i);
            }
            assertEquals(5, sequence.taille());
            
            for (int i = 0; i < 5; i++) {
                assertEquals(i, sequence.extraitTete());
            }
            assertTrue(sequence.estVide());
        }
    }
}
