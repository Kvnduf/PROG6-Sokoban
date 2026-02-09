package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour la classe Niveau
 */
class NiveauTest {
    private Niveau niveau;

    @BeforeEach
    void setUp() {
        // Crée un niveau de 5x5 avant chaque test
        niveau = new Niveau(5, 5);
    }

    @Test
    void testCreationNiveau() {
        assertEquals(5, niveau.lignes());
        assertEquals(5, niveau.colonnes());
    }

    @Test
    void testFixeNom() {
        niveau.fixeNom("Niveau Test");
        assertEquals("Niveau Test", niveau.nom());
    }

    @Test
    void testVideCase() {
        niveau.videCase(2, 2);
        assertTrue(niveau.estVide(2, 2));
    }

    @Test
    void testAjouteMur() {
        niveau.ajouteMur(1, 1);
        assertTrue(niveau.aMur(1, 1));
        assertFalse(niveau.estVide(1, 1));
    }

    @Test
    void testAjoutePousseur() {
        niveau.ajoutePousseur(2, 2);
        assertTrue(niveau.aPousseur(2, 2));
        assertFalse(niveau.estVide(2, 2));
    }

    @Test
    void testAjoutePousseurSurBut() {
        // D'abord on ajoute un but
        niveau.ajouteBut(3, 3);
        // Puis on ajoute un pousseur dessus
        niveau.ajoutePousseur(3, 3);
        assertTrue(niveau.aPousseur(3, 3));
        assertTrue(niveau.aBut(3, 3));
    }

    @Test
    void testAjouteCaisse() {
        niveau.ajouteCaisse(1, 2);
        assertTrue(niveau.aCaisse(1, 2));
        assertFalse(niveau.estVide(1, 2));
    }

    @Test
    void testAjouteCaisseSurBut() {
        niveau.ajouteBut(4, 4);
        niveau.ajouteCaisse(4, 4);
        assertTrue(niveau.aCaisse(4, 4));
        assertTrue(niveau.aBut(4, 4));
    }

    @Test
    void testAjouteBut() {
        niveau.ajouteBut(0, 0);
        assertTrue(niveau.aBut(0, 0));
    }

    @Test
    void testAjouteButSurCaisse() {
        niveau.ajouteCaisse(2, 3);
        niveau.ajouteBut(2, 3);
        assertTrue(niveau.aCaisse(2, 3));
        assertTrue(niveau.aBut(2, 3));
    }

    @Test
    void testCoordonneesHorsGrille() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            niveau.ajouteMur(-1, 0);
        });
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            niveau.ajouteMur(0, -1);
        });
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            niveau.ajouteMur(5, 0);
        });
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            niveau.ajouteMur(0, 5);
        });
    }

    @Test
    void testNiveauComplet() {
        // Construction d'un petit niveau simple
        niveau.fixeNom("Mini Niveau");
        
        // Ligne de murs
        for (int j = 0; j < 5; j++) {
            niveau.ajouteMur(0, j);
            niveau.ajouteMur(4, j);
        }
        
        // Colonnes de murs
        for (int i = 1; i < 4; i++) {
            niveau.ajouteMur(i, 0);
            niveau.ajouteMur(i, 4);
        }
        
        // Pousseur au centre
        niveau.ajoutePousseur(2, 2);
        
        // Caisse et but
        niveau.ajouteCaisse(2, 1);
        niveau.ajouteBut(2, 3);
        
        // Vérifications
        assertEquals("Mini Niveau", niveau.nom());
        assertTrue(niveau.aMur(0, 0));
        assertTrue(niveau.aPousseur(2, 2));
        assertTrue(niveau.aCaisse(2, 1));
        assertTrue(niveau.aBut(2, 3));
    }
}
