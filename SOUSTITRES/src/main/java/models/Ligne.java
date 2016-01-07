package models;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Ligne {

	private List<Mot> contenu;
	
	private StringProperty contenu_edite;
	
	private Placement placement;

	public Ligne(List<Mot> list) {
		super();
		this.contenu = list;
		this.placement = new Placement();
		this.contenu_edite = new SimpleStringProperty(this.toString());
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

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	public StringProperty getContenu_edite() {
		return contenu_edite;
	}

	public void setContenu_edite(StringProperty contenu_edite) {
		this.contenu_edite = contenu_edite;
	}

}
