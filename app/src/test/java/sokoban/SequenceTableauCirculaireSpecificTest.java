package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests spécifiques à SequenceTableauCirculaire.
 * Ces tests vérifient les comportements propres à cette implémentation :
 * - Redimensionnement automatique
 * - Comportement circulaire (wrap-around)
 * - Itération dans un tableau circulaire
 */
class SequenceTableauCirculaireSpecificTest {
    private SequenceTableauCirculaire sequence;

    @BeforeEach
    void setUp() {
        sequence = new SequenceTableauCirculaire();
    }

    @Test
    void testRedimensionnementAutomatique() throws Exception {
        // Insère 40 éléments en tête (dépassement de la capacité initiale de 4)
        for (int i = 0; i < 40; i++) {
            sequence.insereTete(i);
        }
        
        // Vérifie l'extraction dans le bon ordre (LIFO pour insereTete)
        for (int i = 39; i >= 0; i--) {
            assertEquals(i, sequence.extraitTete());
        }
        
        assertTrue(sequence.estVide());
    }

    @Test
    void testCapaciteInitialeEtCroissance() throws Exception {
        // Capacité initiale = 4, max utilisable = 3
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        
        // Ajouter un 4ème devrait déclencher le redimensionnement
        sequence.insereQueue(4);
        
        // Vérifie que tout est bien en place
        assertEquals(1, sequence.extraitTete());
        assertEquals(2, sequence.extraitTete());
        assertEquals(3, sequence.extraitTete());
        assertEquals(4, sequence.extraitTete());
    }

    @Test
    void testRedimensionnementAvecChevauchement() throws Exception {
        // Scénario où e < s (le tableau est circulaire - wrap-around)
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
            
            for (int i = 0; i < 5; i++) {
                assertDoesNotThrow(() -> sequence.extraitTete());
            }
            assertTrue(sequence.estVide());
        }
    }

    @Test
    void testIterateurAvecWrapAround() throws Exception {
        // Créer situation de wrap-around
        for (int i = 1; i <= 3; i++) {
            sequence.insereQueue(i);
        }
        sequence.extraitTete(); // Retire 1
        sequence.extraitTete(); // Retire 2
        
        for (int i = 4; i <= 6; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        int[] expected = {3, 4, 5, 6};
        int index = 0;
        
        while (it.aProchain()) {
            assertEquals(expected[index++], it.prochain());
        }
        assertEquals(4, index, "L'itérateur devrait avoir parcouru 4 éléments");
    }

    @Test
    void testIterateurSuppressionAvecWrapAround() throws Exception {
        // Créer situation de wrap-around
        for (int i = 1; i <= 3; i++) {
            sequence.insereQueue(i);
        }
        sequence.extraitTete();
        sequence.extraitTete();
        
        for (int i = 4; i <= 6; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        it.prochain(); // 3
        it.prochain(); // 4
        it.supprime();
        
        assertEquals("[3, 5, 6]", sequence.toString());
    }

    @Test
    void testInsertionsExtractionsAlternees() throws Exception {
        // Test spécifique au tableau circulaire pour vérifier
        // que les indices s et e se déplacent correctement
        for (int i = 0; i < 10; i++) {
            sequence.insereQueue(i);
            assertEquals(i, sequence.extraitTete());
            assertTrue(sequence.estVide());
        }
    }

    @Test
    void testRedimensionnementMultiple() throws Exception {
        // Force plusieurs redimensionnements successifs
        // Capacité initiale: 4, puis 8, 16, 32, 64, 128
        for (int i = 0; i < 100; i++) {
            sequence.insereQueue(i);
        }
        
        // Vérifie l'ordre
        for (int i = 0; i < 100; i++) {
            assertEquals(i, sequence.extraitTete());
        }
        
        assertTrue(sequence.estVide());
    }

    @Test
    void testWrapAroundAvecInsereTete() throws Exception {
        // Test wrap-around avec insertions en tête
        sequence.insereQueue(1);
        sequence.insereQueue(2);
        sequence.insereQueue(3);
        
        sequence.extraitTete(); // s se déplace
        sequence.extraitTete(); // s se déplace encore
        
        // Maintenant on insère en tête, s devrait wrap-around
        sequence.insereTete(10);
        sequence.insereTete(20);
        
        assertEquals(20, sequence.extraitTete());
        assertEquals(10, sequence.extraitTete());
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @Test
    void testMelangeInsertionsAvecRedimensionnement() throws Exception {
        // Mélange d'insertions tête/queue avec redimensionnement
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                sequence.insereTete(i);
            } else {
                sequence.insereQueue(i);
            }
        }
        
        assertFalse(sequence.estVide());
        
        // Vide complètement
        int count = 0;
        while (!sequence.estVide()) {
            sequence.extraitTete();
            count++;
        }
        
        assertEquals(20, count);
    }
}
