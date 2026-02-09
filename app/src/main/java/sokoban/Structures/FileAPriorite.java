package sokoban.Structures;

import java.util.NoSuchElementException;

public class FileAPriorite<V, P extends Comparable<P>> {
    private Sequence<Couple<V,P>> s;

    /**
     * Constructeur de la classe FileAPriorite. Initialise une nouvelle file à priorité vide.
     */
    public FileAPriorite() {
        s = new SequenceListe<>();
    }

    /**
     * Vérifie si la file à priorité est vide.
     * @return true si la file à priorité est vide, false sinon.
     */
    public boolean estVide(){
        return s.estVide();
    }

    /**
     * Ajoute un élément à la file à priorité avec une priorité associée.
     * @param v l'élément à ajouter à la file à priorité
     * @param p la priorité associée à l'élément v
     */
    public void enfile(V v, P p) {
        s.insereTete(new Couple<V,P>(v, p));
    }

    /**
     * Retire et retourne l'élément de la file à priorité qui a la plus haute priorité.
     * @return l'élément de la file à priorité qui a la plus haute priorité
     * @throws NoSuchElementException si la file à priorité est vide
     */
    public V defile() {
        if (s.estVide()) {
            throw new NoSuchElementException("La file à priorité est vide");
        }
        Iterateur<Couple<V,P>> it = s.iterateur();
        Couple<V,P> m = null;
        Couple<V,P> c = null;
        V val = null;
        
        while (it.aProchain()) {
            c = it.prochain();
            if (m == null || m.compareTo(c) < 0) m = c;
        }
        val = m.get_val();
        it = s.iterateur();
        while (it.prochain().get_prio() != m.get_prio());
        it.supprime();
        return val;
    }

    @Override
    /**
     * Retourne une représentation en chaîne de caractères de la file à priorité.
      * @return une chaîne de caractères représentant la file à priorité
     */
    public String toString() {
        return s.toString();
    }
}
