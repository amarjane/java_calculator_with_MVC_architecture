package com.calculatrice.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.calculatrice.controler.AbstractControler;
import com.calculatrice.observer.Observer;

public class Calculette extends JFrame implements Observer {

	/*
	Cette classe est une fenêtre qui affiche ses attributs ecranContainer qui contient
	ecranPanel au nord, chiffrePanel au centre, operateurPanel à l'est.
	L'ecranPanel contient l'ecranLabel qui affiche des nombres sous forme de string.
	Le chiffrepanel et l'operateurPanel contiennent 
	des boutons définis à partir du tableau de string et du tableau de boutons.
	
	Tout cela est contrôlé par un controler.
	
	*/
	private static final long serialVersionUID = 6479345092571650430L;
	 
	
	String[] tab_string = {"1", "2", "3", "4", "5", 
								"6", "7", "8", "9", "0", 
									".", "=", "C", "+", "-", 
										"*", "/", "%"};
	JButton[] tab_button = new JButton[tab_string.length];
	
	private JLabel ecranLabel = new JLabel("0.0");
	private JPanel containerPanel, operateurPanel, chiffrePanel, ecranPanel;
	
	private Dimension dim = new Dimension(50, 40);
	private Dimension dim2 = new Dimension(50, 31);
	
	private AbstractControler controler;
	
	public Calculette(AbstractControler controler) {
				
		this.controler = controler;
		initComposant();
		initThis();
		
	}
	
	/* fonctions d'initalisation dans l'ordre :
	 * 		la fenètre,
	 * 		les composants, 
	 * 		les ecrans,
	 * 		les autres panels et
	 * 		les boutons avec leur actions
	 */
	public void initThis() {
		
		this.setSize(240, 260);
		this.setTitle("Calculette");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);		
		this.setContentPane(this.containerPanel);
		this.setVisible(true);
		
	}
	
	public void initComposant() {
		
		this.initEcrans();
		this.initPanels();
		this.initAction();
		this.initContainer();
		
	}
	
	public void initEcrans() {
		
		Font police = new Font("Arial", Font.BOLD, 20);
		
	    this.ecranLabel = new JLabel("0.0");
	    this.ecranLabel.setFont(police);
	    this.ecranLabel.setHorizontalAlignment(JLabel.RIGHT);
	    this.ecranLabel.setPreferredSize(new Dimension(220, 20));
	    
		this.ecranPanel = new JPanel();
	    this.ecranPanel.setPreferredSize(new Dimension(220, 30));
	    this.ecranPanel.add(this.ecranLabel);
	    this.ecranPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    
	}
	
	public void initPanels() {
		
		this.operateurPanel = new JPanel();    
	    this.operateurPanel.setPreferredSize(new Dimension(55, 225));
	    this.chiffrePanel = new JPanel();
	    this.chiffrePanel.setPreferredSize(new Dimension(165, 225));
	    		
	}
	
	public void initContainer() {
		
		this.containerPanel = new JPanel();
		this.containerPanel.add(this.ecranPanel, BorderLayout.NORTH);
		this.containerPanel.add(this.chiffrePanel, BorderLayout.CENTER);
		this.containerPanel.add(this.operateurPanel, BorderLayout.EAST);
	    
	}
	
	/*
	 * Initialisation du chiffrePanel en séparant trois actions :
	 * chiffreAction (écrire un chiffre)
	 * operateurAction (utiliser un opérateur)
	 * resetAction (C pour tout effacer)
	*/
	public void initAction() {
		
		for(int i = 0; i < this.tab_string.length; i++) {
			
			this.tab_button[i] = new JButton(tab_string[i]);
			this.tab_button[i].setPreferredSize(this.dim);
			
			switch(i){
			
				case 11 :
					this.tab_button[i].addActionListener(new OperateurListener());
					this.chiffrePanel.add(this.tab_button[i]);
					break;
					
				case 12 :
					this.tab_button[i].setForeground(Color.red);
					this.tab_button[i].setPreferredSize(this.dim2);
					this.tab_button[i].addActionListener(new ResetListener());
					this.operateurPanel.add(this.tab_button[i]);
					break;
					
				case 13 :
				case 14 :
				case 15 :
				case 16 :
				case 17 :
					this.tab_button[i].setForeground(Color.red);
					this.tab_button[i].setPreferredSize(dim2);
					this.tab_button[i].addActionListener(new OperateurListener());
					this.operateurPanel.add(this.tab_button[i]);
					break;
					
				default :
					this.tab_button[i].addActionListener(new ChiffreListener());
					this.chiffrePanel.add(tab_button[i]);					
					break;                       
			}                    		
		}              		
	}
	
	/*
	 * le model calculator nous prévient de mettre à jour l'écran 
	 * une fois le control fait par le controler et 
	 * les calculs faits par le model
	*/
	@Override
	public void update(String str) {
		
		this.ecranLabel.setText(str);	
		
	}
	
	/*
	Action chiffre, Action opérateur et Action reset
	*/
	class ChiffreListener implements ActionListener{ 

		public void actionPerformed(ActionEvent e) {
			
			String str = ((JButton) e.getSource()).getText();
			
			// Si l'écran contient des chiffres autre que 0, on concatène la saisie à ces chiffres.
			if(!ecranLabel.getText().equals("0")) {
				str = ecranLabel.getText() + str;
				
			}
			
			/* S'il l'écran contient uniquement un 0, le chiffre saisi est envoyé au controler
			 * la fonction control() du controler l'envoie au calculateur
			 * le calculateur l'ajoute à l'opérande (concaténation)
			 * et notifie l'observer (cette classe) du changement de l'opérande
			 * la fonction update met à jour l'écranLabel
			 */ 
			
			controler.setNombre(((JButton) e.getSource()).getText());
			                
		}
	}

	class OperateurListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			/* le controler récupère la saisie et la stocke dans son atribue opérateur
			 * la fonction control() contrôle s'il s'agit bien d'un opérateur
			 * et l'envoie au calculateur (model)
			 */
			controler.setOperateur(((JButton)e.getSource()).getText());
		
		}           
	}

	class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			/*
			 * En cliquant sur la touche C,
			 * on réinitialise le controler
			 * qui réinitialise le model calculateur
			 * qui met la case resultat à 0
			 * et vide l'opérande et l'opérateur
			 */
			controler.reset();
		
		}               
	}
}
