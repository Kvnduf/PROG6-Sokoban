package sokoban;


public class SequenceListe {

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

    SequenceListe(){
        tete = null;
        queue = null;
    }
    
    public boolean estVide() {
        return (tete == null && queue == null);
    }

    public void insereTete(int element){
        if (this.estVide()) {
            tete = new Maillon(element, null);
            queue = tete;
        } else {
            Maillon m = new Maillon(element,tete);
            tete = m;
        }
    }

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
    public int extraitTete(){
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
}
