/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

import composantgraphique.Action;
import composantgraphique.Bouton;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Castor
 */
public class MenuVertical extends Menu {
    private final Bouton[] boutons;
    private int espaceEntreBouton = 10;
    private final int LARGEUR_BOUTON, HAUTEUR_BOUTON;
    private final int X, Y;
    
    public MenuVertical(int x, int y, int largeurBouton, int hauteurBouton, String ... options){
        super(options);
        boutons = new Bouton[options.length];
        LARGEUR_BOUTON = largeurBouton;
        HAUTEUR_BOUTON = hauteurBouton;
        X = x;
        Y = y;
        chargement();
    }
    
    private void chargement(){
        int espaceH = HAUTEUR_BOUTON + espaceEntreBouton;
        for(int i = 0; i < boutons.length; i++){
            boutons[i] = new Bouton(options[i], LARGEUR_BOUTON, HAUTEUR_BOUTON, X, Y + i*espaceH);
        }
    }
    
    public void update(){
        for(int i = 0; i < boutons.length; ++i){
            boutons[i].update();
        }
    }
    
    public void addAction(int index, Action action){
        boutons[index].setAction(action);
    }
    
    public void dessiner(Graphics2D g){
        for (Bouton bouton : boutons) {
            bouton.dessiner(g);
        }
    }
    
    public int getHauteur(){
        return (HAUTEUR_BOUTON + espaceEntreBouton) * boutons.length;
    }
    
    public void setEspace(int espace){
        espaceEntreBouton = espace;
    }
    
    public Point getPosition(){
        return new Point(X, Y);
    }
}
