package sokoban;

import sokoban.Global.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcouteurDeClavier implements KeyListener {
    private Jeu jeu;
    private NiveauGraphique niveauGraphique;

    public EcouteurDeClavier(Jeu jeu, NiveauGraphique niveauGraphique) {
        this.jeu = jeu;
        this.niveauGraphique = niveauGraphique;
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
