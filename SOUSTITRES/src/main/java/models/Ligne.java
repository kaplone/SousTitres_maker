package models;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import serializables.Ligne_simple;
import serializables.Mot_simple;

public class Ligne{
     
	private List<Mot> contenu;
	
	private StringProperty contenu_edite;
	private StringProperty contenu_edite_backup;
	
	private Ligne ligneSuivante = null;
	private Ligne ligneprecedente = null;

	private TextArea tf;
	
	private Ligne_simple ligne_simple;

	public Ligne(List<Mot> list, Ligne_simple ligne_simple) {
		super();
		this.contenu = list;
		this.contenu_edite = new SimpleStringProperty(ligne_simple.getSimpleContenu_edite());
		this.contenu_edite_backup = new SimpleStringProperty(ligne_simple.getSimpleContenu_edite());
		this.ligne_simple = ligne_simple;
	}
	
	public List<Mot> getContenu() {
		return contenu;
	}
	
	public double getDebut() {

		return this.ligne_simple.getPremier_mot().getDebut();

	}
	
	public void setContenu(List<Mot> contenu) {
		this.contenu = contenu;
	}
	
	@Override
	public String toString() {
		
		return this.contenu_edite.get();

	}	
	
	public String stringify() {

		
		String ligne = "";
		
		for (Mot_simple m : ligne_simple.getContenu()){
			ligne += m.getContenu();
		}
		return ligne;
	}
	
	public double getDuree() {
		
		if (this.ligne_simple.isPremiereLigne()){
			return ligneSuivante.ligne_simple.getDernier_mot().getDebut() + ligneSuivante.ligne_simple.getDernier_mot().getDuree() - ligne_simple.getPremier_mot().getDebut();
		}
		else {
			return ligne_simple.getDernier_mot().getDebut()+ ligne_simple.getDernier_mot().getDuree() - ligne_simple.getPremier_mot().getDebut();
		}	
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
			if (this.ligne_simple.isPremiereLigne()){
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

	public Ligne_simple getLigne_simple() {
		return ligne_simple;
	}

	public void setLigne_simple(Ligne_simple ligne_simple) {
		this.ligne_simple = ligne_simple;
	}

	public double getAlpha(double tc) {
		
		
		return 1;
		
		
//		System.out.println("tc :  " + tc);
//		System.out.println("getDebut() :  " + getDebut());
//		System.out.println("getDuree :  " + getDuree());
//
//		
//		
//		if (tc - getDebut() <= 0.1){
//			return (tc - getDebut()) * 10 ;
//		}
//		else if (getDebut() + getDuree() - tc <= 0.1 ){
//			return (getDebut() + getDuree() - tc) * 10;
//		}
//		else {
//			return 1;
//		}
		
	}	

}
