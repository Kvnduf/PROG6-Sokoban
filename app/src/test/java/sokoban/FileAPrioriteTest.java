package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

import sokoban.Structures.FileAPriorite;

/**
 * Tests pour la classe FileAPriorite
 */
class FileAPrioriteTest {
    private FileAPriorite<String, Integer> file;

    @BeforeEach
    void setUp() {
        file = new FileAPriorite<>();
    }

    @Test
    void testFileVide() {
        assertTrue(file.estVide());
        assertEquals("[]", file.toString());
    }

    @Test
    void testEnfileUnElement() {
        file.enfile("A", 5);
        assertFalse(file.estVide());
        assertTrue(file.toString().contains("A"));
    }

    @Test
    void testDefileUnElement() {
        file.enfile("A", 5);
        String val = file.defile();
        assertEquals("A", val);
        assertTrue(file.estVide());
        assertEquals("[]", file.toString());
    }

    @Test
    void testEnfilePlusieursElementsAvecPrioritesDifferentes() {
        file.enfile("Basse", 1);
        file.enfile("Haute", 10);
        file.enfile("Moyenne", 5);
        file.enfile("Critique", 20);
        
        assertFalse(file.estVide());
    }

    @Test
    void testDefilePrioriteMaximale() {
        file.enfile("Basse", 1);
        file.enfile("Haute", 10);
        file.enfile("Moyenne", 5);
        file.enfile("Critique", 20);
        
        String val = file.defile();
        assertEquals("Critique", val, "Devrait retourner l'élément de priorité maximale (20)");
    }

    @Test
    void testDefileOrdrePriorite() {
        file.enfile("Basse", 1);
        file.enfile("Haute", 10);
        file.enfile("Moyenne", 5);
        file.enfile("Critique", 20);
        
        assertEquals("Critique", file.defile()); // priorité 20
        assertEquals("Haute", file.defile());    // priorité 10
        assertEquals("Moyenne", file.defile());  // priorité 5
        assertEquals("Basse", file.defile());    // priorité 1
        assertTrue(file.estVide());
    }

    @Test
    void testOrdrePrioriteDecroissant() {
        file.enfile("P10", 10);
        file.enfile("P8", 8);
        file.enfile("P6", 6);
        file.enfile("P4", 4);
        file.enfile("P2", 2);
        
        assertEquals("P10", file.defile());
        assertEquals("P8", file.defile());
        assertEquals("P6", file.defile());
        assertEquals("P4", file.defile());
        assertEquals("P2", file.defile());
        assertTrue(file.estVide());
    }

    @Test
    void testPrioritesIdentiques() {
        file.enfile("Premier", 5);
        file.enfile("Deuxième", 5);
        file.enfile("Troisième", 5);
        
        // Avec même priorité, l'ordre de sortie dépend de l'implémentation
        // On vérifie juste qu'on peut les retirer tous
        String val1 = file.defile();
        String val2 = file.defile();
        String val3 = file.defile();
        
        assertNotNull(val1);
        assertNotNull(val2);
        assertNotNull(val3);
        assertTrue(file.estVide());
    }

    @Test
    void testPrioritesDansLeDesordre() {
        file.enfile("E1", 3);
        file.enfile("E2", 7);
        file.enfile("E3", 1);
        file.enfile("E4", 9);
        file.enfile("E5", 2);
        file.enfile("E6", 8);
        file.enfile("E7", 4);
        
        // Vérifie l'ordre décroissant des priorités
        assertEquals("E4", file.defile()); // 9
        assertEquals("E6", file.defile()); // 8
        assertEquals("E2", file.defile()); // 7
        assertEquals("E7", file.defile()); // 4
        assertEquals("E1", file.defile()); // 3
        assertEquals("E5", file.defile()); // 2
        assertEquals("E3", file.defile()); // 1
        assertTrue(file.estVide());
    }

    @Test
    void testAlternanceEnfileDefile() {
        file.enfile("A", 10);
        file.enfile("B", 20);
        file.enfile("C", 15);
        
        assertEquals("B", file.defile()); // priorité 20
        
        file.enfile("D", 25);
        file.enfile("E", 5);
        
        assertEquals("D", file.defile()); // priorité 25
        assertEquals("C", file.defile()); // priorité 15
        assertEquals("A", file.defile()); // priorité 10
        assertEquals("E", file.defile()); // priorité 5
        assertTrue(file.estVide());
    }

