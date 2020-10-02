/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import composantgraphique.EcouteurClavier;
import composantgraphique.EcouteurSouris;

/**
 *
 * @author Castor
 */
public class Utile {
    public static final int LARGEUR_FENETRE = 400, HAUTEUR_FENETRE = 400;
    
    public static Font bubblebody;
    public static Font marketdeco;
    public static Font linLibertineRB;

    public static BufferedImage imagePlateauDamier;

    public static EcouteurSouris souris;
    public static EcouteurClavier clavier;

    public static void chargerRessource() {
        try {
            bubblebody = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/bubblebody.ttf")).deriveFont(Font.BOLD, 14.0f);
            marketdeco = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/Market_Deco.ttf")).deriveFont(Font.BOLD, 12.0f);
            linLibertineRB = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/LinLibertine_RB.ttf")).deriveFont(Font.BOLD, 36.0f);

            imagePlateauDamier = ImageIO.read(new File("assets/images/plateau.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean pointDansRectangle(int x, int y, int rectX, int rectY, int rectLargeur, int rectHauteur) {
        return x > rectX && x < rectX + rectLargeur
                && y > rectY && y < rectY + rectHauteur;
    }

    public static boolean nombreDansIntervalle(int nombre, int borneInf, int borneSup) {
        return nombre >= borneInf && nombre <= borneSup;
    }

    public static void dessinerChampNom(Graphics2D g, int posx, int posy, int largeur, int hauteur, String nom) {
        g.setColor(new Color(255, 0, 0, 50));
        g.fillRoundRect(posx, posy, largeur, hauteur, 10, 10);
        g.setColor(Color.WHITE);
        g.drawRoundRect(posx, posy, largeur, hauteur, 10, 10);

        FontMetrics fm = g.getFontMetrics();
        int x = posx + (largeur - fm.stringWidth(nom)) / 2;
        int y = posy + (hauteur - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent();
        g.drawString(nom, x, y);
    }

    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void dessinerTexte(String texte, int x, int y, Graphics2D g, Font font){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        if(x < 0){
            int largeur = fm.stringWidth(texte);
            x = (LARGEUR_FENETRE - largeur)/2;
        }
        g.drawString(texte, x, y + fm.getAscent());
    }
}
