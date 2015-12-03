package models;

import application.SousTitres_controller;
import javafx.scene.control.Label;

public class Espace extends Label{
	
	private boolean permanent;
	
	

	public Espace(SousTitres_controller controleur) {
		super(" ");
		this.setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
		
		this.setOnMouseClicked(a -> controleur.onEspaceSelect(a));
		this.setOnMouseEntered(a -> controleur.onEspaceEnter(a));
		this.setOnMouseExited(a -> controleur.onEspaceExit(a));
		this.permanent = false;
		
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	
	

}
