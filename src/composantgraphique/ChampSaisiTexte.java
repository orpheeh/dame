/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composantgraphique;

import appetat.Utile;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author Castor
 */
public class ChampSaisiTexte {

    private StringBuffer chaine;
    private final int largeur, hauteur, capacite;
    private final int[] position = new int[2];
    private boolean focus;

    private Color couleurFond = Color.BLACK, couleurBordure = Color.WHITE, couleurTexte = Color.WHITE;

    public ChampSaisiTexte(int positionX, int positionY, int largeur, int hauteur, int capacite) {
        position[0] = positionX;
        position[1] = positionY;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.capacite = capacite;
        this.chaine = new StringBuffer();
        this.focus = false;
    }

    public void update() {
        Point p = Utile.souris.getPosition();
        if ((Utile.souris.boutonAppuyer(MouseEvent.BUTTON1)
                || Utile.souris.boutonAppuyer(MouseEvent.BUTTON2)
                || Utile.souris.boutonAppuyer(MouseEvent.BUTTON3))) {
            focus = Utile.pointDansRectangle(p.x, p.y, position[0], position[1], largeur, hauteur);
        }
        saisir();
    }
    
    public int getCapacite(){
        return capacite;
    }
    
    public String texte(){
        return chaine.toString();
    }
    
    public void dessiner(Graphics g) {
        g.setColor(couleurFond);
        g.fillRoundRect(position[0], position[1], largeur, hauteur, 10, 10);
        g.setColor(couleurBordure);
        g.drawRoundRect(position[0], position[1], largeur, hauteur, 10, 10);

        String str = chaine.toString();
        g.setFont(Utile.bubblebody);
        g.setColor(couleurTexte);
        FontMetrics fm = g.getFontMetrics();

        int x = position[0] + 2;
        int y = position[1] + (hauteur - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent();
        g.drawString(str, x, y);

        System.out.println(focus);
        if (focus) {
            g.setColor(Color.GREEN);
            g.fillRect(x + fm.stringWidth(str) + 2,
                    position[1] + (hauteur - fm.getAscent() - fm.getDescent()) / 2, 3, fm.getAscent() + fm.getDescent());
        }
    }
    
     private void saisir(){
        int caractere = Utile.clavier.getTouche();
        if (caractere == KeyEvent.VK_BACKSPACE) {
            int index = chaine.length()-1;
            if(index >= 0){
                chaine.deleteCharAt(index);
            }
        } else if (caractere >= 0) {
           if(chaine.length() >= capacite) return;
           if(Character.isLetterOrDigit(caractere) || Character.isSpaceChar(caractere)){
               if(Utile.clavier.boutonAppuyer(KeyEvent.VK_SHIFT))
                    chaine.append((char) caractere);
               else {
                   String str = (char)caractere + "";
                   chaine.append(str.toLowerCase());
               }
           }
        }
    }
}
