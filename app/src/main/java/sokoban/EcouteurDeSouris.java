package sokoban;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurDeSouris implements MouseListener {
    private Jeu jeu;
    private NiveauGraphique niveauGraphique;
    private int unit;

    public EcouteurDeSouris(Jeu jeu, NiveauGraphique niveauGraphique, int unit) {
        this.jeu = jeu;
        this.niveauGraphique = niveauGraphique;
        this.unit = unit;
    }

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
