package sokoban;

import sokoban.Global.*;
import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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

    // Classes interne pour l'écouteur de souris
    private class EcouteurDeSouris implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            int ligne = e.getY() / unit;
            int colonne = e.getX() / unit;
            
            if (jeu.declencherMouvementPousseurPosition(ligne, colonne))
                niveauGraphique.repaint();
            
        }
        
        @Override
        public void mouseClicked(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }

    // Classe interne pour l'écouteur de clavier
    private class EcouteurDeClavier implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            Direction dir = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    dir = Direction.HAUT;
                    break;
                case KeyEvent.VK_DOWN:
                    dir = Direction.BAS;
                    break;
                case KeyEvent.VK_LEFT:
                    dir = Direction.GAUCHE;
                    break;
                case KeyEvent.VK_RIGHT:
                    dir = Direction.DROITE;
                    break;
            }
            if (dir != null) {
                if (jeu.declencherMouvementPousseurDirection(dir)) {
                    niveauGraphique.repaint();
                } 
                
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyTyped(KeyEvent e) { }
    }


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
            ecouteurSouris = new EcouteurDeSouris();
            ecouteurClavier = new EcouteurDeClavier();
            
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
