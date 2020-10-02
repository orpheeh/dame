/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composantgraphique;

import appetat.Utile;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author Castor
 */
public class Bouton {
    private final String NOM;
    private final int LARGEUR;
    private final int HAUTEUR;
    private final int X, Y;
    private Color couleurFond = Color.GREEN;
    private Color couleurSurvol = Color.BLUE;
    private Color couleurTexte = Color.BLACK;
    private Action action;
    
    public Bouton(String nom, int largeur, int hauteur, int x, int y){
        this.LARGEUR = largeur;
        this.HAUTEUR = hauteur;
        this.NOM = nom;
        this.X = x;
        this.Y = y;
    }
    
    public void setCouleurFond(Color nouvelleCouleur){
        this.couleurFond = nouvelleCouleur;
    }
    
    public void setCouleurSurvol(Color nouvelleCouleur){
        this.couleurSurvol = nouvelleCouleur;
    }
    
    public void setCouleurTexte(Color nouvelleCouleur){
        this.couleurTexte = nouvelleCouleur;
    }
    
    public void dessiner(Graphics2D g){
        FontMetrics fm = g.getFontMetrics();
        int hauteurTexte = fm.getAscent() + fm.getDescent() + fm.getLeading();
        int largeurTexte = fm.stringWidth(NOM);
        int xTexte= X + (LARGEUR - largeurTexte)/2;
        int yTexte = Y + (HAUTEUR - hauteurTexte)/2 + fm.getAscent();
        
        g.setColor(couleurFond);
        if(survol()){
            g.setColor(couleurSurvol);
        }
        g.fillRoundRect(X, Y, LARGEUR, HAUTEUR, 10, 10);
        g.setColor(couleurTexte);
        g.drawString(NOM, xTexte, yTexte);
    }
    
    public void update(){
        if(survol() && Utile.souris.boutonAppuyerUneFois(MouseEvent.BUTTON1)){
            if(action != null){
                action.execute();
            }
        }
    }
    
    public void setAction(Action action){
        this.action = action;
    }
    
    private boolean survol(){
        Point p = Utile.souris.getPosition();
        return Utile.pointDansRectangle(p.x, p.y, X, Y, LARGEUR, HAUTEUR);
    }
}
