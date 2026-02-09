package sokoban;
public interface Iterateur {
    /**
     * Indique s'il existe un prochain élément dans la séquence.
     * @return true s'il existe un prochain élément, false sinon
     */
    boolean aProchain();
    /**
     * Retourne le prochain élément de la séquence.
     * @return le prochain élément de la séquence
     */
    int prochain();
    /**
     * Supprime le dernier élément retourné par prochain() de la séquence.
     */
    void supprime();
}
