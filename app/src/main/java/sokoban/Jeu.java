package sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Jeu {

    private static String cheminNiveaux = "Original.txt";
    private LecteurNiveaux lecteurNiv;
    private InputStream fichierNiv;
    private BufferedReader lecteurBuffer;
    private Niveau niveauCourant = null;

    public Jeu () throws FileNotFoundException {
        lecteurNiv = new LecteurNiveaux();
        fichierNiv = getClass().getClassLoader().getResourceAsStream(cheminNiveaux);
        if (fichierNiv == null) throw new FileNotFoundException("Fichier " + cheminNiveaux + " introuvable dans le classpath.");
        lecteurBuffer = new BufferedReader(new InputStreamReader(fichierNiv));
    }

    Niveau niveau() {
        return niveauCourant;
    }

    boolean prochainNiveau() {
        niveauCourant = lecteurNiv.lisProchainNiveau(lecteurBuffer);
        return niveauCourant != null;
    }
}
