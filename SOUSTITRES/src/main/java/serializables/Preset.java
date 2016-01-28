package serializables;

public class Preset {
	
	private Placement placement;
	private String nom;

	public Preset(Placement placement, String nom) {
		super();
		this.placement = placement;
		this.nom = nom;
	}
	public Preset(String nom) {
		this(new Placement(), nom);

	}

	public Placement getPlacement() {
		return placement;
	}
	public void setPlacement(Placement placement) {
		this.placement = placement;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString(){
		return nom;
	}

}
