package sokoban;
import sokoban.Global.*;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            Configuration.affiche_erreur("Usage: ./gradlew run --args=\"<level_number>\"");
            return;
        }
        try {
            Jeu jeu = new Jeu();
            boolean b;
            
            while ((b = jeu.prochainNiveau()) && !jeu.niveau().nom().equals(args[0]));

            if (!b) {
                throw new Exception("Niveau " + args[0] + " introuvable dans le fichier de niveaux.");
            } else {
                SwingUtilities.invokeLater(new InterfaceGraphique(jeu));
            }
            
        } catch (Exception e) {
            Configuration.affiche_erreur(e.getMessage());
            System.exit(1);
        }
    }
}
