package sokoban.Structures;

public class Couple<V, P extends Comparable<P>> implements Comparable<Couple<V, P>>{
    private V val = null;
    private P prio = null;

    /**
     * Constructeur de la classe Couple. Initialise un nouveau couple avec une valeur et une priorité.
     * @param valeur
     * @param priorite
     */
    public Couple(V valeur, P priorite) {
        val = valeur;
        prio = priorite;
    }

    /**
     * Retourne la valeur du couple.
     * @return la valeur du couple
     */
    public V get_val() {
        return val;
    }

    /**
     * Retourne la priorité du couple.
     * @return la priorité du couple
     */
    public P get_prio() {
        return prio;
    }

    @Override
    /**
     * Retourne une représentation en chaîne de caractères du couple.
     * @return une chaîne de caractères représentant le couple
     */
    public String toString() {
        return "("+val+","+prio+")";
    }

    @Override
    /**
     * Compare ce couple à un autre couple en fonction de leur priorité.
     * @param arg0 le couple à comparer
     * @return un entier négatif, zéro ou un entier positif selon que la priorité de ce couple est inférieure, égale ou supérieure à celle du couple argument
     */
    public int compareTo(Couple<V, P> arg0) {
        return this.prio.compareTo(arg0.prio);
    }
}
