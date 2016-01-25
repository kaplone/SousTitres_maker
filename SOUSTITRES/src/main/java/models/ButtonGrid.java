package models;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ButtonGrid extends Button {
	
	private int rowInTheGrid;
	
	private Node[] autresElements;
	
	private GridPane grid;
	
	private Ligne ligne;

	
	

	public ButtonGrid(int rowInTheGrid, String s, Button b1, Button b2, TextArea tf, Button b3, GridPane gp, Ligne l) {
		super(s);
		this.rowInTheGrid = rowInTheGrid;
		this.autresElements = new Node [] {b1, b2, tf, b3};
		this.grid = gp;
		this.ligne = l;
	}
	
	public void removeLine(){
		
		
		for (Node n : autresElements){
			grid.getChildren().remove(n);
		}
	}

	public int getRowInTheGrid() {
		return rowInTheGrid;
	}

	public void setRowInTheGrid(int rowInTheGrid) {
		this.rowInTheGrid = rowInTheGrid;
	}

	public Ligne getLigne() {
		return ligne;
	}

	public void setLigne(Ligne ligne) {
		this.ligne = ligne;
	}
    
	public TextField getTextField(){
		return (TextField) this.autresElements[2];
	}
	
	public ButtonGrid getButton2(){
		return (ButtonGrid) this.autresElements[1];
	}

}
