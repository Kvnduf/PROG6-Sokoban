package sokoban;
import javax.imageio.ImageIO;
import javax.swing.*;

import sokoban.Global.Configuration;

import java.awt.*;
import java.io.*;

public class NiveauGraphique extends JComponent {
    Jeu jeu;
	Image imgBut;
	Image imgCaisseBut;
	Image imgCaisse;
	Image imgMur;
    Image imgPousseur;
	Image imgSol;

	int unit;

	/**
	 * Construit un composant graphique pour afficher le niveau courant du jeu donné en paramètre.
	 * @param jeu Le jeu dont le niveau courant doit être affiché
	 * @throws FileNotFoundException Si les fichiers d'images ne sont pas trouvés dans le classpath
	 * @throws IOException Si une erreur survient lors du chargement des images
	 * @throws Exception Si une erreur survient lors de l'ouverture ou du chargement des images, avec un message d'erreur approprié
	 * @see Configuration.ouvre(String)
	 */
    public NiveauGraphique(Jeu jeu) throws Exception{
        this.jeu = jeu;
        try {
			// Chargement de l'image de la même manière que le fichier de niveaux
			InputStream inBut = Configuration.ouvre("Images/But.png");
			InputStream inCaisseBut = Configuration.ouvre("Images/Caisse_sur_but.png");
			InputStream inCaisse = Configuration.ouvre("Images/Caisse.png");
			InputStream inMur = Configuration.ouvre("Images/Mur.png");
            InputStream inPousseur = Configuration.ouvre("Images/Pousseur.png");
			InputStream inSol = Configuration.ouvre("Images/Sol.png");


			imgBut = ImageIO.read(inBut);
			imgCaisseBut = ImageIO.read(inCaisseBut);
			imgCaisse = ImageIO.read(inCaisse);
			imgMur = ImageIO.read(inMur);
            imgPousseur = ImageIO.read(inPousseur);
			imgSol = ImageIO.read(inSol);

			// Unité de dessin
			unit = 50;
		
		} catch (FileNotFoundException e) {
			throw new Exception("Impossible de trouver les fichiers d'images");
		} catch (IOException e) {
			throw new Exception("Impossible de charger les images");
		}
    }

	// Méthodes de dessin pour les différents éléments du niveau
	private void dessineBut(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgBut, x*unit, y*unit, unit, unit, null);
	}
	private void dessineCaisse(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgCaisse, x*unit, y*unit, unit, unit, null);
	}

	private void dessineCaisseBut(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgCaisseBut, x*unit, y*unit, unit, unit, null);
	}

	private void dessineMur(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgMur, x*unit, y*unit, unit, unit, null);
	}
	
	private void dessinePousseur(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgPousseur, x*unit, y*unit, unit, unit, null);
	}
	private void dessineSol(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgSol, x*unit, y*unit, unit, unit, null);
	}


    @Override
	/**
	 * Redéfinit la méthode paintComponent pour dessiner le niveau courant du jeu en utilisant les images chargées pour les différents éléments du niveau.
	 * @param g L'objet Graphics utilisé pour dessiner le composant
	 */
	public void paintComponent(Graphics g) {

		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		Graphics2D drawable = (Graphics2D) g;

		// On reccupere quelques infos provenant de la partie JComponent
		int width = getSize().width;
		int height = getSize().height;

		// On calcule le centre de la zone et un rayon
		

		// On efface tout
		drawable.clearRect(0, 0, width, height);

		// On affiche une petite image au milieu
		Niveau n = jeu.niveau();

		for (int x = 0; x < n.colonnes(); x++) {
			for (int y = 0; y < n.lignes(); y++) {
				if (n.aMur(y, x)) {
					dessineSol(drawable, x, y);
					dessineMur(drawable, x, y);
				} else if (n.aPousseur(y, x) && n.aBut(y, x)) {
					dessineBut(drawable, x, y);
					dessinePousseur(drawable, x, y);
				} else if (n.aCaisse(y, x) && n.aBut(y, x)) {
					dessineCaisseBut(drawable, x, y);
				} else if (n.aPousseur(y, x)) {
					dessineSol(drawable, x, y);
					dessinePousseur(drawable, x, y);
				} else if (n.aCaisse(y, x)) {
					dessineSol(drawable, x, y);
					dessineCaisse(drawable, x, y);
				} else if (n.aBut(y, x)) {
					dessineBut(drawable, x, y);
				} else {
					dessineSol(drawable, x, y);
				}
			}
		}
	}
}
