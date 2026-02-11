package sokoban;


import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;


public class InterfaceGraphique implements Runnable {
    private Jeu jeu;
    private JFrame frame;
    private boolean maximized = false;

    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
    }


    public void run() {
        // Creation d'une fenetre
        frame = new JFrame("Sokoban");

        // Ajout de notre composant de dessin dans la fenetre
        frame.add(new NiveauGraphique(jeu));

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et on demarre
        frame.setSize(500, 300);
        frame.setVisible(true);
        toggleFullscreen();
    }

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
