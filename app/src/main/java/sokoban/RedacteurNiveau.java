package sokoban;
import sokoban.Global.*;
import java.io.BufferedWriter;

public class RedacteurNiveau {
    private char caracterePourCase(Niveau n, int ligne, int colonne) {
        if (n.estVide(ligne, colonne)) return ' ';
        if (n.aMur(ligne, colonne)) return '#';
        if (n.aPousseur(ligne, colonne) && n.aBut(ligne, colonne) ) return '+';
        if (n.aPousseur(ligne, colonne) && !n.aBut(ligne, colonne) ) return '@';
        if (n.aCaisse(ligne, colonne) && n.aBut(ligne, colonne) ) return '*';
        if (n.aCaisse(ligne, colonne) && !n.aBut(ligne, colonne) ) return '$';
        if (n.aBut(ligne, colonne)) return '.';
        return ' ';
    }

    /**
     * Ecrit le niveau donné dans le BufferedWriter au format de l'original, suivi d'une ligne vide.
     * @param writer
     * @param niv
     */
    public void ecrisNiveau(BufferedWriter writer, Niveau niv) {
        try {
            for (int i = 0 ; i < niv.lignes() ; i++) {
                for (int j = 0 ; j < niv.colonnes() ; j ++) {
                    writer.write(caracterePourCase(niv,i,j));
                }
                writer.newLine();
            }
            writer.write("; "+niv.nom()+"\n\n");
            writer.flush();
        } catch (Exception e) {
            Configuration.affiche_erreur("Lors de l'écriture du niveau : " + e.getMessage());
        }
    }
}
