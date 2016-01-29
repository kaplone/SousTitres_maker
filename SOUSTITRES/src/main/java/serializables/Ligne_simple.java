package serializables;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Ligne_simple {
     
	protected List<Mot_simple> simple_contenu;
	
	protected String simple_contenu_edite;
	
	private Placement placement;
	
	protected boolean premiereLigne;
	private boolean deuxiemeLigne;
	
	protected Mot_simple simple_premierMot;
	protected Mot_simple simple_dernierMot;
	
	private Ligne_simple simple_ligneSuivante = null;

	public Ligne_simple(List<Mot_simple> list) {
		
		this.simple_contenu = list;
		//this.placement = new Placement();
		this.simple_contenu_edite = this.stringify();
		this.premiereLigne = false;
		this.deuxiemeLigne = false;
		this.simple_premierMot = list.get(0);
		this.simple_dernierMot = list.get(simple_contenu.size() -1);
	}
	
	public Ligne_simple() {
		this.placement = new Placement();
	}
	
	public List<Mot_simple> getContenu() {
		return simple_contenu;
	}
	
	public double getDebut() {

		return simple_premierMot.getDebut();

	}
	
	public void setContenu(List<Mot_simple> contenu) {
		this.simple_contenu = contenu;
	}
	
	@Override
	public String toString() {
		
		return this.simple_contenu_edite;
	
	}	
	
	public String stringify() {

		
		String ligne = "";
		
		for (Mot_simple m : simple_contenu){
			ligne += m.getContenu();
		}
		return ligne;
	}
	
	public double getDuree() {
		
		if (this.isPremiereLigne()){
			return simple_ligneSuivante.getDernier_mot().getDebut() + simple_ligneSuivante.getDernier_mot().getDuree() - simple_premierMot.getDebut();
		}
		else {
			return simple_dernierMot.getDebut()+ simple_dernierMot.getDuree() - simple_premierMot.getDebut();
		}

		
	}

	public Mot_simple getPremier_mot() {
		return simple_contenu.get(0);
	}

	public Mot_simple getDernier_mot() {
		return simple_dernierMot;
	}

	public Placement getSimplePlacement() {
		return placement;
	}

	public void setSimplePlacement(Placement placement) {
		this.placement = placement;
	}

	public String getSimpleContenu_edite() {
		return simple_contenu_edite;
	}

	public void setSimpleContenu_edite(String contenu_edite) {
		this.simple_contenu_edite = contenu_edite;
	}

	public Ligne_simple getSimpleLigneSuivante() {
		return simple_ligneSuivante;
	}

	public void setSimpleLigneSuivante(Ligne_simple ligneSuivante) {
		this.simple_ligneSuivante = ligneSuivante;
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

		Text text = new Text(simple_contenu_edite);
		text.setFont(Font.font("Lucida", 25.0));

		return (int) Math.round(text.getLayoutBounds().getWidth());
		
		
	}

}
