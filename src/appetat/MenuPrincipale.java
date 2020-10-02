/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

import composantgraphique.Action;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


/**
 *
 * @author Castor
 */
public class MenuPrincipale implements EtatDuJeu {
    private EtatDuJeu etatSuivant = null;
    private final MenuVertical leMenu;
    
    public MenuPrincipale() {
        int largeurBouton = 2*Utile.LARGEUR_FENETRE/3;
        int hauteurBouton = 30;
        int x = (Utile.LARGEUR_FENETRE - largeurBouton)/2;
        int y = (Utile.HAUTEUR_FENETRE - hauteurBouton)/2;
        
        leMenu = new MenuVertical(x, y, largeurBouton, hauteurBouton, "Mode un joueur", "Mode deux joueur", "Quitter");
        leMenu.addAction(0, () -> {
            etatSuivant = new NouvellePartie(300, 300, "joueur1", "IA", true);
        });
        leMenu.addAction(1, () ->{
            etatSuivant = new NouvellePartie(300, 300, "joueur1", "joueur2", false);
        });
        leMenu.addAction(2, () -> {
            System.exit(0);
        });
    }

    @Override
    public void update() {
        leMenu.update();
    }
    
    @Override
    public void rendu(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setFont(Utile.marketdeco);
        leMenu.dessiner(g2d);
    }

    @Override
    public EtatDuJeu bascule() {
        return etatSuivant;
    }
}