/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dame;

/**
 *
 * @author Castor
 */
public class Pion {
	public static final boolean ETAT_CAPTURER = false, ETAT_ENJEUX = true;
	public static final char NOIR = 'Q', BLANC = 'X', DAME_NOIR = 'q', DAME_BLANC = 'x';
	public enum TypePion { ORDINAIRE, DAME };
	
	protected char couleur;
	protected boolean etat;
	protected int ligne, colonne;
	protected Case casePion;
	protected final int numero;
	protected TypePion type;
	
	public Pion(char couleur, final Case case_initial, int numero){
		this.casePion = case_initial;
		case_initial.placerPion(this);
		this.etat = ETAT_ENJEUX;
		this.type = TypePion.ORDINAIRE;
		this.couleur = couleur;
		this.numero = numero;
	}
	
	public void deplacer(Case newCase){
		newCase.placerPion(casePion.retirerPion());
		casePion = newCase;
	}
	
	public void retirerDuJeu(){
		etat = ETAT_CAPTURER;
		casePion.retirerPion();
		casePion = null;
	}
	
	public void devenirDame(){
		type = TypePion.DAME;
	}
	
	public boolean isDame(){
		return type == TypePion.DAME;
	}
	
	public Case getCase(){
		return casePion;
	}
	
	public char getCouleur(){
		return couleur;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public boolean estCapturer(){
		return etat == ETAT_CAPTURER;
	}
	
	public String toString(){
		return  casePion.toString();
	}    
}
