package sokoban.Vue;

import sokoban.Global.*;
import sokoban.Modele.*;

import javax.swing.JFrame;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;


public class InterfaceGraphique implements Runnable {
    private Jeu jeu;
    private JFrame frame;
    private int unit;
    private boolean maximized;
    private int windowSizeX;
    private int windowSizeY;

    NiveauGraphique niveauGraphique;


    /**
     * Construit une interface graphique pour le jeu donné en paramètre.
     * Crée également le composant NiveauGraphique associé.
     * @param jeu Le jeu à afficher
     * @throws Exception Si le chargement des images échoue
     */
    public InterfaceGraphique(Jeu jeu) throws Exception {
        this.jeu = jeu;
        this.unit = Configuration.unit;
        this.maximized = Configuration.maximized;
        this.windowSizeX = Configuration.windowSizeX;
        this.windowSizeY = Configuration.windowSizeY;
        this.niveauGraphique = new NiveauGraphique(this.jeu, unit);
    }

    /**
     * Enregistre un écouteur clavier sur le composant graphique du niveau.
     * Appelé par le programme principal pour connecter le Contrôleur à la Vue
     * sans que la Vue ne dépende du Contrôleur.
     * @param kl L'écouteur clavier à enregistrer
     */
    public void ajouterEcouteurClavier(KeyListener kl) {
        niveauGraphique.addKeyListener(kl);
    }

    /**
     * Enregistre un écouteur souris sur le composant graphique du niveau.
     * Appelé par le programme principal pour connecter le Contrôleur à la Vue
     * sans que la Vue ne dépende du Contrôleur.
     * @param ml L'écouteur souris à enregistrer
     */
    public void ajouterEcouteurSouris(MouseListener ml) {
        niveauGraphique.addMouseListener(ml);
    }

    /**
     * Affiche la fenêtre de jeu et lance l'interface graphique.
     */
    public void run() {
        // Creation d'une fenetre
        frame = new JFrame("Sokoban");

        // Ajout de notre composant de dessin dans la fenetre
        try {
            frame.add(niveauGraphique);
            // Un clic sur le bouton de fermeture clos l'application
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
            // On fixe la taille et on demarre
            frame.setSize(windowSizeX, windowSizeY);
            frame.setVisible(true);
            toggleFullscreen();
        } catch (Exception e) {
            Configuration.affiche_erreur(e.getMessage());
            System.exit(1);
        }

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
