/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dame;

/**
 *
 * @author Castor
 */
public class Case {

    public static final boolean FONCE = true, CLAIRE = false;
    private final int ligne, colonne;
    private final boolean couleur;
    private Pion pion = null;

    public Case(int ligne, int colonne, boolean couleur) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.couleur = couleur;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
    
    public void retirerPionDuJeu(){
        if(estVide()) return;
        pion.retirerDuJeu();
    }
    
    public boolean getCouleur() {
        return couleur;
    }

    public void placerPion(Pion pion) {
        this.pion = pion;
    }

    public Pion retirerPion() {
        Pion tmp = this.pion;
        this.pion = null;
        return tmp;
    }

    public int getCouleurPion() {
        if (pion == null) {
            return -1;
        }
        return pion.getCouleur();
    }

    public int getNumeroPion() {
        if (pion == null) {
            return -1;
        }
        return pion.getNumero();
    }

    public boolean pionIsDame() {
        return pion.isDame();
    }

    public boolean estVide() {
        return pion == null;
    }

    public String toString() {
        return "[" + ligne + "," + colonne + "," + (couleur ? "FONCE" : "CLAIRE") + ", " + (pion == null ? ' ' : pion.getCouleur()) + "]";
    }
}
