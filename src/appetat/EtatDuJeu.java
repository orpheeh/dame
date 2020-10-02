/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

import java.awt.Graphics;

/**
 *
 * @author Castor
 */
public interface EtatDuJeu {

    public void update();

    public void rendu(Graphics g);

    public EtatDuJeu bascule();
}
