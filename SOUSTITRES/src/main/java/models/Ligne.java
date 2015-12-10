package models;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Ligne {

	private List<Mot> contenu;

	public Ligne(List<Mot> list) {
		super();
		this.contenu = list;
	}
	
	public List<Mot> getContenu() {
		return contenu;
	}
	
	public double getDebut() {
		
		return getPremier_mot().getDebut();
	}
	
	public void setContenu(List<Mot> contenu) {
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
 
		return getDernier_mot().getDebut()+ getDernier_mot().getDuree() - this.getDebut();
		
	}

	public Mot getPremier_mot() {
		return contenu.get(0);
	}

	public Mot getDernier_mot() {
		return contenu.get(contenu.size() -1);
	}
	
	
}
