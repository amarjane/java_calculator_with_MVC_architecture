package com.calculatrice.controler;

import java.util.ArrayList;

import com.calculatrice.model.AbstractModel;

public abstract class AbstractControler {

	/*
	 * le controler controle les saisies de la vue
	 * et les envoie au model calculateur.
	 * il utilise deux variable nombre et opérateur
	 * la variable nombre servira à remplir l'opérande et le résultat du model calculateur.
	 * Une troisième variable sert à stocker les opérateurs autorisés
	 * et la quatrième à stocker une instance du modèle calculateur
	 */
	protected AbstractModel calculator;
	
	protected String operateur;
	protected String nombre;
	
	protected ArrayList<String> listOperateur = new ArrayList<String>();
	
	// on initialise tous les attributs de cette classe.
	public AbstractControler(AbstractModel calculator) {
		
		this.operateur = "";
		this.nombre = "";
		this.calculator = calculator;
		
		this.listOperateur.add("+");
		this.listOperateur.add("-");
		this.listOperateur.add("*");
		this.listOperateur.add("/");
		this.listOperateur.add("%");
		this.listOperateur.add("=");
		
	}
	
	public abstract void control();
	
	public void setOperateur(String operateur) {
		this.operateur = operateur;
		control();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;		
		control();
	}
	
	public void reset() {
		this.calculator.reset();		
	}

}
