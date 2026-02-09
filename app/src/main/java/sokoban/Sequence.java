package sokoban;
public interface Sequence {
    /**
     * Indique si la séquence est vide ou pas
     * @return true si la séquence est vide, false sinon
     */
    boolean estVide();
    /**
     * Insère un élément en tête de la séquence
     * @param element l'élément à insérer
     */
    void insereTete(int element);
    /**
     * Insère un élément en queue de la séquence
     * @param element l'élément à insérer
     */
    void insereQueue(int element);
    /**
     * Extrait et retourne l'élément en tête de la séquence
     * @return l'élément en tête de la séquence
     * @throws Exception si la séquence est vide
     */
    int extraitTete() throws Exception;
    /**
     * Retourne une représentation sous forme de chaîne de caractères de la séquence
     * @return la représentation sous forme de chaîne de caractères de la séquence
     */
    String toString();
    /**
     * Retourne un itérateur pour parcourir la séquence
     * @return un itérateur pour la séquence
     */
    Iterateur iterateur();
}

