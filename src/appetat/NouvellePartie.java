/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

import static appetat.Utile.*;
import jeudedame.EquipeHumain;
import jeudedame.EquipeMachine;
import jeudedame.Equipe;
import static appetat.Utile.souris;
import composantgraphique.Bouton;
import dame.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Vector;

/**
 *
 * @author Castor
 */
public class NouvellePartie implements EtatDuJeu {
    
    private Color[] couleurEquipe = { Color.BLUE, Color.WHITE };
    private EtatDuJeu etatSuivant;
    private final Damier damier;
    private final int PLATEAU_X;
    private final int PLATEAU_Y;
    private final int LARGEUR_PLATEAU, HAUTEUR_PLATEAU;
    private final int TAILLE_CASE;
    private final String[] nomEquipe = new String[2];
    private final boolean IA;
    private final Equipe[] equipes = new Equipe[2];
    private int indiceEquipeSuivante = 0;
    private int indiceGagnant = -1;
    private MenuVertical menuFin;
    private Bouton boutonAbandonner;
    
    public NouvellePartie(int largeurPlateau, int hauteurPlateau, String nom1, String nom2, boolean IA) {
        this.IA = IA;
        this.HAUTEUR_PLATEAU = largeurPlateau;
        this.LARGEUR_PLATEAU = hauteurPlateau;
        this.TAILLE_CASE = (LARGEUR_PLATEAU - 40) / 10;
        PLATEAU_X = (LARGEUR_FENETRE - LARGEUR_PLATEAU) / 2;
        PLATEAU_Y = (HAUTEUR_FENETRE - HAUTEUR_PLATEAU) / 2;
        nomEquipe[0] = nom1;
        nomEquipe[1] = nom2;

        damier = Damier.create10X10();
        Joueur joueur = new Joueur(Joueur.BAS, damier.getNombreDePionUtile(), '0', damier);
        equipes[0] = new EquipeHumain(joueur, damier,
                (Utile.LARGEUR_FENETRE - LARGEUR_PLATEAU) / 2 + 20, (HAUTEUR_FENETRE - this.HAUTEUR_PLATEAU) / 2 + 20,
                LARGEUR_PLATEAU - 40, HAUTEUR_PLATEAU - 40, TAILLE_CASE);
        joueur = new Joueur(Joueur.HAUT, damier.getNombreDePionUtile(), '1', damier);
        if (IA) {
            equipes[1] = new EquipeMachine(joueur, damier,
                    (LARGEUR_FENETRE - LARGEUR_PLATEAU) / 2 + 20, (HAUTEUR_FENETRE - this.HAUTEUR_PLATEAU) / 2 + 20,
                    LARGEUR_PLATEAU - 40, HAUTEUR_PLATEAU - 40, TAILLE_CASE);
        } else {
            equipes[1] = new EquipeHumain(joueur, damier,
                    (LARGEUR_FENETRE - LARGEUR_PLATEAU) / 2 + 20, (HAUTEUR_FENETRE - this.HAUTEUR_PLATEAU) / 2 + 20,
                    LARGEUR_PLATEAU - 40, HAUTEUR_PLATEAU - 40, TAILLE_CASE);
        }

        chargerMenu();
    }

    private void chargerMenu() {
        int largeur = Utile.LARGEUR_FENETRE / 2;
        int hauteur = 30;
        int x = (Utile.LARGEUR_FENETRE - largeur) / 2;
        int y = HAUTEUR_FENETRE / 4;

        menuFin = new MenuVertical(x, y, largeur, hauteur, "Rejouer", "Menu principal");
        menuFin.addAction(0, () -> {
            etatSuivant = new NouvellePartie(LARGEUR_PLATEAU, HAUTEUR_PLATEAU, nomEquipe[0], nomEquipe[1], IA);
        });

        menuFin.addAction(1, () -> {
            etatSuivant = new MenuPrincipale();
        });
        
        boutonAbandonner = new Bouton("Abondonner", LARGEUR_FENETRE/3, 30, (LARGEUR_FENETRE - LARGEUR_FENETRE/3)/2, 20);
        boutonAbandonner.setAction(() -> {
            etatSuivant = new MenuPrincipale();
        });
    }

    @Override
    public void update() {
        if (indiceGagnant < 0) {
            if (equipes[indiceEquipeSuivante].jouer()) {
                passerLaMain();
            }
            boutonAbandonner.update();
        } else {
            menuFin.update();
        }
    }

    @Override
    public EtatDuJeu bascule() {
        return etatSuivant;
    }

