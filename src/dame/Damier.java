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
public class Damier {

    private Case[] grille;
    private final int nombreDeCaseParLigne;

    private Damier(int nombreDeCaseParLigne) {
        this.nombreDeCaseParLigne = nombreDeCaseParLigne;
        grille = new Case[nombreDeCaseParLigne * nombreDeCaseParLigne];
        chargerGrille();
    }

    private void chargerGrille() {
        for (int i = 0; i < grille.length; ++i) {
            int colonne = i % nombreDeCaseParLigne;
            int ligne = i / nombreDeCaseParLigne;
            boolean couleur = (i < nombreDeCaseParLigne) ? (i % 2 == 0 ? Case.CLAIRE : Case.FONCE)
                    : !grille[i - nombreDeCaseParLigne].getCouleur();
            grille[i] = new Case(ligne, colonne, couleur);
        }
    }

    public static Damier create8X8() {
        return new Damier(8);
    }

    public static Damier create10X10() {
        return new Damier(10);
    }

    public static Damier create12X12() {
        return new Damier(12);
    }

    public Case[] getGrille() {
        return grille;
    }

    public int getNombreDeCaseParLigne() {
        return nombreDeCaseParLigne;
    }

    public int getNombreDeCase() {
        return grille.length;
    }

    public Case getCase(int pos) {
        return grille[pos];
    }

    public Case getCase(int i, int j) {
        for (int k = 0; k < grille.length; k++) {
            if (grille[k].getLigne() == i && grille[k].getColonne() == j) {
                return grille[k];
            }
        }
        return null;
    }

    public int getNombreDePionUtile() {
        return (grille.length - 2 * nombreDeCaseParLigne) / 4;
    }

    private void toStringCase(StringBuffer buff) {
        for (int i = 0; i < grille.length; i++) {
            buff.append(grille[i] + " ");
            if (grille[i].getColonne() == (nombreDeCaseParLigne - 1)) {
                buff.append('\n');
            }
        }
    }

    private void dessinerDamier(StringBuffer buff) {
        final byte TAILLE_CASE = 3;
        char caractere = ' ';
        Pion p = null;
        for (int i = 0; i < nombreDeCaseParLigne * TAILLE_CASE; ++i) {
            for (int j = 0; j < nombreDeCaseParLigne; ++j) {
                caractere = ' ';
                if (grille[(i / TAILLE_CASE) * nombreDeCaseParLigne + j].getCouleur() == Case.FONCE) {
                    caractere = '.';
                    int c = -1;
                    if ((c = grille[(i / TAILLE_CASE) * nombreDeCaseParLigne + j].getCouleurPion()) > 0) {
                        caractere = (char) c;
                    }
                }
                for (int k = 0; k < TAILLE_CASE; k++) {
                    int n = grille[(i / TAILLE_CASE) * nombreDeCaseParLigne + j].getNumeroPion();
                    if (i % TAILLE_CASE == 1 && k == TAILLE_CASE / 2 && n >= 0) {
                        buff.append(n + (n > 9 ? "" : " "));
                    } else {
                        buff.append(caractere + " ");
                    }
                }
            }
            buff.append('\n');
        }
    }

    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        dessinerDamier(strBuff);
        return strBuff.toString();
    }
}
