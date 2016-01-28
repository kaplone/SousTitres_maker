package serializables;

public class Mot_simple{
	
	private String contenu;
	private double debut;
	private double duree;
	private boolean espace;
		
	public Mot_simple(String contenu, double debut, double duree, boolean espace) {

		this.contenu = contenu;
		this.debut = debut;
		this.duree = duree;
		this.espace = espace;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
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
	public boolean isEspace() {
		return espace;
	}
	public void setEspace(boolean espace) {
		this.espace = espace;
	}
	
	

}
