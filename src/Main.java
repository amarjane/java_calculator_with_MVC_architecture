
import com.calculatrice.controler.AbstractControler;
import com.calculatrice.controler.Controler;
import com.calculatrice.model.AbstractModel;
import com.calculatrice.model.Calculator;
import com.calculatrice.vue.Calculette;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Instanciation de notre modèle
		AbstractModel calculator = new Calculator();

	    //Création du contrôleur
		AbstractControler controler = new Controler(calculator);

	    //Création de notre fenêtre avec le contrôleur en paramètre
		Calculette calculette = new Calculette(controler);

	    //Ajout de la fenêtre comme observer de notre modèle
		calculator.addObserver(calculette);
		
	}

}
