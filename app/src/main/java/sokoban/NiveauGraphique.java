package sokoban;
import javax.imageio.ImageIO;
import javax.swing.*;
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


    public NiveauGraphique(Jeu jeu) {
        this.jeu = jeu;
        try {
			// Chargement de l'image de la même manière que le fichier de niveaux
			InputStream inBut = getClass().getClassLoader().getResourceAsStream("Images/But.png");
			InputStream inCaisseBut = getClass().getClassLoader().getResourceAsStream("Images/Caisse_sur_but.png");
			InputStream inCaisse = getClass().getClassLoader().getResourceAsStream("Images/Caisse.png");
			InputStream inMur = getClass().getClassLoader().getResourceAsStream("Images/Mur.png");
            InputStream inPousseur = getClass().getClassLoader().getResourceAsStream("Images/Pousseur.png");
			InputStream inSol = getClass().getClassLoader().getResourceAsStream("Images/Sol.png");


			imgBut = ImageIO.read(inBut);
			imgCaisseBut = ImageIO.read(inCaisseBut);
			imgCaisse = ImageIO.read(inCaisse);
			imgMur = ImageIO.read(inMur);
            imgPousseur = ImageIO.read(inPousseur);
			imgSol = ImageIO.read(inSol);

			// Unité de dessin
			unit = 50;
		
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : impossible de trouver les fichiers d'images");
			System.exit(2);
		} catch (IOException e) {
			System.err.println("ERREUR : impossible de charger les images");
			System.exit(3);
		}
    }


	void dessineBut(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgBut, x*unit, y*unit, unit, unit, null);
	}
	void dessineCaisse(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgCaisse, x*unit, y*unit, unit, unit, null);
	}

	void dessineCaisseBut(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgCaisseBut, x*unit, y*unit, unit, unit, null);
	}

	void dessineMur(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgMur, x*unit, y*unit, unit, unit, null);
	}
	
	void dessinePousseur(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgPousseur, x*unit, y*unit, unit, unit, null);
	}
	void dessineSol(Graphics2D drawable, int x, int y) {
		drawable.drawImage(imgSol, x*unit, y*unit, unit, unit, null);
	}


	



    @Override
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
