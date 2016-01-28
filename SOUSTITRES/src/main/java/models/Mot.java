package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javafx.scene.control.Label;
import serializables.Mot_simple;


public class Mot extends Label{
	
	private Mot_simple mot_simple;
		
	public Mot(Mot_simple mot_simple) {
		super();
		this.mot_simple = mot_simple;
		
		this.setText(this.mot_simple.getContenu());
	}
	public Mot(String string, double d, double e, boolean b) {
		this(new Mot_simple(string, d, e, b));
	}

	public String getContenu() {
		return this.mot_simple.getContenu();
	}
	public void setContenu(String contenu) {
		this.mot_simple.setContenu(contenu);
	}
	public double getDebut() {
		return this.mot_simple.getDebut();
	}
	public void setDebut(double debut) {
		this.mot_simple.setDebut(debut);
	}
	public double getDuree() {
		return this.mot_simple.getDuree();
	}
	public void setDuree(double duree) {
		this.mot_simple.setDuree(duree);
	}
	public boolean isEspace() {
		return this.mot_simple.isEspace();
	}
	public void setEspace(boolean espace) {
		this.mot_simple.setEspace(espace);
	}
	public Mot_simple getMot_simple() {
		return mot_simple;
	}
	public void setMot_simple(Mot_simple mot_simple) {
		this.mot_simple = mot_simple;
	}
	
	

}
