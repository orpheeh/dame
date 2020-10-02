/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composantgraphique;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Castor
 */
public class EcouteurSouris implements MouseMotionListener, MouseListener {

    private boolean[] bouton;
    private int[] scruterBouton;
    private final int NB_BOUTON = 4;
    private Point positionSouris, positionCourante;

    public EcouteurSouris() {
        scruterBouton = new int[NB_BOUTON];
        bouton = new boolean[NB_BOUTON];
        positionSouris = new Point();
        positionCourante = new Point();
    }

    public boolean boutonAppuyer(int num) {
        return scruterBouton[num] > 0;
    }

    public boolean boutonAppuyerUneFois(int num) {
        return scruterBouton[num] == 1;
    }

    public void scruterEvenement() {
        positionSouris = new Point(positionCourante);
        for (int i = 0; i < NB_BOUTON; i++) {
            if (bouton[i]) {
                scruterBouton[i]++;
            } else {
                scruterBouton[i] = 0;
            }
        }
    }

    public Point getPosition() {
        return positionSouris;
    }

    @Override
    public synchronized void mousePressed(MouseEvent arg) {
        bouton[arg.getButton()] = true;
    }

    @Override
    public synchronized void mouseReleased(MouseEvent arg) {
        bouton[arg.getButton()] = false;
    }

    @Override
    public synchronized void mouseClicked(MouseEvent arg) {
    }

    @Override
    public synchronized void mouseEntered(MouseEvent arg) {
        mouseMoved(arg);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent arg) {
        positionCourante = arg.getPoint();
    }

    @Override
    public synchronized void mouseDragged(MouseEvent arg) {
        mouseMoved(arg);
    }

    @Override
    public synchronized void mouseExited(MouseEvent arg) {
        mouseMoved(arg);
    }
}
