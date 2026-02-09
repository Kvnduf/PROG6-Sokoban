package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Tests pour la classe LecteurNiveaux
 */
class LecteurNiveauxTest {
    private LecteurNiveaux lecteur;

    @BeforeEach
    void setUp() {
        lecteur = new LecteurNiveaux();
    }

    @Test
    void testLectureNiveauSimple() {
        String contenu = 
            "#####\n" +
            "#@  #\n" +
            "#$. #\n" +
            "#####\n" +
            "; Niveau Simple\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertEquals("Niveau Simple", niveau.nom());
        assertEquals(4, niveau.lignes());
        assertEquals(5, niveau.colonnes());
        
        // Vérification des éléments
        assertTrue(niveau.aMur(0, 0));
        assertTrue(niveau.aPousseur(1, 1));
        assertTrue(niveau.aCaisse(2, 1));
        assertTrue(niveau.aBut(2, 2));
    }

    @Test
    void testLectureNiveauAvecCommentaires() {
        String contenu = 
            "; Ceci est un commentaire\n" +
            "; Un autre commentaire\n" +
            "####\n" +
            "#@ #\n" +
            "####\n" +
            "; Nom du niveau\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertEquals("Nom du niveau", niveau.nom());
    }

    @Test
    void testLectureNiveauVide() {
        String contenu = "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNull(niveau);
    }

    @Test
    void testLectureNiveauAvecCaisseSurBut() {
        String contenu = 
            "####\n" +
            "#@*#\n" +
            "####\n" +
            "; Test caisse sur but\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertTrue(niveau.aCaisse(1, 2));
        assertTrue(niveau.aBut(1, 2));
    }

    @Test
    void testLectureNiveauAvecPousseurSurBut() {
        String contenu = 
            "####\n" +
            "#+.#\n" +
            "####\n" +
            "; Test pousseur sur but\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertTrue(niveau.aPousseur(1, 1));
        assertTrue(niveau.aBut(1, 1));
        assertTrue(niveau.aBut(1, 2));
    }

    @Test
    void testLectureLignesInegales() {
        String contenu = 
            "####\n" +
            "#@\n" +
            "#######\n" +
            "####\n" +
            "; Lignes inégales\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertEquals(4, niveau.lignes());
        assertEquals(7, niveau.colonnes()); // La plus longue ligne
    }

    @Test
    void testLectureDeuxNiveauxSuccessifs() {
        String contenu = 
            "####\n" +
            "#@ #\n" +
            "####\n" +
            "; Niveau 1\n" +
            "\n" +
            "#####\n" +
            "#@$.#\n" +
            "#####\n" +
            "; Niveau 2\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        
        Niveau niveau1 = lecteur.lisProchainNiveau(reader);
        assertNotNull(niveau1);
        assertEquals("Niveau 1", niveau1.nom());
        
        Niveau niveau2 = lecteur.lisProchainNiveau(reader);
        assertNotNull(niveau2);
        assertEquals("Niveau 2", niveau2.nom());
        
        // Il ne devrait plus y avoir de niveau
        Niveau niveau3 = lecteur.lisProchainNiveau(reader);
        assertNull(niveau3);
    }

    @Test
    void testLectureNiveauComplet() {
        String contenu = 
            "  #####\n" +
            "###   #\n" +
            "#@$   #\n" +
            "### $.#\n" +
            "#   ###\n" +
            "##### \n" +
            "; Niveau Complet\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertEquals("Niveau Complet", niveau.nom());
        assertEquals(6, niveau.lignes());
        assertEquals(7, niveau.colonnes());
        
        // Vérifications spécifiques
        assertTrue(niveau.aMur(0, 2));
        assertTrue(niveau.aPousseur(2, 1));
        assertTrue(niveau.aCaisse(2, 2));
        // Ligne 3: "### $.#" -> $ est en position 4, . est en position 5
        assertTrue(niveau.aCaisse(3, 4));
        assertFalse(niveau.aBut(3, 4)); // $ seul n'est pas sur un but
        assertTrue(niveau.aBut(3, 5)); // . est un but
    }

    @Test
    void testLectureTousLesCaracteres() {
        String contenu = 
            "# @+$*.  \n" +
            "; Test tous caractères\n" +
            "\n";
        
        BufferedReader reader = new BufferedReader(new StringReader(contenu));
        Niveau niveau = lecteur.lisProchainNiveau(reader);
        
        assertNotNull(niveau);
        assertTrue(niveau.aMur(0, 0));
        assertTrue(niveau.estVide(0, 1));
        assertTrue(niveau.aPousseur(0, 2));
        assertTrue(niveau.aPousseur(0, 3) && niveau.aBut(0, 3));
        assertTrue(niveau.aCaisse(0, 4));
        assertTrue(niveau.aCaisse(0, 5) && niveau.aBut(0, 5));
        assertTrue(niveau.aBut(0, 6));
        assertTrue(niveau.estVide(0, 7));
        assertTrue(niveau.estVide(0, 8));
    }
}
