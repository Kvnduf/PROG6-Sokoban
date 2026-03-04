package sokoban;
import sokoban.Global.*;
import sokoban.Modele.*;
import sokoban.Vue.*;
import sokoban.Controleur.*;

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
                InterfaceGraphique vue = new InterfaceGraphique(jeu);

                vue.ajouterEcouteurClavier(new EcouteurDeClavier(jeu));
                vue.ajouterEcouteurSouris(new EcouteurDeSouris(jeu));

                vue.run();

            }
            
        } catch (Exception e) {
            Configuration.affiche_erreur(e.getMessage());
            System.exit(1);
        }
    }
}
