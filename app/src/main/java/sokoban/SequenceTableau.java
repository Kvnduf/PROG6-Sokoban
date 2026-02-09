package sokoban;

public class SequenceTableau {
    private static final int NB_MAX_TAB = 4;
    private int[] tab;
    private int nb;
    SequenceTableau() {
        tab = new int[NB_MAX_TAB];
        nb = 0;
    }

    public boolean estVide() {
        return nb <= 0;
    }

    public void insereTete(int element){
        if (nb >= SequenceTableau.NB_MAX_TAB) {
            throw new RuntimeException("Séquence pleine");
        }
        int i;
        for (i = nb-1 ; i >= 0 ; i--) {
            tab[i+1] = tab[i] ;
        }
        tab[0] = element;
        nb ++;
        return;
    }

    public void insereQueue(int element) {
        if (nb >= SequenceTableau.NB_MAX_TAB) {
            throw new RuntimeException("Séquence pleine");
        }
        tab[nb] = element;
        nb ++;
        return;
    }
    
    public int extraitTete(){
        int v;
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        } else {
            int i;
            v = tab[0];
            for (i = 0 ; i < nb - 1 ; i++) {
                tab[i] = tab[i+1] ;
            }
            nb--;
        }
        return v;
    }

    public String toString() {
        String txt = "[";
        int i;
        for (i = 0 ; i < this.nb; i++) {
            if (i != 0) {
                txt += ", ";
            }
            txt += this.tab[i];
        }
        return txt+"]";
    }
}
