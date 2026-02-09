package sokoban;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Tests paramétrés pour l'interface Sequence.
 * Teste toutes les implémentations avec les mêmes scénarios.
 */
class SequenceTest {

    /**
     * Fournit toutes les implémentations de Sequence à tester
     */
    static Stream<Sequence> sequenceProvider() {
        return Stream.of(
            new SequenceListe(),
            new SequenceTableauCirculaire()
        );
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testSequenceVideInitiale(Sequence sequence) {
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testInsereTeteUnElement(Sequence sequence) {
        sequence.insereTete(0);
        assertFalse(sequence.estVide());
        assertEquals("[0]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testExtraitTeteUnElement(Sequence sequence) throws Exception {
        sequence.insereTete(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testInsereQueueUnElement(Sequence sequence) {
        sequence.insereQueue(0);
        assertFalse(sequence.estVide());
        assertEquals("[0]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testExtraitTeteApresInsereQueue(Sequence sequence) throws Exception {
        sequence.insereQueue(0);
        assertEquals(0, sequence.extraitTete());
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testInsereTetePlusieursElements(Sequence sequence) throws Exception {
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

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testInsereQueuePlusieursElements(Sequence sequence) throws Exception {
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

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testExtraitTeteSequenceVide(Sequence sequence) {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sequence.extraitTete();
        });
        assertEquals("Séquence vide", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testMelangeInsertionsTeteQueue(Sequence sequence) throws Exception {
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

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testInsertionsExtractionsAlternees(Sequence sequence) throws Exception {
        sequence.insereTete(1);
        assertEquals(1, sequence.extraitTete());
        sequence.insereQueue(2);
        assertEquals(2, sequence.extraitTete());
        sequence.insereTete(3);
        assertEquals(3, sequence.extraitTete());
        assertTrue(sequence.estVide());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testGrandeQuantiteElements(Sequence sequence) {
        // Insère beaucoup d'éléments
        for (int i = 0; i < 100; i++) {
            sequence.insereQueue(i);
        }
        
        // Vérifie l'extraction
        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(() -> sequence.extraitTete());
        }
        assertTrue(sequence.estVide());
    }

    // ==================== TESTS ITÉRATEUR ====================
    
    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSequenceVide(Sequence sequence) {
        Iterateur it = sequence.iterateur();
        assertFalse(it.aProchain());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurParcours(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        int[] expected = {1, 2, 3, 4, 5};
        int index = 0;
        
        while (it.aProchain()) {
            assertEquals(expected[index++], it.prochain());
        }
        assertEquals(5, index);
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateursIndependants(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it1 = sequence.iterateur();
        Iterateur it2 = sequence.iterateur();
        
        assertEquals(1, it1.prochain());
        assertEquals(2, it1.prochain());
        assertEquals(1, it2.prochain());
        assertEquals(3, it1.prochain());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurExceptionProchainSansElement(Sequence sequence) {
        for (int i = 1; i <= 3; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        it.prochain();
        it.prochain();
        it.prochain();
        
        assertThrows(NoSuchElementException.class, () -> {
            it.prochain();
        });
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurApresInsertionTete(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereTete(i);
        }
        
        Iterateur it = sequence.iterateur();
        int[] expected = {5, 4, 3, 2, 1};
        int index = 0;
        
        while (it.aProchain()) {
            assertEquals(expected[index++], it.prochain());
        }
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSuppression(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        it.prochain(); // 1
        it.supprime();
        
        assertEquals("[2, 3, 4, 5]", sequence.toString());
        
        it.prochain(); // 2
        it.prochain(); // 3
        it.supprime();
        
        assertEquals("[2, 4, 5]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSuppressionImpairs(Sequence sequence) {
        for (int i = 1; i <= 10; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        while (it.aProchain()) {
            int val = it.prochain();
            if (val % 2 == 1) {
                it.supprime();
            }
        }
        
        assertEquals("[2, 4, 6, 8, 10]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSuppressionTete(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        it.prochain();
        it.supprime();
        
        assertEquals("[2, 3, 4, 5]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSuppressionQueue(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        while (it.aProchain()) {
            int val = it.prochain();
            if (val == 5) {
                it.supprime();
            }
        }
        
        assertEquals("[1, 2, 3, 4]", sequence.toString());
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurExceptionDoubleSuppression(Sequence sequence) {
        for (int i = 1; i <= 3; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        it.prochain();
        it.supprime();
        
        assertThrows(IllegalStateException.class, () -> {
            it.supprime();
        });
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurExceptionSuppressionAvantProchain(Sequence sequence) {
        for (int i = 1; i <= 3; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        assertThrows(IllegalStateException.class, () -> {
            it.supprime();
        });
    }

    @ParameterizedTest
    @MethodSource("sequenceProvider")
    void testIterateurSuppressionTousElements(Sequence sequence) {
        for (int i = 1; i <= 5; i++) {
            sequence.insereQueue(i);
        }
        
        Iterateur it = sequence.iterateur();
        while (it.aProchain()) {
            it.prochain();
            it.supprime();
        }
        
        assertTrue(sequence.estVide());
        assertEquals("[]", sequence.toString());
    }
}
