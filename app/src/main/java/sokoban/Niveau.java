package sokoban;

public class Niveau {

    // Types de cases possibles dans le niveau
    enum Puzzle_element {
        WALL,
        PLAYER,
        PLAYER_ON_GOAL_SQUARE,
        BOX,
        BOX_ON_GOAL_SQUARE,
        GOAL,
        FLOOR
    }

    // Représentation du niveau : une grille de cases
    private Puzzle_element [][] grille;
    private int longueur = 0;
    private int largeur = 0;
    private String nom = "";

    // Constructeurs
    /**
     * Crée un niveau de longueur l et de largeur c, initialisé avec des cases vides.
     * @param l La longueur du niveau
     * @param c La largeur du niveau
     */
    public Niveau(int l, int c) {
        longueur = l;
        largeur = c;
        grille = new Puzzle_element[l][c];
    }


    // Vérification que les coordonnées (i, j) sont valides pour la grille du niveau.
    private void verificationBorneGrille(int i, int j) {
        if (i < 0 || i >= longueur || j < 0 || j >= largeur ) {
            throw new IndexOutOfBoundsException("Coordonnées hors grille : (" + i + "," + j + "), taille = (" + longueur + "," + largeur + ")");
        }
    }

    /**
     * Fixe le nom du niveau.
     * @param s Le nom à fixer pour le niveau
     */
    public void fixeNom(String s){
        nom = s;
    }
    
    /**
     * Supprime le contenu de la case à la ligne i et à la colonne j, en la remplaçant par une case vide.
     * @param i L'indice de la ligne de la case à vider
     * @param j L'indice de la colonne de la case à vider
     */
    public void videCase(int i, int j){
        verificationBorneGrille(i, j);
        grille[i][j] = Niveau.Puzzle_element.FLOOR;
    }
    
    
    /**
     * Ajoute un mur à la ligne i et à la colonne j du niveau.
     * @param i L'indice de la ligne où ajouter le mur
     * @param j L'indice de la colonne où ajouter le mur
     */
    public void ajouteMur(int i, int j) {
        verificationBorneGrille(i, j);
        grille[i][j] = Niveau.Puzzle_element.WALL;
    }
    
    /**
     * Ajoute un pousseur à la ligne i et à la colonne j du niveau.
     * @param i L'indice de la ligne où ajouter le pousseur
     * @param j L'indice de la colonne où ajouter le pousseur
     */
    public void ajoutePousseur(int i, int j) {
        verificationBorneGrille(i, j);
        if (grille[i][j] == Niveau.Puzzle_element.GOAL) {
            grille[i][j] = Niveau.Puzzle_element.PLAYER_ON_GOAL_SQUARE;
        } else {
            grille[i][j] = Niveau.Puzzle_element.PLAYER;
        }
    }
    
    /**
     * Ajoute une caisse à la ligne i et à la colonne j du niveau.
     * @param i L'indice de la ligne où ajouter la caisse
     * @param j L'indice de la colonne où ajouter la caisse
     */
    public void ajouteCaisse(int i, int j) {
        verificationBorneGrille(i, j);
        if (grille[i][j] == Niveau.Puzzle_element.GOAL) {
            grille[i][j] = Niveau.Puzzle_element.BOX_ON_GOAL_SQUARE;
        } else {
            grille[i][j] = Niveau.Puzzle_element.BOX;
        }
    }
    
    /**
     * Ajoute un but à la ligne i et à la colonne j du niveau.
     * @param i L'indice de la ligne où ajouter le but
     * @param j L'indice de la colonne où ajouter le but
     */
    public void ajouteBut(int i, int j) {
        verificationBorneGrille(i, j);
        if (grille[i][j] == Niveau.Puzzle_element.BOX) {
            grille[i][j] = Niveau.Puzzle_element.BOX_ON_GOAL_SQUARE;
        } else if (grille[i][j] == Niveau.Puzzle_element.PLAYER) {
            grille[i][j] = Niveau.Puzzle_element.PLAYER_ON_GOAL_SQUARE;
        } else {
            grille[i][j] = Niveau.Puzzle_element.GOAL;
        }
    }
    
    /**
     * Renvoie le nombre de lignes du niveau.
     * @return Le nombre de lignes
     */
    public int lignes(){
        return longueur;
    }
    
    /**
     * Renvoie le nombre de colonnes du niveau.
     * @return Le nombre de colonnes
     */
    public int colonnes(){
        return largeur;
    }
    
    /**
     * Renvoie le nom du niveau.
     * @return Le nom du niveau
     */
    public String nom(){
        return nom;
    }
    
    /**
     * Renvoie vrai si la case à la ligne i et à la colonne j est vide (c'est-à-dire qu'elle ne contient ni mur, ni pousseur, ni caisse, ni but), et faux sinon.
     * @param l
     * @param c
     * @return
     */
    public boolean estVide(int l, int c) {
        verificationBorneGrille(l, c);
        return grille[l][c] == Niveau.Puzzle_element.FLOOR;
    }
    
    /**
     * Renvoie vrai si la case à la ligne i et à la colonne j contient un mur, et faux sinon.
     * @param l
     * @param c
     * @return
     */
    public boolean aMur(int l, int c) {
        verificationBorneGrille(l, c);
        
        return grille[l][c] == Niveau.Puzzle_element.WALL;
    }
    /**
     * Renvoie vrai si la case à la ligne i et à la colonne j contient un but, et faux sinon.
     * @param l
     * @param c
     * @return
     */
    public boolean aBut(int l, int c) {
        verificationBorneGrille(l, c);
        return  grille[l][c] == Niveau.Puzzle_element.GOAL ||
                grille[l][c] == Niveau.Puzzle_element.PLAYER_ON_GOAL_SQUARE ||
                grille[l][c] == Niveau.Puzzle_element.BOX_ON_GOAL_SQUARE ;
    }
    
    /**
     * Renvoie vrai si la case à la ligne i et à la colonne j contient un pousseur, et faux sinon.
     * @param l
     * @param c
     * @return
     */
    public boolean aPousseur(int l, int c) {
        verificationBorneGrille(l, c);
        return  grille[l][c] == Niveau.Puzzle_element.PLAYER ||
                grille[l][c] == Niveau.Puzzle_element.PLAYER_ON_GOAL_SQUARE ;
    }
    /**
     * Renvoie vrai si la case à la ligne i et à la colonne j contient une caisse, et faux sinon.
     * @param l
     * @param c
     * @return
     */
    public boolean aCaisse(int l, int c) {
        verificationBorneGrille(l, c);
        return  grille[l][c] == Niveau.Puzzle_element.BOX ||
                grille[l][c] == Niveau.Puzzle_element.BOX_ON_GOAL_SQUARE ;
    }

}
