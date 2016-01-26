package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

public class Placement {
	
	private int haut;
	private int lateral;
	private Allignement allignement;
	private ObjectProperty<Rectangle> rect;
	
	public Placement(){
		rect = new SimpleObjectProperty<>();
	}
	
	public int getHaut() {
		return haut;
	}
	public void setHaut(int haut) {
		this.haut = haut;
	}
	public int getLateral() {
		return lateral;
	}
	public void setLateral(int lateral) {
		this.lateral = lateral;
	}
	public Allignement getAllignement() {
		return allignement;
	}
	public void setAllignement(Allignement allignement) {
		this.allignement = allignement;
	}
	public ObjectProperty<Rectangle> getRectProperty() {
		return rect;
	}
	public void setRectProperty(ObjectProperty<Rectangle> rect) {
		this.rect = rect;
	}
	public Rectangle getRect() {
		return rect.get();
	}
	public void setRect(Rectangle rect) {
		this.rect.set(rect);
	}
	
	

}
