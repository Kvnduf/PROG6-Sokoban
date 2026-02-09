package sokoban;
import java.io.*;

public class App {
    public static void main(String[] args) {
        String chemin = "Original.txt";
        try (InputStream fichierNiveaux = App.class.getClassLoader().getResourceAsStream(chemin);
             OutputStream fichierSortie = System.out;) {
            if (fichierNiveaux == null) {
                System.out.println("Erreur : Fichier " + chemin + " introuvable dans le classpath.");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(fichierNiveaux));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fichierSortie));
            LecteurNiveaux lecteur = new LecteurNiveaux();
            RedacteurNiveau redacteur = new RedacteurNiveau();
            Niveau niveauCourant = null;
            while ((niveauCourant = lecteur.lisProchainNiveau(reader)) != null) {
                redacteur.ecrisNiveau(writer, niveauCourant);
            }
            writer.flush();
        } catch (Exception e) {
            System.out.println("Erreur :  " + e.getMessage());
        }
    }
}