    @Test
    void testPrioritesNegativesEtPositives() {
        file.enfile("Neg5", -5);
        file.enfile("Pos10", 10);
        file.enfile("Neg3", -3);
        file.enfile("Zero", 0);
        file.enfile("Pos7", 7);
        file.enfile("Neg10", -10);
        file.enfile("Pos3", 3);
        
        assertEquals("Pos10", file.defile());  // 10
        assertEquals("Pos7", file.defile());   // 7
        assertEquals("Pos3", file.defile());   // 3
        assertEquals("Zero", file.defile());   // 0
        assertEquals("Neg3", file.defile());   // -3
        assertEquals("Neg5", file.defile());   // -5
        assertEquals("Neg10", file.defile());  // -10
        assertTrue(file.estVide());
    }

    @Test
    void testSimulationFileAttenteUrgences() {
        file.enfile("Patient-Fracture", 3);
        file.enfile("Patient-ArrêtCardiaque", 10);
        file.enfile("Patient-Grippe", 1);
        file.enfile("Patient-Hémorragie", 8);
        file.enfile("Patient-Migraine", 2);
        
        // Ordre de traitement par priorité décroissante
        assertEquals("Patient-ArrêtCardiaque", file.defile());
        assertEquals("Patient-Hémorragie", file.defile());
        assertEquals("Patient-Fracture", file.defile());
        assertEquals("Patient-Migraine", file.defile());
        assertEquals("Patient-Grippe", file.defile());
        assertTrue(file.estVide());
    }

    @Test
    void testAvecBeaucoupElements() {
        // Ajoute 20 éléments
        for (int i = 1; i <= 20; i++) {
            file.enfile("Elem" + i, i * 3 % 17);
        }
        
        assertFalse(file.estVide());
        
        // Défile 5 éléments
        for (int i = 0; i < 5; i++) {
            String val = file.defile();
            assertNotNull(val);
        }
        
        assertFalse(file.estVide());
    }

    @Test
    void testExceptionFileVide() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            file.defile();
        });
        assertTrue(exception.getMessage().contains("vide") || 
                   exception.getMessage().contains("empty"));
    }

    @Test
    void testTypesGeneriquesIntegerDouble() {
        FileAPriorite<Integer, Double> fNumeric = new FileAPriorite<>();
        fNumeric.enfile(100, 1.5);
        fNumeric.enfile(200, 3.7);
        fNumeric.enfile(300, 2.1);
        fNumeric.enfile(400, 5.9);
        fNumeric.enfile(500, 0.5);
        
        assertEquals(400, fNumeric.defile()); // priorité 5.9
        assertEquals(200, fNumeric.defile()); // priorité 3.7
        assertEquals(300, fNumeric.defile()); // priorité 2.1
        assertEquals(100, fNumeric.defile()); // priorité 1.5
        assertEquals(500, fNumeric.defile()); // priorité 0.5
        assertTrue(fNumeric.estVide());
    }

    @Test
    void testTypesGeneriquesStringString() {
        FileAPriorite<String, String> fString = new FileAPriorite<>();
        fString.enfile("Alpha", "Z");
        fString.enfile("Beta", "A");
        fString.enfile("Gamma", "M");
        
        assertEquals("Alpha", fString.defile()); // "Z" > "M" > "A"
        assertEquals("Gamma", fString.defile());
        assertEquals("Beta", fString.defile());
        assertTrue(fString.estVide());
    }

    @Test
    void testPrioriteZero() {
        file.enfile("Zero", 0);
        file.enfile("Positif", 1);
        file.enfile("Negatif", -1);
        
        assertEquals("Positif", file.defile());
        assertEquals("Zero", file.defile());
        assertEquals("Negatif", file.defile());
    }

    @Test
    void testDefileMultiplesFoisMemeElement() {
        file.enfile("A", 10);
        file.enfile("A", 10);
        file.enfile("A", 10);
        
        assertEquals("A", file.defile());
        assertEquals("A", file.defile());
        assertEquals("A", file.defile());
        assertTrue(file.estVide());
    }

    @Test
    void testGrandeQuantiteElements() {
        // Test avec 100 éléments
        for (int i = 0; i < 100; i++) {
            file.enfile("Element" + i, i);
        }
        
        // Vérifie qu'on peut tout défiler
        for (int i = 99; i >= 0; i--) {
            String val = file.defile();
            assertTrue(val.startsWith("Element"));
        }
        
        assertTrue(file.estVide());
    }

    @Test
    void testEnfileApresVideComplet() {
        file.enfile("A", 1);
        file.enfile("B", 2);
        
        file.defile();
        file.defile();
        assertTrue(file.estVide());
        
        // Réutilise après avoir vidé
        file.enfile("C", 3);
        file.enfile("D", 4);
        
        assertEquals("D", file.defile());
        assertEquals("C", file.defile());
        assertTrue(file.estVide());
    }
}
