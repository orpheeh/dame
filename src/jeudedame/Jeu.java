/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudedame;

import composantgraphique.Chrono;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import appetat.*;
import java.awt.Color;
/**
 *
 * @author Castor
 */
public class Jeu extends Fenetre implements Runnable {
    
    private Thread threadDuJeu;
    private volatile boolean running;
    private EtatDuJeu etat;
    private Chrono chrono = new Chrono();
    private final int fps = 60;
    
    public Jeu() {
        appLargeur = Utile.LARGEUR_FENETRE;
        appHauteur = Utile.HAUTEUR_FENETRE;
        this.appCouleurDeFonds = Color.BLACK;
    }

    public void lancer() {
        creerFenetre();
        etat = new MenuPrincipale();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                terminate();
            }
        });
        threadDuJeu = new Thread(this);
        threadDuJeu.start();
        chrono.start();
    }

    @Override
    public void run() {
        running = true;
        long framerateLimite = 1000L/fps;
        while (running) {
            long buff = chrono.getMilliseconde();
            
            boucleDeJeu();
            
            long tempsEcoule = chrono.getMilliseconde() - buff;
            if( tempsEcoule < framerateLimite){
                Utile.sleep(framerateLimite - tempsEcoule);
            }
        }
    }

    private void boucleDeJeu() {
        gestionEvenement();
        update();
        afficherTrame();
    }

    private void gestionEvenement() {
        this.scruterEvenement();
    }

    private void update() {
        etat.update();
        EtatDuJeu suivant = etat.bascule();
        if(suivant != null){
            etat = suivant;
        }
    }

    @Override
    protected void rendu(Graphics g) {
        super.rendu(g);
        etat.rendu(g);
    }

    private void terminate() {
        try {
            running = false;
            threadDuJeu.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
