package com.calculatrice.model;

public class Calculator extends AbstractModel{

	public Calculator() {
		
		super();
		
	}
	
	@Override
	public void reset() {
		
		this.result = (double) 0;
		this.operande = "";
		this.operator = "";
		
		notifyObserver(String.valueOf(this.result));
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.calculatrice.model.AbstractModel#calcul()
	 * si l'opérateur est vide, on n'effectue pas encore d'opération.
	 * dans ce cas, on stocke l'opérande dans le result.
	 * si l'opérande est vide, c'est qu'on a cliqué sur un opérateur, 
	 * dans ce cas, on commence par stocker 0 (+2 = 0+2 !!);
	 * si l'opérateur est plein, on effectue l'opération qui lui correspond
	 * ensuite on vide l'opérande pour la remplir avec la futur saisie.
	 * on notifie l'observateur vue calculette du résultat.
	 * c'est surtout utile si on enchaîne les opérations.
	 */
	@Override
	public void calcul() {
		if(this.operator.equals("")) {
			
			this.result = Double.parseDouble(this.operande.isEmpty() ? "0" : this.operande);
			
		} else {
			
			if(this.operator.equals("+"))
				this.result += Double.parseDouble(this.operande);
			
			if(this.operator.equals("-"))
				this.result -= Double.parseDouble(this.operande);
			
			if(this.operator.equals("*"))
				this.result *= Double.parseDouble(this.operande);
			
			if(this.operator.equals("/")) {
				
				try {
					
					this.result /= Double.parseDouble(this.operande);
				
				} catch (ArithmeticException e) {
					
					this.result = 0.0;
				
				}
				
			}
			
			if(this.operator.equals("%"))				
				this.result = this.result % Double.parseDouble(this.operande);			
		}
		
		this.operande = "";
		notifyObserver(String.valueOf(this.result));
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.calculatrice.model.AbstractModel#getResult()
	 * on vient de cliquer sur égale,
	 * on calcul le resultat avec le result stocké, l'opérande saisie juste avant et l'opérateur saisi entre temps.
	 * la fonction calcul notifie l'observateur vue calculette du résultat qu'il affiche,
	 * on vide l'opérande et l'opérateur qui accueilleront les nouvelles saisies
	 */
	@Override
	public void getResult() {
		this.calcul();		
		this.operator = "";
		this.operande = "";
	}

	/* 
	 * (non-Javadoc)
	 * @see com.calculatrice.model.AbstractModel#setOperator(java.lang.String)
	 * quand on vient de cliquer sur u nouvel opérateur,
	 * on effectue d'abord l'opération de l'ancien opérateur déjà stocker
	 * et on stocke le nouveau.
	 * et on vide l'opérande pour éviter de concaténer le result et l'opérande
	 */
	@Override
	public void setOperator(String operator) {
		this.calcul();
		this.operator = operator;
		
		if(!operator.equals("="))
			this.operande = "";
		
	}

	@Override
	public void setNombre(String nbr) {
		this.operande += nbr;
		notifyObserver(this.operande);
		
	}



}
