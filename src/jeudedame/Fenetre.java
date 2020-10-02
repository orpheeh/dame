/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudedame;

import composantgraphique.EcouteurSouris;
import composantgraphique.EcouteurClavier;
import appetat.Utile;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author Castor
 */
public class Fenetre extends JFrame {

    private BufferStrategy bs;
    private Canvas canvas;

    protected Color appCouleurDeFonds = Color.BLACK, appCouleurDuTexte = Color.WHITE;
    protected int appLargeur = 400, appHauteur = 400;
    protected String appTitre = "Dame";

    public Fenetre() {
    }

    protected void creerFenetre() {
        canvas = new Canvas();
        canvas.setBackground(appCouleurDeFonds);
        canvas.setSize(appLargeur, appHauteur);
        canvas.setIgnoreRepaint(true);

        this.setTitle(appTitre);
        this.setIgnoreRepaint(true);
        this.setResizable(false);
        this.getContentPane().add(canvas);
        this.pack();
        this.setVisible(true);
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        Utile.souris = new EcouteurSouris();
        canvas.addMouseListener(Utile.souris);
        canvas.addMouseMotionListener(Utile.souris);

        Utile.clavier = new EcouteurClavier();
        canvas.addKeyListener(Utile.clavier);
    }

    protected void scruterEvenement() {
        Utile.souris.scruterEvenement();
        Utile.clavier.scruterEvenement();
    }

    protected void afficherTrame() {
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    rendu(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    protected void rendu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(appCouleurDuTexte);
    }
}
