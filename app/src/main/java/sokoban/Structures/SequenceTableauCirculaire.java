package sokoban.Structures;


public class SequenceTableauCirculaire<E> implements Sequence<E> {
    private static final int NB_MIN_TAB = 4;
    private int capacite;
    private E tab[];  
    private int s; // Index début 
    private int e; // Index fin + 1

    @SuppressWarnings("unchecked")
    /**
     * Renvoie une séquence implémentée par un tableau circulaire vide
     */
    public SequenceTableauCirculaire() {
        capacite = SequenceTableauCirculaire.NB_MIN_TAB;
        tab = (E[]) new Object[capacite];
        s = 0;
        e = 0;
    }

    private boolean capaciteFaible(int nouv_taille) {
        return (nouv_taille >  capacite-1);
    }

    @SuppressWarnings("unchecked")
    private void doubleCapacite() {
        E nouv_tableau[] = (E[]) new Object[2*capacite];
        int i;

        if (e < s) {
            for (i = s ; i < capacite ; i++) {
                nouv_tableau[i + capacite] = tab[i];
            }
            for (i = 0 ; i < e ; i++) {
                nouv_tableau[i] = tab[i];
            }
            s = s + capacite;
            tab = nouv_tableau;
            capacite = 2*capacite;
        } else {
            for (i = s ; i < e ; i++) {
                nouv_tableau[i] = tab[i];
            }
            tab = nouv_tableau;
            capacite = 2*capacite;
        }
    }

    private int taille() {
        if (e < s) {
            return e + capacite - s; 
        } else {
            return e - s;
        }
    }

    @Override
    public boolean estVide() {
        return this.taille() <= 0;
    }

    @Override
    public void insereTete(E element){
        if (this.capaciteFaible(this.taille()+1)) {
            doubleCapacite();
        }
        if (s == 0) {
            s = capacite - 1;
        } else {
            s --;
        }
        tab[s] = element;
        return;
    }

    @Override
    public void insereQueue(E element) {
        if (this.capaciteFaible(this.taille()+1)) {
            doubleCapacite();
        }
        tab[e] = element;
        if (e == capacite - 1) {
            e = 0;
        } else {
            e ++;
        }
        return;
    }

    @Override
    public E extraitTete() throws Exception {
        E v;
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        } else {
            v = (E) tab[s];
            if (s == capacite - 1) {
                s = 0;
            } else {
                s++;
            }
        }
        return v;
    }
    @Override
    public String toString() {
        String txt = "[";
        int i;
        // System.out.println("s="+s+" e="+e+" taille="+this.taille());
        for (i = 0 ; i < this.taille(); i++) {
            if (i != 0) {
                txt += ", ";
            }
            txt += this.tab[(i+s)%capacite];
        }
        return txt+"]";
    }

    private class IterateurSequenceTableauCirculaire implements Iterateur<E> {
        int prochain;
        boolean dejaSuppr = false;
        boolean prochainAppele = false;
        
        IterateurSequenceTableauCirculaire(SequenceTableauCirculaire<E> sequence) {
            prochain = s;
        }
        @Override
        public boolean aProchain() {
            return prochain != e;
        }
        @Override
        public E prochain() {
            if (!aProchain()) {
                throw new java.util.NoSuchElementException();
            }
            dejaSuppr = false;
            prochainAppele = true;
            int temp = prochain;
            prochain = (prochain+1)%capacite;
            return (E) tab[temp];
        }
        @Override
        public void supprime() {
            if (dejaSuppr) {
                throw new IllegalStateException("Element déjà supprimé");
            }
            if (!prochainAppele) {
                throw new IllegalStateException("Aucun élément courant à supprimer");
            }
            dejaSuppr = true;
            
            int indexASupprimer = (prochain - 1 + capacite) % capacite;
            
            int i = indexASupprimer;
            while (i != (e - 1 + capacite) % capacite) {
                int next = (i + 1) % capacite;
                tab[i] = tab[next];
                i = next;
            }
            
            e = (e - 1 + capacite) % capacite;
            
            prochain = (prochain - 1 + capacite) % capacite;
        }
    }

    @Override
    public Iterateur<E> iterateur() {
        return new IterateurSequenceTableauCirculaire(this);
    }
}
