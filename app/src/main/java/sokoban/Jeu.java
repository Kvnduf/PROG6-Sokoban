package sokoban;

import sokoban.Global.*;
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

    /**
     * Construit un jeu en l'initialisant
     * @throws Exception
     */
    public Jeu () throws Exception {
        lecteurNiv = new LecteurNiveaux();
        fichierNiv = Configuration.ouvre(cheminNiveaux);
        if (fichierNiv == null) throw new FileNotFoundException("Fichier " + cheminNiveaux + " introuvable dans le classpath.");
        lecteurBuffer = new BufferedReader(new InputStreamReader(fichierNiv));
    }

    /**
     * Retourne le niveau courant du jeu.
     * @return
     */
    public Niveau niveau() {
        return niveauCourant;
    }

    /**
     * Charge le prochain niveau du jeu à partir du fichier de niveaux et le fixe comme niveau courant.
     * @return bool : true si un nouveau niveau a été chargé avec succès, false s'il n'y a plus de niveaux à charger
     */
    public boolean prochainNiveau() {
        niveauCourant = lecteurNiv.lisProchainNiveau(lecteurBuffer);
        return niveauCourant != null;
    }
}
