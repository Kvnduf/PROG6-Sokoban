package sokoban;

import sokoban.Global.*;
import javax.swing.JFrame;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;


public class InterfaceGraphique implements Runnable {
    private Jeu jeu;
    private JFrame frame;
    private int unit;
    private boolean maximized;
    private int windowSizeX;
    private int windowSizeY;

    NiveauGraphique niveauGraphique;
    EcouteurDeSouris ecouteurSouris;
    EcouteurDeClavier ecouteurClavier;


    /**
     * Construit une interface graphique pour le jeu donné en paramètre.
     * @param jeu
     * @param unit L'unité de dessin à utiliser pour le niveau graphique
     * @param maximized Indique si la fenêtre doit être affichée en plein écran ou non
     * @param windowSizeX La largeur de la fenêtre en pixels (utilisée si maximized est false)
     * @param windowSizeY La hauteur de la fenêtre en pixels (utilisée si maximized est false)
     */
    public InterfaceGraphique(Jeu jeu, int unit, boolean maximized, int windowSizeX, int windowSizeY) {
        this.jeu = jeu;
        this.unit = unit;
        this.maximized = maximized;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
    }

    /**
     * Affiche la fenêtre de jeu et lance l'interface graphique.
     */
    public void run() {
        // Creation d'une fenetre
        frame = new JFrame("Sokoban");

        // Ajout de notre composant de dessin dans la fenetre
        try {
            niveauGraphique = new NiveauGraphique(jeu, unit);
            ecouteurSouris = new EcouteurDeSouris(jeu, niveauGraphique, unit);
            ecouteurClavier = new EcouteurDeClavier(jeu, niveauGraphique);
            
            frame.add(niveauGraphique);
            niveauGraphique.addKeyListener(ecouteurClavier);
            niveauGraphique.addMouseListener(ecouteurSouris);
        } catch (Exception e) {
            Configuration.affiche_erreur(e.getMessage());
            System.exit(1);
        }

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et on demarre
        frame.setSize(windowSizeX, windowSizeY);
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
