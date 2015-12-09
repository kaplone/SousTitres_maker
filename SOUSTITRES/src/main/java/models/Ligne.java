package models;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Ligne {

	private ObservableList<Mot> contenu;
	
	public Ligne(ObservableList<Mot> contenu) {
		super();
		this.contenu = contenu;
	}
	
	public ObservableList<Mot> getContenu() {
		return contenu;
	}
	
	public double getDebut() {
		return contenu.get(0).getDebut();
	}
	
	public void setContenu(ObservableList<Mot> contenu) {
		this.contenu = contenu;
	}
	
	@Override
	public String toString() {
		
		String ligne = "";
		
		for (Mot m : contenu){
			ligne += m.getText();
		}
		return ligne;
	}	
	
	public double getDuree() {
		
		Mot dernier = contenu.get(contenu.size() -1);
 
		return dernier.getDebut()+ dernier.getDuree() - this.getDebut();
		
	}
}
