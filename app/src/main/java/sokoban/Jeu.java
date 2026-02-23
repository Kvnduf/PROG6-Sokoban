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

    /**
     * Véifie le niveau est gagné (aucune caisse n'est présente sur une case qui n'est pas un but).
     * @return true si le niveau est gagné, false sinon
     */
    public boolean estGagne() {
        for (int i = 0; i < niveauCourant.lignes(); i++) {
            for (int j = 0; j < niveauCourant.colonnes(); j++) {
                if (niveauCourant.aCaisse(i, j) && !niveauCourant.aBut(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tente de déplacer le pousseur dans la direction donnée. Si le déplacement est valide, le niveau courant est mis à jour en conséquence.
     * @param d La direction dans laquelle tenter de déplacer le pousseur
     * @return true si le déplacement a été effectué avec succès, false sinon
     */
    private boolean deplacerPousseurVers(Direction d) {
        // Trouve la position actuelle du pousseur
        int lignePousseur = -1, colonnePousseur = -1;
        for (int i = 0; i < niveauCourant.lignes(); i++) {
            for (int j = 0; j < niveauCourant.colonnes(); j++) {
                if (niveauCourant.aPousseur(i, j)) {
                    lignePousseur = i;
                    colonnePousseur = j;
                    break;
                }
            }
            if (lignePousseur != -1) break; // Pousseur trouvé, on peut sortir de la boucle
        }

        if (lignePousseur == -1) return false;
        int ligneCible = lignePousseur, colonneCible = colonnePousseur;
        int ligneCibleCaisse = lignePousseur, colonneCibleCaisse = colonnePousseur;
        switch (d) {
            case HAUT: ligneCible--; ligneCibleCaisse -= 2; break;
            case BAS: ligneCible++; ligneCibleCaisse += 2; break;
            case GAUCHE: colonneCible--; colonneCibleCaisse -= 2; break;
            case DROITE: colonneCible++; colonneCibleCaisse += 2; break;
        }
        if (niveauCourant.aMur(ligneCible, colonneCible)) return false;
        if (niveauCourant.aCaisse(ligneCible, colonneCible)) {
            if (niveauCourant.aMur(ligneCibleCaisse, colonneCibleCaisse) || niveauCourant.aCaisse(ligneCibleCaisse, colonneCibleCaisse)) return false;
            // Déplace la caisse vers sa nouvelle position
            niveauCourant.ajouteCaisse(ligneCibleCaisse, colonneCibleCaisse);
            niveauCourant.videCase(ligneCible, colonneCible);
        }
        // Déplace le pousseur
        niveauCourant.ajoutePousseur(ligneCible, colonneCible);
        niveauCourant.videCase(lignePousseur, colonnePousseur);
        return true;
    }

    public boolean declencherMouvementPousseurDirection(Direction d) {
        if (deplacerPousseurVers(d)) {
            if (estGagne()) prochainNiveau();
            return true;
        }
        return false;
    }

    /**
     * Renvoie la direction principale à suivre pour aller du pousseur vers la case cible donnée en paramètre.
     * @param ligneCible la ligne de la case cible
     * @param colonneCible la colonne de la case cible
     * @return La direction principale vers la case cible
     */
    private Direction directionVers(int ligneCible, int colonneCible) {
        int lignePousseur = -1, colonnePousseur = -1;
        for (int i = 0; i < niveauCourant.lignes(); i++) {
            for (int j = 0; j < niveauCourant.colonnes(); j++) {
                if (niveauCourant.aPousseur(i, j)) {
                    lignePousseur = i;
                    colonnePousseur = j;
                    break;
                }
            }
            if (lignePousseur != -1) break; // Pousseur trouvé, on peut sortir de la boucle
        }
        if (lignePousseur == -1) return null; // Pousseur introuvable
        // Calcule la direction principale
        int deltaLigne = ligneCible - lignePousseur;
        int deltaColonne = colonneCible - colonnePousseur;
        
        if (Math.abs(deltaLigne) > Math.abs(deltaColonne)) {
            return (deltaLigne < 0) ? Direction.HAUT : Direction.BAS;
        } else {
            return (deltaColonne < 0) ? Direction.GAUCHE : Direction.DROITE;
        }
    }

    public boolean declencherMouvementPousseurPosition(int ligne, int colonne) {
        return declencherMouvementPousseurDirection(directionVers(ligne, colonne));
    }

}
