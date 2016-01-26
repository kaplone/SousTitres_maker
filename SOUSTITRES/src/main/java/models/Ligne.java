package models;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Ligne {

	private List<Mot> contenu;
	
	private StringProperty contenu_edite;
	private StringProperty contenu_edite_backup;
	
	private Placement placement;
	
	private boolean premiereLigne;
	private boolean deuxiemeLigne;
	
	private Mot premierMot;
	private Mot dernierMot;
	
	private Ligne ligneSuivante = null;
	private Ligne ligneprecedente = null;
	
	private TextArea tf;

	public Ligne(List<Mot> list) {
		super();
		this.contenu = list;
		this.placement = new Placement();
		this.contenu_edite = new SimpleStringProperty(this.stringify());
		this.contenu_edite_backup = new SimpleStringProperty(this.stringify());
		this.premiereLigne = false;
		this.deuxiemeLigne = false;
		this.premierMot = list.get(0);
		this.dernierMot = list.get(contenu.size() -1);
	}
	
	public List<Mot> getContenu() {
		return contenu;
	}
	
	public double getDebut() {

		return premierMot.getDebut();

	}
	
	public void setContenu(List<Mot> contenu) {
		this.contenu = contenu;
	}
	
	@Override
	public String toString() {
		
		return this.contenu_edite.get();
		
//		String ligne = "";
//		
//		for (Mot m : contenu){
//			ligne += m.getText();
//		}
//		return ligne;
	}	
	
	public String stringify() {

		
		String ligne = "";
		
		for (Mot m : contenu){
			ligne += m.getText();
		}
		return ligne;
	}
	
	public double getDuree() {
		
		if (this.isPremiereLigne()){
			return ligneSuivante.getDernier_mot().getDebut() + ligneSuivante.getDernier_mot().getDuree() - premierMot.getDebut();
		}
		else {
			return dernierMot.getDebut()+ dernierMot.getDuree() - premierMot.getDebut();
		}

		
	}

	public Mot getPremier_mot() {
		return contenu.get(0);
	}

	public Mot getDernier_mot() {
		return dernierMot;
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

	public Ligne getLigneSuivante() {
		return ligneSuivante;
	}

	public void setLigneSuivante(Ligne ligneSuivante) {
		this.ligneSuivante = ligneSuivante;
	}

	public Ligne getLigneprecedente() {
		return ligneprecedente;
	}

	public void setLigneprecedente(Ligne ligneprecedente) {
		this.ligneprecedente = ligneprecedente;
	}

	public boolean isPremiereLigne() {
		return premiereLigne;
	}

	public void setPremiereLigne(boolean premiereLigne) {
		this.premiereLigne = premiereLigne;
	}

	public boolean isDeuxiemeLigne() {
		return deuxiemeLigne;
	}

	public void setDeuxiemeLigne(boolean deuxiemeLigne) {
		this.deuxiemeLigne = deuxiemeLigne;
	}

	public StringProperty getContenu_edite_backup() {
		return contenu_edite_backup;
	}

	public void setContenu_edite_backup(StringProperty contenu_edite_backup) {
		this.contenu_edite_backup = contenu_edite_backup;
	}

	public TextArea getTf() {
		return tf;
	}

	public void setTf(TextArea tf) {
		this.tf = tf;
	}
	
	public IntegerProperty getLayoutY(boolean synthe){
		
		if (synthe){
			return new SimpleIntegerProperty(482);
		}
		else {
			if (this.premiereLigne){
				return new SimpleIntegerProperty(485);
			}
			else {
				return new SimpleIntegerProperty(498);
			}
		}
		
		
	}
	
	public int getSize(){

		Text text = new Text(contenu_edite_backup.get());
		text.setFont(Font.font("Lucida", 25.0));

		return (int) Math.round(text.getLayoutBounds().getWidth());
		
		
	}

}
