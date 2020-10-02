/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudedame;

import appetat.Utile;
import dame.*;
import java.util.Vector;
import composantgraphique.EcouteurSouris;

/**
 *
 * @author Castor
 */
public abstract class Equipe {

    public static final int DELIMITEUR = 0;

    protected Joueur joueur;
    protected Damier damier;
    protected final int NOMBRE_CASE_LIGNE, NOMBRE_CASE;
    protected final int POSITION_DAMIER_X, POSITION_DAMIER_Y;
    protected final int LARGEUR_IMG_PLATEAU, HAUTEUR_IMG_PLATEAU;
    protected final int TAILLE_CASE;

    protected int indiceCaseSelectionnee;
    protected Vector<Integer> listeChoix = new Vector<Integer>();
    protected int nombreDePrise;
    protected boolean bloqueSelection;
    protected boolean doitCapturer; // pour choisir un pion qui peut capturer

    public Equipe(Joueur joueur, Damier damier,
            int POSITION_DAMIER_X, int POSITION_DAMIER_Y, int LARGEUR_IMG_PLATEAU, int HAUTEUR_IMG_PLATEAU, int TAILLE_CASE) {
        this.joueur = joueur;
        this.damier = damier;
        this.POSITION_DAMIER_X = POSITION_DAMIER_X;
        this.POSITION_DAMIER_Y = POSITION_DAMIER_Y;
        this.LARGEUR_IMG_PLATEAU = LARGEUR_IMG_PLATEAU;
        this.HAUTEUR_IMG_PLATEAU = HAUTEUR_IMG_PLATEAU;
        this.TAILLE_CASE = TAILLE_CASE;
        this.NOMBRE_CASE_LIGNE = damier.getNombreDeCaseParLigne();
        this.NOMBRE_CASE = damier.getNombreDeCase();
        reinitialiser();
    }

    public abstract boolean jouer();

    protected boolean valider(int ligne, int colonne) {
        doitCapturer = captureObligatoire();
        if (bloqueSelection && !listeChoix.contains(ligne * NOMBRE_CASE_LIGNE + colonne)) {
            if (indiceCaseSelectionnee >= 0) {
                return false;
            }
        }
        if (!this.actionDeplacerPion(ligne, colonne)) {
            if (bloqueSelection == false) {
                this.actionSelectionneeCase(ligne, colonne);
            }
            return false;
        }
        reinitialiser();
        return true;
    }

    protected boolean actionSelectionneeCase(int ligne, int colonne) {
        this.indiceCaseSelectionnee = ligne * NOMBRE_CASE_LIGNE + colonne;
        Case caseSelectionnee = damier.getCase(indiceCaseSelectionnee);
        if (caseSelectionnee.estVide() || caseSelectionnee.getCouleurPion() != joueur.getCouleur()) {
            reinitialiser();
            return false;
        } else {
            getChoixPossible(ligne, colonne);
            if(doitCapturer && peutCapturer() < 0){
                reinitialiser();
                return false;
            }
            return true;
        }
    }

    protected boolean aucunChoixPossible() {
        for (int i = 0; i < listeChoix.size(); i++) {
            if (listeChoix.get(i) != DELIMITEUR) {
                return false;
            }
        }
        return true;
    }

    protected boolean actionDeplacerPion(int ligne, int colonne) {
        if (this.indiceCaseSelectionnee > 0) {
            int indiceNouvelleCase = ligne * this.NOMBRE_CASE_LIGNE + colonne;
            if (listeChoix.contains(indiceNouvelleCase)) {
                boolean enDame = joueur.deplacer(damier.getCase(indiceCaseSelectionnee).getNumeroPion(), damier.getCase(indiceNouvelleCase));
                for (int i = listeChoix.indexOf(indiceNouvelleCase); i >= 0; i--) {
                    int indiceACapturer = listeChoix.get(i);
                    if (indiceACapturer == DELIMITEUR) {
                        break;
                    }
                    if (indiceACapturer < 0) {
                        nombreDePrise++;
                        damier.getCase(-indiceACapturer).retirerPionDuJeu();
                        reinitialiser();
                        indiceCaseSelectionnee = indiceNouvelleCase;
                        getChoixPossible(ligne, colonne);
                        if (peutCapturer() >= 0 && enDame == false) {
                            bloqueSelection = true;
                            return false;
                        }
                        bloqueSelection = false;
                        reinitialiser();
                        break;
                    }
                }
                return true;
            }
        }
        reinitialiser();
        return false;
    }

