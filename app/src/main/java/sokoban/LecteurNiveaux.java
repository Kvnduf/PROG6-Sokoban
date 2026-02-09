package sokoban;
import java.io.*;
import java.util.*;

public class LecteurNiveaux {
    /**
     * Traite un caractère du niveau et met à jour le niveau en conséquence.
     * @param n Le niveau à mettre à jour
     * @param ligne L'indice de la ligne du caractère
     * @param colonne L'indice de la colonne du caractère
     * @param c Le caractère à traiter
     */
    private void traiteCaractere(Niveau n, int ligne, int colonne, char c) {
        switch (c) {
            case '#':
                n.ajouteMur(ligne, colonne);
                break;
            case '@':
                n.ajoutePousseur(ligne, colonne);
                break;
            case '+':
                n.ajouteBut(ligne, colonne);
                n.ajoutePousseur(ligne, colonne);
                break;
            case '$':
                n.ajouteCaisse(ligne, colonne);
                break;
            case '*':
                n.ajouteBut(ligne, colonne);
                n.ajouteCaisse(ligne, colonne);
                break;
            case '.':
                n.ajouteBut(ligne, colonne);
                break;
            case ' ':
                n.videCase(ligne, colonne);
                break;
            default:
                throw new IllegalArgumentException("Caractère inconnu dans le niveau : '" + c + "'");
        }
    }

    /**
     * Lit le prochain niveau à partir du BufferedReader.
     * La Structure :
     *  - Un niveau est constitué de lignes de caractères, suivi d'une ligne vide.
     *  - Les lignes commençant par ';' sont des commentaires et sont ignorées, sauf la dernière qui est utilisée comme nom du niveau.
     * @param reader Le BufferedReader à partir duquel lire le niveau
     * @return Le niveau lu, ou null s'il n'y a plus de niveaux à lire ou en cas d'erreur de format.
     */
    public Niveau lisProchainNiveau(BufferedReader reader) {
        Niveau niv = null;
        int l = 0;
        int c = 0;
        String ligne = "";
        String dernierCommentaire = "";
        ArrayList<String> lignes = new ArrayList<String>();
        
        try {
            while ((ligne = reader.readLine()) != null) {
                if (ligne.isEmpty()) {
                    // Ligne vide = fin du niveau
                    break;
                }
                if (ligne.startsWith(";")) {
                    dernierCommentaire = ligne.substring(1).trim();
                } else {
                    c = Integer.max(ligne.length(), c);
                    lignes.add(ligne);
                    l++;
                }
            }
            
            if (l == 0) {
                return null;
            }
            
            niv = new Niveau(l,c);
            niv.fixeNom(dernierCommentaire);
            for(int i = 0 ; i < l; i++) {
                ligne = lignes.get(i);
                for (int j = 0; j < c; j++) {
                    char ch = (j < ligne.length()) ? ligne.charAt(j) : ' ';
                    traiteCaractere(niv, i, j, ch);
                }
            }
        } catch (Exception a){
            return null;
        }
        return niv;
    }
}
