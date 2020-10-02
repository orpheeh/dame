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
public class Joueur {

    public static final int HAUT = -1, BAS = 1;
    public static final int GAUCHE = -1, DROITE = 1;

    private final Pion[] pions;
    private final int sensDeDeplacement;
    private final Damier damier;
    private boolean dame = false;
    private final char couleur;

    public Joueur(int sensDeDeplacement, int nombreDePion, char couleur, Damier damier) {
        this.sensDeDeplacement = sensDeDeplacement;
        this.damier = damier;
        this.couleur = couleur;
        pions = new Pion[nombreDePion];
        charger(damier.getGrille(), couleur);
    }

    private void charger(Case[] grille, char couleur) {
        int taille = grille.length;
        for (int i = (sensDeDeplacement - 1) * (taille - 1) / -2, k = 0; k < pions.length; i += sensDeDeplacement, k++) {
            while (grille[i].getCouleur() == Case.CLAIRE) {
                i += sensDeDeplacement;
            }
            pions[k] = new Pion(couleur, grille[i], k);
        }
    }

    public char getCouleur() {
        return couleur;
    }

    public boolean deplacer(int numPion, Case nouvelleCase) {
        pions[numPion].deplacer(nouvelleCase);
        if (!pions[numPion].isDame() && ((sensDeDeplacement == HAUT && nouvelleCase.getLigne() == 0)
                || sensDeDeplacement == BAS && nouvelleCase.getLigne() == damier.getNombreDeCaseParLigne() - 1)) {
            pions[numPion].devenirDame();
            dame = true;
            return true;
        }
        return false;
    }

    public void retirerPion(int numPion) {
        pions[numPion].retirerDuJeu();
    }

    public boolean perdu() {
        for (int i = 0; i < pions.length; ++i) {
            if (pions[i].estCapturer() == false) {
                return false;
            }
        }
        return true;
    }

    public boolean poccedeDame() {
        if (dame == false) {
            return dame;
        }
        for (int i = 0; i < pions.length; ++i) {
            if (pions[i].estCapturer() == false && pions[i].isDame()) {
                return true;
            }
        }
        return false;
    }

    public int getSensDeDeplacement() {
        return sensDeDeplacement;
    }

    public boolean pionEn(int ligne, int colonne) {
        for (int i = 0; i < pions.length; i++) {
            Case c = pions[i].getCase();
            if (c.getLigne() == ligne && c.getColonne() == colonne) {
                return true;
            }
        }
        return false;
    }

    private boolean obstacle(Case c1, Case c2) {
        int pasl = (c1.getLigne() > c2.getLigne() ? -1 : 1);
        int pasc = (c1.getColonne() > c2.getColonne() ? -1 : 1);

        for (int l = c1.getLigne(), c = c1.getColonne(); l != c2.getLigne(); l += pasl, c += pasc) {
            Case tmp = damier.getCase(l * damier.getNombreDeCaseParLigne() + c);
            if (tmp != c1 && tmp != c2 && tmp.estVide() == false) {
                return true;
            }
        }
        return false;
    }

    private int obstacle(Case c1, Case c2, int inutile) {
        int pasl = (c1.getLigne() > c2.getLigne() ? -1 : 1);
        int pasc = (c1.getColonne() > c2.getColonne() ? -1 : 1);
        int n = 0;
        for (int l = c1.getLigne(), c = c1.getColonne(); l != c2.getLigne(); l += pasl, c += pasc) {
            Case tmp = damier.getCase(l * damier.getNombreDeCaseParLigne() + c);
            if (tmp != c1 && tmp != c2 && tmp.estVide() == false) {
                n++;
            }
        }
        return n;
    }
}
