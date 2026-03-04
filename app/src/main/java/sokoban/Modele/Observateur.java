package sokoban.Modele;

/**
 * Interface pour l'Observateur.
 * Toute classe souhaitant être notifiée des changements du modèle doit implémenter cette interface.
 */
public interface Observateur {
    /**
     * Appelée par le modèle lorsqu'un changement d'état s'est produit.
     */
    void miseAJour();
}
