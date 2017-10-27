package com.calculatrice.controler;

import com.calculatrice.model.AbstractModel;


public class Controler extends AbstractControler {


	public  Controler(AbstractModel calculator) {
		
		super(calculator);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.calculatrice.controler.AbstractControler#control()
	 * si l'opérateur est correct, on envoie au model calculateur.
	 * si c'est un signe =, on calcule le résultat
	 * sinon on envoie au model calculateur
	 * si le nombre est correct, on envoie au model calculateur 
	 */
	public void control(){
		
		if(this.listOperateur.contains(this.operateur))	
			if(this.operateur.equals("="))
				this.calculator.getResult();
			else
				this.calculator.setOperator(this.operateur);
		
		if(this.nombre.matches("^[0-9.]+$"))
			this.calculator.setNombre(this.nombre);
		
		// on oublie pas de tout réinitialiser
		this.operateur = "";
		this.nombre = "";
			
	}
	

}
