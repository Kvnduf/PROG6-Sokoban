package sokoban;

import sokoban.Global.*;
import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;


public class InterfaceGraphique implements Runnable {
    private Jeu jeu;
    private JFrame frame;
    private boolean maximized = false;

    /**
     * Construit une interface graphique pour le jeu donné en paramètre.
     * @param jeu
     */
    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Affiche la fenêtre de jeu et lance l'interface graphique.
     */
    public void run() {
        // Creation d'une fenetre
        frame = new JFrame("Sokoban");

        // Ajout de notre composant de dessin dans la fenetre
        try {
            frame.add(new NiveauGraphique(jeu));
        } catch (Exception e) {
            Configuration.affiche_erreur(e.getMessage());
            System.exit(1);
        }

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et on demarre
        frame.setSize(500, 300);
        frame.setVisible(true);
        toggleFullscreen();
    }

    /**
     * Affiche ou masque le mode plein écran pour la fenêtre de jeu.
     */
    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

}
