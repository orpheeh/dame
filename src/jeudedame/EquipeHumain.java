/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudedame;

import appetat.Utile;
import static appetat.Utile.souris;
import dame.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author Castor
 */
public class EquipeHumain extends Equipe {

    public EquipeHumain(Joueur joueur, Damier damier,
            int positionDamierX, int positionDamierY, int largeurImagePlateau, int hauteurImagePlateau, int tailleCase) {
        super(joueur, damier, positionDamierX, positionDamierY, largeurImagePlateau, hauteurImagePlateau, tailleCase);
    }
    
    @Override
    public boolean jouer() {
        if (souris.boutonAppuyerUneFois(MouseEvent.BUTTON1)) {
            Point p = souris.getPosition();
            if (Utile.pointDansRectangle(p.x, p.y, POSITION_DAMIER_X, POSITION_DAMIER_Y, LARGEUR_IMG_PLATEAU, HAUTEUR_IMG_PLATEAU)) {
               int ligne = (p.y - POSITION_DAMIER_Y) / this.TAILLE_CASE;
               int colonne = (p.x - POSITION_DAMIER_X) / this.TAILLE_CASE;
               return this.valider(ligne, colonne);
            }
        }
        return false;
    }
}
