/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudedame;

import appetat.Utile;

import dame.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Castor
 */
public class EquipeMachine extends Equipe {

    private Random random = new Random();

    public EquipeMachine(Joueur joueur, Damier damier,
            int positionDamierX, int positionDamierY, int largeurImagePlateau, int hauteurImagePlateau, int tailleCase) {
        super(joueur, damier, positionDamierX, positionDamierY, largeurImagePlateau, hauteurImagePlateau, tailleCase);
    }

    @Override
    public boolean jouer() {
        if (aucunChoixPossible() == false) {
            int indice = peutCapturer();
            if (indice < 0) {
                indice = random.nextInt(listeChoix.size());
                while (listeChoix.get(indice) == DELIMITEUR) {
                    indice = random.nextInt(listeChoix.size());
                }
            }
            int numCase = listeChoix.get(indice);
            if (numCase < 0) {
                numCase = -numCase;
            }
            Utile.sleep(1000L);
            return valider(numCase / NOMBRE_CASE_LIGNE, numCase % NOMBRE_CASE_LIGNE);
        }
        int ligne = random.nextInt(NOMBRE_CASE_LIGNE);
        int colonne = random.nextInt(NOMBRE_CASE_LIGNE);
        valider(ligne, colonne);
        return false;
    }
}
