package sokoban.Controleur;

import sokoban.Global.*;
import sokoban.Modele.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurDeSouris implements MouseListener {
    private Jeu jeu;
    private int unit;

    public EcouteurDeSouris(Jeu jeu) {
        this.jeu = jeu;
        this.unit = Configuration.unit;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int ligne = e.getY() / unit;
        int colonne = e.getX() / unit;
        jeu.declencherMouvementPousseurPosition(ligne, colonne);
        // Le modèle notifie la vue via le patron Observateur
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

