package sokoban.Global;

import java.io.InputStream;

public class Configuration {
    static public Boolean affichageAvertissement = true;

    /**
     * Ouvre un flux de lecture vers une ressource du projet, à partir de son chemin relatif.
     * @param path Le chemin relatif de la ressource à ouvrir
     * @return Un flux de lecture vers la ressource demandée
     */
    static public InputStream ouvre(String path) throws Exception {
        return Configuration.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * Affiche un message d'erreur dans la console
     */
    static public void affiche_erreur(String message){
        System.err.println("[ERREUR] : "+message);
    }

    /**
     * Affiche un message d'avertissement dans la console
     */
    static public void affiche_avertissement(String message){
        if (affichageAvertissement) {
            System.err.println("[AVERTISSEMENT] : "+message);
        }
    }
}