    protected void getChoixPossible(int ligne, int colonne) {
        int s = joueur.getSensDeDeplacement();
        boolean isDame = damier.getCase(ligne, colonne).pionIsDame();
        Vector<Integer> listeChoix = new Vector<Integer>();
        getChoixPossibleDiagonale(listeChoix, ligne, colonne, s, Joueur.GAUCHE, isDame);
        getChoixPossibleDiagonale(listeChoix, ligne, colonne, s, Joueur.DROITE, isDame);
        getChoixPossibleDiagonale(listeChoix, ligne, colonne, -s, Joueur.GAUCHE, isDame);
        getChoixPossibleDiagonale(listeChoix, ligne, colonne, -s, Joueur.DROITE, isDame);

        if (delimiteurSeulement()) {
            this.listeChoix = listeChoix;
        }
    }

    protected void getChoixPossibleDiagonale(Vector<Integer> listeChoix, int ligneDepart, int colonneDepart, int pasLigne, int pasColonne, boolean isDame) {
        boolean capture = false;
        int indiceACapturer = -1;
        boolean captureSeulement = !isDame && pasLigne != joueur.getSensDeDeplacement();
        for (int ligne = ligneDepart + pasLigne, colonne = colonneDepart + pasColonne; Utile.nombreDansIntervalle(ligne, 0, NOMBRE_CASE_LIGNE - 1)
                && Utile.nombreDansIntervalle(colonne, 0, NOMBRE_CASE_LIGNE - 1); ligne += pasLigne, colonne += pasColonne) {
            Case c = damier.getCase(ligne, colonne);
            if (capture && c.estVide()) {
                listeChoix.add(-indiceACapturer);
                this.listeChoix.add(-indiceACapturer);
            }
            if (!c.estVide()) {
                if (c.getCouleurPion() != joueur.getCouleur() && !capture) {
                    capture = true;
                    indiceACapturer = ligne * NOMBRE_CASE_LIGNE + colonne;
                    continue;
                } else {
                    break;
                }
            }
            if (!captureSeulement || capture) {
                listeChoix.add(ligne * NOMBRE_CASE_LIGNE + colonne);
            }
            if (capture) {
                this.listeChoix.add(ligne * NOMBRE_CASE_LIGNE + colonne);
            }
            if (!isDame) {
                break;
            }
        }
        listeChoix.add(DELIMITEUR);
        this.listeChoix.add(DELIMITEUR);
    }

    protected boolean captureObligatoire() {
        Vector<Integer> sauvegardeListe = new Vector<Integer>();
        sauvegardeListe.addAll(listeChoix);
        int sauvegardeIndice = indiceCaseSelectionnee;
        Case[] grille = damier.getGrille();

        for (int i = 0; i < grille.length; i++) {
            if (grille[i].estVide() == false && grille[i].getCouleurPion() == joueur.getCouleur()) {
                listeChoix.removeAllElements();
                indiceCaseSelectionnee = i;
                getChoixPossible(grille[i].getLigne(), grille[i].getColonne());
                if (peutCapturer() > 0) {
                    listeChoix.removeAllElements();
                    listeChoix.addAll(sauvegardeListe);
                    indiceCaseSelectionnee = sauvegardeIndice;
                    return true;
                }
            }
        }
        listeChoix.removeAllElements();
        listeChoix.addAll(sauvegardeListe);
        indiceCaseSelectionnee = sauvegardeIndice;
        return false;
    }

    protected boolean delimiteurSeulement() {
        for (int i = 0; i < listeChoix.size(); i++) {
            if (listeChoix.get(i) != DELIMITEUR) {
                return false;
            }
        }
        return true;
    }

    protected int peutCapturer() {
        for (int i = 0; i < listeChoix.size(); i++) {
            if (listeChoix.get(i) < 0) {
                return i + 1;
            }
        }
        return -1;
    }

    public final void reinitialiser() {
        indiceCaseSelectionnee = -1;
        listeChoix.removeAllElements();
    }

    public char getCouleur() {
        return joueur.getCouleur();
    }

    public Vector<Integer> getListeCasesMarquee() {
        return listeChoix;
    }

    public int getIndiceCaseSelectionnee() {
        return indiceCaseSelectionnee;
    }

    public int getNombreDePrise() {
        return nombreDePrise;
    }
    
    public boolean perdu(){
        return joueur.perdu();
    }
}
