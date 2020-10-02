/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composantgraphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Castor
 */
public class EcouteurClavier implements KeyListener {

    private boolean[] touche;
    private int[] appuyer;
    private final int NOMBRE_TOUCHE = 256;
    private char toucheActive = ' ';
    private boolean once = false;
    
    public EcouteurClavier() {
        touche = new boolean[NOMBRE_TOUCHE];
        appuyer = new int[NOMBRE_TOUCHE];
    }

    public boolean boutonAppuyer(int touche) {
        return appuyer[touche] > 0;
    }

    public boolean boutonAppuyerUneFois(int touche) {
        return appuyer[touche] == 1;
    }

    public int getTouche(){
        for(int i = 0; i < NOMBRE_TOUCHE; i++){
            if(boutonAppuyerUneFois(i))
                return i;
        }
        
        return -1;
    }
    
    public void scruterEvenement() {
        for (int i = 0; i < NOMBRE_TOUCHE; i++) {
            if (touche[i]) {
                appuyer[i]++;
            } else {
                appuyer[i] = 0;
            }
        }
    }

    @Override
    public synchronized void keyTyped(KeyEvent e) {
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key >= 0 && key < NOMBRE_TOUCHE) {
            touche[key] = true;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key >= 0 && key < NOMBRE_TOUCHE) {
            touche[key] = false;
            toucheActive = ' ';
        }
    }

}
