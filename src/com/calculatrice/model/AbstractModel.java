package com.calculatrice.model;

import java.util.ArrayList;

import com.calculatrice.observer.Observable;
import com.calculatrice.observer.Observer;

public abstract class AbstractModel implements Observable {
	
	/*
	 * le result va stocker l'ancienne opérande
	 * l'operator stocke l'opérateur et
	 * l'opérande stocke la nouvelle saisie
	 */
	protected Double result;
	protected String operator;
	protected String operande;
	
	// le calculateur est observé par la vue calculette
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	// on initialise nos attributs à l'instanciation de cette classe
	public AbstractModel() {
		
		this.result = 0.;
		this.operator = "";
		this.operande = "";
		
	}
	
	public abstract void reset();
	
	public abstract void calcul();
	
	public abstract void getResult();

	public abstract void setNombre(String nbr);
	
	public abstract void setOperator(String operator);
	
	@Override
	public void addObserver(Observer obs) {
		
		this.listObserver.add(obs);

	}

	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<Observer>();

	}

	/* 
	 * (non-Javadoc)
	 * @see com.calculatrice.observer.Observable#notifyObserver(java.lang.String)
	 * quand l'opérande est mise à jour (par concaténation des nouvelles saisies de chiffres
	 * on prévient l'observer calculette pour afficher l'opérande
	 * durant le calcul (cf fonction calcul)
	 * durant la saisie d'un chiffre (cf fonction setNombre)
	 */
	
	@Override
	public void notifyObserver(String str) {
		
		if(str.matches("^0[0-9]+"))
			str = str.substring(1, str.length());
		
		for(Observer obs : this.listObserver)			
			obs.update(str);
		
	}

}
