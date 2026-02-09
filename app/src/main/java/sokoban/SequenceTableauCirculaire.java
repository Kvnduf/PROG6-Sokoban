package sokoban;

public class SequenceTableauCirculaire {
    private static final int NB_MIN_TAB = 4;
    private int capacite;
    private int tab[];  
    private int s; // Index début 
    private int e; // Index fin + 1

    SequenceTableauCirculaire() {
        capacite = SequenceTableauCirculaire.NB_MIN_TAB;
        tab = new int[capacite];
        s = 0;
        e = 0;
    }

    private boolean capaciteFaible(int nouv_taille) {
        return (nouv_taille >  capacite-1);
    }

    private void doubleCapacite() {
        if (e < s) {
            int nouv_tableau[] = new int[2*capacite];
            int i;
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
            int nouv_tableau[] = new int[2*capacite];
            int i;
            for (i = s ; i < e ; i++) {
                nouv_tableau[i] = tab[i];
            }
            tab = nouv_tableau;
            capacite = 2*capacite;
        }
    }

    public int taille() {
        if (e < s) {
            return e + capacite - s; 
        } else {
            return e - s;
        }
    }

    public boolean estVide() {
        return this.taille() <= 0;
    }

    public void insereTete(int element){
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

    public void insereQueue(int element) {
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
    
    public int extraitTete(){
        int v;
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        } else {
            v = tab[s];
            if (s == capacite - 1) {
                s = 0;
            } else {
                s++;
            }
        }
        return v;
    }

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
}
