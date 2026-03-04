package sokoban.Controleur;

import sokoban.Modele.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcouteurDeClavier implements KeyListener {
    private Jeu jeu;

    public EcouteurDeClavier(Jeu jeu) {
        this.jeu = jeu;
    }

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
            jeu.declencherMouvementPousseurDirection(dir);
            // Le modèle notifie la vue via le patron Observateur
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
