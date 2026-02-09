package sokoban;
import java.util.NoSuchElementException;

public class SequenceListe implements Sequence {

    private class Maillon {
        int val;
        Maillon suivant;

        Maillon(int nouveau_val, Maillon nouveau_suivant){
            val = nouveau_val;
            suivant = nouveau_suivant;
        }

        // public void set_val(int nouvelle_val) {
        //     val = nouvelle_val;
        // }

        public void set_suivant(Maillon nouveau_suivant) {
            suivant = nouveau_suivant;
        }

        public int get_val() {
            return val;
        }

        public Maillon get_suivant() {
            return suivant;
        }
    }


    private Maillon tete;
    private Maillon queue;

    /**
     * Rend la séquence implémentée par une liste chaînée vide
     */
    public SequenceListe(){
        tete = null;
        queue = null;
    }
    
    @Override
    public boolean estVide() {
        return (tete == null && queue == null);
    }

    @Override
    public void insereTete(int element){
        if (this.estVide()) {
            tete = new Maillon(element, null);
            queue = tete;
        } else {
            Maillon m = new Maillon(element,tete);
            tete = m;
        }
    }

    @Override
    public void insereQueue(int element) {
        if (this.estVide()) {
            tete = new Maillon(element, null);
            queue = tete;
        } else {
            Maillon m = new Maillon(element,null);
            queue.set_suivant(m);
            queue = m;
        }
    }
    @Override
    public int extraitTete() throws Exception {
        int v = 0;
        if (this.estVide()) {
            throw new RuntimeException("Séquence vide");
        } else if (tete.get_suivant() == null) { // Un seul élément
            v = tete.get_val();
            tete = null;
            queue = null;
        } else {
            v = tete.get_val();
            tete = tete.get_suivant();
        }
        return v;
    }
    @Override
    public String toString() {
        String txt = "[";
        Maillon curr;
        if (!this.estVide()) {
            curr = tete;
            txt += curr.get_val();
            curr = curr.get_suivant();

            while (curr != null) {
                txt+= ", "+curr.get_val();
                curr = curr.get_suivant();
            }
        } 
        return txt+"]";
    }
    private class IterateurSequenceListe implements Iterateur {
        Maillon prochain = null;
        Maillon courant = null;
        Maillon precedent = null;
        Boolean dejaSuppr = false;
        SequenceListe s = null;
        IterateurSequenceListe(SequenceListe sequence) {
            s = sequence;
            prochain = sequence.tete;
        }
        @Override
        public boolean aProchain() {
            return prochain != null;
        }
        @Override
        public int prochain() {
            if (!aProchain()) {
                throw new NoSuchElementException();
            }
            dejaSuppr = false;
            precedent = courant;
            courant = prochain;
            prochain = prochain.suivant;
            return courant.val;
        }
        @Override
        public void supprime() {
            if (dejaSuppr) {
                throw new IllegalStateException("Element déjà supprimé");
            }
            if (courant == null) {
                throw new IllegalStateException("Aucun élément courant à supprimer");
            }
            dejaSuppr = true;
            if (courant == s.tete) {
                s.tete = prochain;
                if (s.tete == null) {
                    s.queue = null;
                }
            } else if (courant == s.queue) {
                s.queue = precedent;
                if (s.queue != null) {
                    s.queue.suivant = null;
                }
            } else {
                precedent.suivant = prochain;
            }

        }
    }

    @Override
    public Iterateur iterateur() {
        return new IterateurSequenceListe(this);
    }
}