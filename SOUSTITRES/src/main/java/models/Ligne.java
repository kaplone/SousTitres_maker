package models;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Ligne {
	
	private double debut;
	private double duree;
	private ObservableList<Label> contenu;
	
	public Ligne(double debut, double duree, ObservableList<Label> contenu) {
		super();
		this.debut = debut;
		this.duree = duree;
		this.contenu = contenu;
	}
	
	public double getDebut() {
		return debut;
	}
	public void setDebut(double debut) {
		this.debut = debut;
	}
	public double getDuree() {
		return duree;
	}
	public void setDuree(double duree) {
		this.duree = duree;
	}
	public ObservableList<Label> getContenu() {
		return contenu;
	}
	public void setContenu(ObservableList<Label> contenu) {
		this.contenu = contenu;
	}
	
	

}
