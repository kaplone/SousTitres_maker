package serializables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.shape.Rectangle;

public class Placement {
	
	//private int haut;
	//private int lateral;
	
	@JsonIgnore
	private Rectangle rect;
	
	private double [] coordonnees_rectangle;
	private double [] couleur_rectangle;
	
//	public int getHaut() {
//		return haut;
//	}
//	public void setHaut(int haut) {
//		this.haut = haut;
//	}
//	public int getLateral() {
//		return lateral;
//	}
//	public void setLateral(int lateral) {
//		this.lateral = lateral;
//	}

	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
		
		this.coordonnees_rectangle = new double [4];
		this.coordonnees_rectangle[0] = rect.getX();
		this.coordonnees_rectangle[1] = rect.getY();
		this.coordonnees_rectangle[2] = rect.getWidth();
		this.coordonnees_rectangle[3] = rect.getHeight();
//		
//		this.couleur_rectangle[0] = 1.0d;
//		this.couleur_rectangle[1] = 1.0d;
//		this.couleur_rectangle[2] = 1.0d;
//		this.couleur_rectangle[3] = 0.7d;

	}
	public double[] getCoordonnees_rectangle() {
		return coordonnees_rectangle;
	}
	public void setCoordonnees_rectangle(double[] coordonnees_rectangle) {
		this.coordonnees_rectangle = coordonnees_rectangle;
	}
	public double[] getCouleur_rectangle() {
		return couleur_rectangle;
	}
	public void setCouleur_rectangle(double[] couleur_rectangle) {

		this.couleur_rectangle = couleur_rectangle;
	}
    
	
	
	
	

}
