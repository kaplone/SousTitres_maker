package models;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ButtonGrid extends Button {
	
	private int rowInTheGrid;
	
	private Node[] autresElements;
	
	private GridPane grid;
	
	private Ligne ligne;

	
	

	public ButtonGrid(int rowInTheGrid, Button b1, Button b2, TextField tf, GridPane gp, Ligne l) {
		super();
		this.rowInTheGrid = rowInTheGrid;
		this.autresElements = new Node [] {b1, b2, tf, this};
		this.grid = gp;
		this.ligne = l;
	}
	
	public ButtonGrid(String text, Ligne l){
		super(text);
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


}