    @Override
    public void rendu(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = PLATEAU_X;
        int y = PLATEAU_Y;
        g.drawImage(Utile.imagePlateauDamier, x, y, null);
        x += 20;
        y += 20;
        g.drawImage(dessiner(), x, y, null);
        dessinerPrise(g, LARGEUR_PLATEAU, y + HAUTEUR_PLATEAU - 20, 30, 1);
        dessinerPrise(g, x, y + HAUTEUR_PLATEAU - 20, 30, 0);
        dessinerInterfaceUtilisateur(g);
        boutonAbandonner.dessiner(g);
        
        if (indiceGagnant >= 0) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE);
            menuFin.dessiner(g);
            g.setColor(Color.WHITE);
            Utile.dessinerTexte("Victoire de " + nomEquipe[indiceGagnant], -1, menuFin.getPosition().x + menuFin.getHauteur() + 20, g, Utile.linLibertineRB);
        }
    }

    private void dessinerPrise(Graphics2D g, int posx, int posy, int diametre, int indiceEquipe) {
        g.setFont(Utile.marketdeco);
        g.setColor(new Color(255, 0, 0, 50));
        if (indiceEquipeSuivante == indiceEquipe) {
            g.setColor(new Color(0, 255, 0, 50));
        }
        g.fillOval(posx, posy, diametre, diametre);
        g.setColor(couleurEquipe[indiceEquipe]);
        g.drawOval(posx, posy, diametre, diametre);

        FontMetrics fm = g.getFontMetrics();
        String num = equipes[indiceEquipe].getNombreDePrise() + "";
        int x = posx + (diametre - fm.stringWidth(num)) / 2 + 1;
        int y = posy + (diametre - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent();
        g.setColor(Color.WHITE);
        g.drawString(num, x, y);
    }

    private BufferedImage dessiner() {
        BufferedImage img = new BufferedImage(LARGEUR_PLATEAU, HAUTEUR_PLATEAU, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Case[] grille = damier.getGrille();
        for (Case grille1 : grille) {
            dessinerMarque(grille1, g);
            dessinerPion(grille1, g);
        }
        g.dispose();
        return img;
    }

    private void dessinerPion(Case cases, Graphics2D g) {
        if (cases.estVide()) {
            return;
        }
        int diametre = TAILLE_CASE * 3 / 4;
        int x = cases.getColonne() * TAILLE_CASE + (TAILLE_CASE - diametre) / 2;
        int y = cases.getLigne() * TAILLE_CASE + (TAILLE_CASE - diametre) / 2;
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x, y + 2, diametre, diametre);
        if (cases.getCouleurPion() == equipes[0].getCouleur()) {
            g.setColor(couleurEquipe[0]);
        } else {
            g.setColor(couleurEquipe[1]);
        }
        g.fillOval(x, y, diametre, diametre);
        if (cases.pionIsDame()) {
            g.setColor(new Color(255, 127, 39));
            g.drawOval(x + (diametre - diametre / 2) / 2, y + (diametre - diametre / 2) / 2, diametre / 2, diametre / 2);
        }
    }

    private void dessinerMarque(Case cases, Graphics2D g) {
        Vector<Integer> liste = equipes[indiceEquipeSuivante].getListeCasesMarquee();
        int indice = cases.getLigne() * damier.getNombreDeCaseParLigne() + cases.getColonne();
        if ((liste.contains(indice) && indice != Equipe.DELIMITEUR)
                || indice == equipes[indiceEquipeSuivante].getIndiceCaseSelectionnee()) {
            g.setColor(new Color(0, 242, 0, 128));
            g.fillRect(cases.getColonne() * TAILLE_CASE, cases.getLigne() * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
        } else if (liste.contains(-indice) && indice != Equipe.DELIMITEUR) {
            g.setColor(new Color(251, 0, 0, 128));
            g.fillRect(cases.getColonne() * TAILLE_CASE, cases.getLigne() * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
        }
    }

    private void dessinerInterfaceUtilisateur(Graphics2D g) {
        g.setFont(Utile.marketdeco);
        Utile.dessinerChampNom(g, PLATEAU_X + (LARGEUR_PLATEAU - 100) / 2, PLATEAU_Y + HAUTEUR_PLATEAU, 100, 30, nomEquipe[indiceEquipeSuivante]);
    }

    public void passerLaMain() {
        indiceEquipeSuivante = 1 - indiceEquipeSuivante;
        if (equipes[indiceEquipeSuivante].perdu()) {
            indiceGagnant = 1 - indiceEquipeSuivante;
        }
        if (equipes[1 - indiceEquipeSuivante].perdu()) {
            indiceGagnant = indiceEquipeSuivante;
        }
    }
}
