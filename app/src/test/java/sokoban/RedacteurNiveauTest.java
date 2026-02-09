package sokoban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.StringWriter;

/**
 * Tests pour la classe RedacteurNiveau
 */
class RedacteurNiveauTest {
    private RedacteurNiveau redacteur;

    @BeforeEach
    void setUp() {
        redacteur = new RedacteurNiveau();
    }

    @Test
    void testEcritureNiveauSimple() {
        Niveau niveau = new Niveau(3, 4);
        niveau.fixeNom("Test Simple");
        niveau.ajouteMur(0, 0);
        niveau.ajouteMur(0, 1);
        niveau.ajouteMur(0, 2);
        niveau.ajouteMur(0, 3);
        niveau.ajoutePousseur(1, 1);
        niveau.ajouteMur(2, 0);
        niveau.ajouteMur(2, 1);
        niveau.ajouteMur(2, 2);
        niveau.ajouteMur(2, 3);
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        assertTrue(resultat.contains("####"));
        assertTrue(resultat.contains("@"));
        assertTrue(resultat.contains("; Test Simple"));
    }

    @Test
    void testEcritureNiveauAvecTousLesElements() {
        Niveau niveau = new Niveau(1, 9);
        niveau.fixeNom("Tous éléments");
        
        // # @ + $ * .   (espace) (espace)
        niveau.ajouteMur(0, 0);
        niveau.ajoutePousseur(0, 2);
        niveau.ajouteBut(0, 3);
        niveau.ajoutePousseur(0, 3); // Pousseur sur but = '+'
        niveau.ajouteCaisse(0, 4);
        niveau.ajouteBut(0, 5);
        niveau.ajouteCaisse(0, 5); // Caisse sur but = '*'
        niveau.ajouteBut(0, 6);
        niveau.videCase(0, 7);
        niveau.videCase(0, 8);
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        String[] lignes = resultat.split("\n");
        
        // La première ligne doit contenir tous les caractères
        assertTrue(lignes[0].contains("#"));
        assertTrue(lignes[0].contains("@"));
        assertTrue(lignes[0].contains("+"));
        assertTrue(lignes[0].contains("$"));
        assertTrue(lignes[0].contains("*"));
        assertTrue(lignes[0].contains("."));
    }

    @Test
    void testEcritureNiveauNom() {
        Niveau niveau = new Niveau(2, 2);
        niveau.fixeNom("Mon Niveau");
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        assertTrue(resultat.contains("; Mon Niveau"));
    }

    @Test
    void testEcritureLectureRoundTrip() {
        // Crée un niveau
        Niveau niveauOriginal = new Niveau(4, 5);
        niveauOriginal.fixeNom("Round Trip Test");
        
        // Ligne de murs
        for (int j = 0; j < 5; j++) {
            niveauOriginal.ajouteMur(0, j);
            niveauOriginal.ajouteMur(3, j);
        }
        
        // Pousseur, caisse, but
        niveauOriginal.ajoutePousseur(1, 1);
        niveauOriginal.ajouteCaisse(1, 2);
        niveauOriginal.ajouteBut(1, 3);
        
        // Écrit le niveau
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        redacteur.ecrisNiveau(writer, niveauOriginal);
        
        // Relit le niveau
        String contenu = stringWriter.toString();
        LecteurNiveaux lecteur = new LecteurNiveaux();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.StringReader(contenu));
        Niveau niveauRelu = lecteur.lisProchainNiveau(reader);
        
        // Vérifications
        assertNotNull(niveauRelu);
        assertEquals(niveauOriginal.nom(), niveauRelu.nom());
        assertEquals(niveauOriginal.lignes(), niveauRelu.lignes());
        assertEquals(niveauOriginal.colonnes(), niveauRelu.colonnes());
        
        // Vérifie que tous les éléments sont identiques
        assertTrue(niveauRelu.aPousseur(1, 1));
        assertTrue(niveauRelu.aCaisse(1, 2));
        assertTrue(niveauRelu.aBut(1, 3));
        assertTrue(niveauRelu.aMur(0, 0));
    }

    @Test
    void testEcritureNiveauAvecCaisseSurBut() {
        Niveau niveau = new Niveau(1, 3);
        niveau.fixeNom("Caisse sur but");
        niveau.ajouteBut(0, 1);
        niveau.ajouteCaisse(0, 1);
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        assertTrue(resultat.contains("*")); // Caractère pour caisse sur but
    }

    @Test
    void testEcritureNiveauAvecPousseurSurBut() {
        Niveau niveau = new Niveau(1, 3);
        niveau.fixeNom("Pousseur sur but");
        niveau.ajouteBut(0, 1);
        niveau.ajoutePousseur(0, 1);
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        assertTrue(resultat.contains("+")); // Caractère pour pousseur sur but
    }

    @Test
    void testEcritureNiveauComplet() {
        Niveau niveau = new Niveau(5, 7);
        niveau.fixeNom("Niveau Complet");
        
        // Bordures de murs
        for (int j = 0; j < 7; j++) {
            niveau.ajouteMur(0, j);
            niveau.ajouteMur(4, j);
        }
        for (int i = 1; i < 4; i++) {
            niveau.ajouteMur(i, 0);
            niveau.ajouteMur(i, 6);
        }
        
        // Éléments de jeu
        niveau.ajoutePousseur(2, 2);
        niveau.ajouteCaisse(2, 3);
        niveau.ajouteBut(2, 4);
        
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        
        redacteur.ecrisNiveau(writer, niveau);
        
        String resultat = stringWriter.toString();
        String[] lignes = resultat.split("\n");
        
        // Vérifie qu'il y a bien 5 lignes de grille + ligne de commentaire + ligne vide
        assertTrue(lignes.length >= 5);
        
        // Vérifie la présence du nom
        assertTrue(resultat.contains("; Niveau Complet"));
    }
}
