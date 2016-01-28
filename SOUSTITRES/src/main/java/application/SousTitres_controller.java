package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.ButtonGrid;
import models.Espace;
import models.Ligne;
import models.Mot;
import models.Projet;
import serializables.Ligne_simple;
import serializables.Mot_simple;
import serializables.Placement;
import serializables.Preset;
import utils.ExportASS;
import utils.ExportSRT;
import utils.MediaControl;
import utils.SerialisationJson;

public class SousTitres_controller implements Initializable {
		
	@FXML
	private FlowPane flowpane;
	@FXML
	private Pane pane;
	
	private Rectangle degrade_fx;
	private Rectangle bloc_fx;
	
	@FXML
	private Button export_button;
	
	@FXML
	private VBox index_vbox;
	
	@FXML
	private ChoiceBox<Preset> preset_choiceBox;
	
	@FXML
	private GridPane lignes_grid;
	
	@FXML
    private Button serialize_button;

	
	private int derniere_ligne;

	private Media media;
	private MediaPlayer mediaplayer;
	private MediaControl mediaControl;
	
	private DoubleProperty point_d_entree;
	private double bourage;
	
	private Espace labelCourant;
	
	private ObservableList<Label> mots_observables ;
	private ArrayList<Mot> mots ;
	private LinkedList<Ligne> lignes ;
	private ArrayList<Ligne_simple> lignes_simples ;
	
	private int index_label;
	
	private Map<String, Mot> map_des_mots;
	private Map<String, Ligne> map_des_lignes;
	private Map<String, Ligne_simple> map_des_lignes_simples;
	
	private Mot mot_lu;
	
	private Text text_sous_titre;
	
	private ObjectProperty<String> phrase_affichee; 
	
	private boolean debut_texte = true;
	
	private static final String IM_FICHIER = "fichier_01.png";
	private static final String IM_LIGNE = "retour_ligne_01.png";
	private static final String IM_DELETE = "delete_01.png";
	
	private static final Image IMG_FICHIER = new Image(IM_FICHIER);
	private static final Image IMG_LIGNE =  new Image(IM_LIGNE);
	private static final Image IMG_DELETE =  new Image(IM_DELETE);

	private ImageView imv;
	
	private ObservableList<Preset> observablePresets;
	
	private Ligne ligne;
	private Ligne lignePrecedente;
	
	private Ligne_simple ligne_simple;
	private Ligne_simple ligne_simple_precedente;
	
	private Ligne ligneLue;
	
	private Projet projet;
	
	private ChangeListener<Preset> choiceBoxListener;
	
	public void onExport_button(){
		
		ExportSRT.export_srt_file(lignes);
		ExportASS.export_ass_file(lignes);
		
	}
    
    public void mot_lecture(Mot lu){
    	
    	lu.setStyle("-fx-font-size:20; -fx-background-color: #aa88dd ;");
    	
    	if (mot_lu != null) {
    		mot_lu.setStyle("-fx-font-size:20; -fx-background-color: #eeeeee;");
    	}
    	mot_lu = lu;
    }
    
    public void ligne_lecture(Ligne lu){
    	
    	ligneLue = lu;
    	
    	phrase_affichee.unbind();
    	phrase_affichee.bind(lu.getContenu_edite());
    	
    	text_sous_titre.setLayoutX((940 - text_sous_titre.getLayoutBounds().getWidth())/2);
    	
    	preset_choiceBox.getSelectionModel().selectedItemProperty().removeListener(choiceBoxListener);
    	preset_choiceBox.getSelectionModel().select(lu.getLigne_simple().getSimplePlacement().getRect().getHeight() == 79 ? 0 : 1);
    	preset_choiceBox.getSelectionModel().selectedItemProperty().addListener(choiceBoxListener);
    	
    	text_sous_titre.layoutYProperty().unbind();
    	System.out.println(preset_choiceBox.getSelectionModel().getSelectedItem());
    	text_sous_titre.layoutYProperty().bind(lu.getLayoutY(preset_choiceBox.getSelectionModel().getSelectedItem().getNom().equals("Centre sur synthé")));
    	
    	bloc_fx.heightProperty().unbind();
    	bloc_fx.yProperty().unbind();
    	
    	bloc_fx.heightProperty().bind(lu.getLigne_simple().getSimplePlacement().getRect().heightProperty());
		bloc_fx.yProperty().bind(lu.getLigne_simple().getSimplePlacement().getRect().yProperty());
		
		System.out.println(bloc_fx.getX() + ", " + bloc_fx.getY());
		System.out.println(bloc_fx.getWidth() + " ," + bloc_fx.getHeight());
		
    }
    
    
    public void ajout_ligne_dans_grille(Ligne ligne){
    	
    	Button a = null;
    	Button b = null;
    	Button d = null;
    	TextArea c = null;
    	
    	a = new ButtonGrid(derniere_ligne, "", a, b, c, d, lignes_grid, ligne);

		imv = new ImageView(IMG_LIGNE);
		
		imv.setPreserveRatio(true);
		imv.setFitHeight(16);
		a.setGraphic(imv);
		a.setOnAction((e) -> sautLigne(e));
		
		b = new ButtonGrid(derniere_ligne, String.format("%03d >", ++derniere_ligne), a, b, c, d, lignes_grid, ligne);
		
		c = new TextArea();
		c.setText(ligne.getContenu_edite().get());
		
		StringProperty textEditable = c.textProperty();
    	StringProperty textEdite = ligne.getContenu_edite();
    	
        c.setText(textEdite.get());
    	textEdite.bind(textEditable);

		d = new ButtonGrid(derniere_ligne, "", a, b, c, d, lignes_grid, ligne);
        imv = new ImageView(IMG_DELETE);
		
		imv.setPreserveRatio(true);
		imv.setFitHeight(16);
		d.setGraphic(imv);
		d.setOnAction((e) -> ligneDelete( (ButtonGrid) e.getSource()));
		
		RowConstraints row = new RowConstraints(30);
		//row.setPrefHeight(20);
		row.setMaxHeight(javafx.scene.control.Control.USE_PREF_SIZE);
		row.setMinHeight(javafx.scene.control.Control.USE_PREF_SIZE);
		
		lignes_grid.getRowConstraints().add(row);

		lignes_grid.add(a, 0, derniere_ligne);
		lignes_grid.add(b, 1, derniere_ligne);
		lignes_grid.add(c, 2, derniere_ligne);
		lignes_grid.add(d, 3, derniere_ligne);
		
		ligne.setTf(c);

    }
    
    public void ligneDelete(ButtonGrid bg){
    	
    	bg.removeLine();
    	
    }
    
    public void defaire(MouseEvent me){
    	
    	labelCourant = ((Espace)me.getSource());
    	
    	labelCourant.setPermanent(false);
    	labelCourant.setText(" ");
    	labelCourant.setPadding(new Insets(0, 0, 0, 0));
    	labelCourant.setOnMouseClicked(a -> onEspaceSelect(a));
    	labelCourant.setOnMouseEntered(a -> onEspaceEnter(a));
    	labelCourant.setOnMouseExited(a -> onEspaceExit(a));
    	
    	mots.remove(mots.indexOf(labelCourant) + 1);
    	mots_observables.clear();
    	mots_observables.addAll(mots);
    	
    	flowpane.getChildren().clear();
    	flowpane.getChildren().addAll(mots_observables);
    }
    
    public void onEspaceSelect(MouseEvent me){
    	
    	labelCourant.setPermanent(true);
    	labelCourant.setText("][");
    	labelCourant.setPadding(new Insets(0, 0, 0, 0));
    	labelCourant.setStyle("-fx-font-size:20; -fx-text-fill: #aaaaee");
    	
    	Mot padding = new Espace(this);
    	padding.setPadding(new Insets(0, bourage - 20, 0, 0));
    	padding.setStyle("-fx-font-size:20; -fx-text-fill: #000077");
    	padding.setVisible(true);
    	padding.setOnMouseEntered(null);
    	padding.setOnMouseExited(null);
    	
    	index_label = mots.indexOf(labelCourant);
    	
    	mots.add(index_label + 1, padding);

    	mots_observables.clear();
    	mots_observables.addAll(mots);
    	
    	flowpane.getChildren().clear();
    	flowpane.getChildren().addAll(mots_observables);
    	
    	labelCourant = (Espace) mots.get(index_label);
    	
    	labelCourant.setOnMouseClicked(a -> defaire(me));
    	labelCourant.setOnMouseEntered(null);
    	labelCourant.setOnMouseExited(null);
    	
    	Label l = labelCourant;
    	int index_l = mots.indexOf(l);
    	
    	index_l -= 1 ;
    	l = mots.get(index_l);

    	
    	while (l.getLayoutX() > 0){
    		
    		index_l -= 1 ;
    		l = mots.get(index_l);
    	}
    	
    	List<Mot> mots_ligne =  new ArrayList<>();
    	List<Mot_simple> mots_simples_ligne =  new ArrayList<>();
    	for (int i = index_l; i < index_label; i++){
    		mots_ligne.add(mots.get(i));
    		mots_simples_ligne.add(mots.get(i).getMot_simple());
    	}
    	
    	ligne_simple_precedente = ligne_simple ;
    	ligne_simple = new Ligne_simple(mots_simples_ligne);
    	
    	if (ligne_simple_precedente != null) {
    		ligne_simple_precedente.setSimpleLigneSuivante(ligne_simple);
    	}

    	ligne_simple.setSimplePlacement(preset_choiceBox.getSelectionModel().getSelectedItem().getPlacement());
    	
    	lignePrecedente = ligne;
    	ligne = new Ligne(mots_ligne, ligne_simple);
    	
    	ligne.setLigneprecedente(lignePrecedente);
    	
    	if(lignePrecedente != null){
    		lignePrecedente.setLigneSuivante(ligne);
    	}

    	map_des_lignes.put(String.format("%.02f", ligne.getDebut()), ligne);
    	lignes.add(ligne);
    	
    	lignes_simples.add(ligne_simple);
    	map_des_lignes_simples.put(String.format("%.02f", ligne.getDebut()), ligne_simple);
		
		for (double i = 0.01d; i < ligne.getDuree(); i+=0.01){
			
			map_des_lignes.put(String.format("%.02f", ligne.getDebut() + i ).replace('.', ','), ligne);
			map_des_lignes_simples.put(String.format("%.02f", ligne.getDebut()), ligne_simple);
		}
		
		ajout_ligne_dans_grille(ligne);
    	

    }
    public void onEspaceEnter(MouseEvent me){
    	
    	labelCourant = ((Espace)me.getSource());
    	
    	if (! labelCourant.isPermanent()){
    	
	    	point_d_entree.set(me.getSceneX());
	    	//bourage = 896 - (me.getSceneX() - 1000) - 20;	 
	    	bourage = 896 - (me.getSceneX() - 1000) - 18;
	    	labelCourant.setPadding(new Insets(0, bourage, 0, 0));
    	}
    }
    public void onEspaceExit(MouseEvent me){
    	
    	if (labelCourant != null && ! labelCourant.isPermanent()){
    		((Espace)me.getSource()).setPadding(new Insets(0, 0, 0, 0));
    	}
        
        labelCourant = null;
    }
    
    public void onMouseMove(MouseEvent me){
    	
    	if (labelCourant != null && ! labelCourant.isPermanent() && me.getSceneX() - point_d_entree.get() > 8){
    		labelCourant.setPadding(new Insets(0, 0, 0, 0));
    		labelCourant = null;
    	}
    }
    
	public void playerStatus(MouseEvent me){
		
		if (mediaplayer.getStatus().equals(Status.PLAYING)){
			
			mediaplayer.pause();
			
		}
		else {
			mediaplayer.play();
			System.out.println(mediaplayer.getCurrentTime());
		}	
	}
	
	public void sautLigne(Event e){
		
		ButtonGrid bg = (ButtonGrid) e.getSource();
		GridPane gp = (GridPane) bg.getParent();
		Ligne l = bg.getLigne();
		
		int indice = gp.getChildren().indexOf(bg);
		System.out.println(indice);
		
		lignePrecedente = l.getLigneprecedente();
		
//		Ligne lignePrecedente = lignes.get(lignes.indexOf(l) -1);
		
//		if(! l.equals(lignes.getLast())){
//			Ligne ligneSuivante = lignes.get(lignes.indexOf(l) +1);
//		}
			
		if ( ((ImageView) bg.getGraphic()).getImage().equals(IMG_LIGNE)){
				
			if (! lignePrecedente.getLigne_simple().isDeuxiemeLigne() && ! l.getLigne_simple().isPremiereLigne()){
				ImageView im = new ImageView(IMG_FICHIER);
				im.setPreserveRatio(true);
				im.setFitHeight(16);
				
				bg.setGraphic(im);
				
				gp.getChildren().get(indice + 1).setStyle("-fx-background-color: linear-gradient(#999999, #bbbbbb);");
				
				// changement des drapeaux
				l.getLigne_simple().setDeuxiemeLigne(true);
				lignePrecedente.getLigne_simple().setPremiereLigne(true);	
				
				// copie en backup des contenus étités
				l.getContenu_edite_backup().set(l.getContenu_edite().get());
				lignePrecedente.getContenu_edite_backup().set(lignePrecedente.getContenu_edite().get());
				
				// calcul des tailles des textes
				int taille_l1 = lignePrecedente.getSize();
				int taille_l2  =l.getSize();
				
				// unbind pour pouvoir setter
				l.getContenu_edite().unbind();
				lignePrecedente.getContenu_edite().unbind();
				
                // set des contenus édités avec les nouvelles valeurs
				l.getContenu_edite().set("");
				
				if ( taille_l1 >=  taille_l2){
					
					int nb = (taille_l1 - taille_l2) / 15;
					
					String decallage = "";
					for (int i = 0; i < nb; i++){
						decallage += " ";
					}
					
					lignePrecedente.getContenu_edite().set(lignePrecedente.getContenu_edite_backup().get() + "\n" + decallage + l.getContenu_edite_backup().get());
				}
				else {
					
					int nb = (taille_l2 - taille_l1) / 15;
					String decallage = "";
					for (int i = 0; i < nb; i++){
						decallage += " ";
					}
					
					lignePrecedente.getContenu_edite().set(decallage + lignePrecedente.getContenu_edite_backup().get() + "\n" + l.getContenu_edite_backup().get());
				}
				
				// 
				//StringProperty textEditable = l.getTf().textProperty();
		    	StringProperty textEdite = l.getContenu_edite();
		    	
		        l.getTf().setText(textEdite.get());
		    	//textEdite.bind(textEditable);
		    	
		    	StringProperty textEditable_p = lignePrecedente.getTf().textProperty();
		    	StringProperty textEdite_p = lignePrecedente.getContenu_edite();
		    	
		    	lignePrecedente.getTf().setText(textEdite_p.get());
		    	textEdite_p.bind(textEditable_p);
				
				for (double i = 0.01d; i < lignePrecedente.getDuree(); i+=0.01){
					
					map_des_lignes.replace(String.format("%.02f", lignePrecedente.getDebut() + i ).replace('.', ','), lignePrecedente);
				}				
			}		
		}
		else {
			
			ImageView im = new ImageView(IMG_LIGNE);
			im.setPreserveRatio(true);
			im.setFitHeight(16);
			
			bg.setGraphic(im);

			gp.getChildren().get(indice + 1).setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058);");
			
			// changement des drapeaux
			l.getLigne_simple().setDeuxiemeLigne(false);
			lignePrecedente.getLigne_simple().setPremiereLigne(false);			
			
			// unbind pour pouvoir setter
			l.getContenu_edite().unbind();
			lignePrecedente.getContenu_edite().unbind();
			
			// set des contenus édités avec les nouvelles valeurs
			l.getContenu_edite().set(l.getContenu_edite_backup().get());
			lignePrecedente.getContenu_edite().set(lignePrecedente.getContenu_edite_backup().get());
			
			// 
			StringProperty textEditable = l.getTf().textProperty();
	    	StringProperty textEdite = l.getContenu_edite();
	    	
	        l.getTf().setText(textEdite.get());
	    	textEdite.bind(textEditable);
	    	
	    	StringProperty textEditable_p = lignePrecedente.getTf().textProperty();
	    	StringProperty textEdite_p = lignePrecedente.getContenu_edite();
	    	
	    	lignePrecedente.getTf().setText(textEdite_p.get());
	    	textEdite_p.bind(textEditable_p);

			for (double i = 0.01d; i < l.getDuree(); i+=0.01){
				
				map_des_lignes.replace(String.format("%.02f", l.getDebut() + i ).replace('.', ','), l);
			}
			
			// il peut rester les timecodes de LignePrecedente ...
		}
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		lignes = new LinkedList<Ligne>();
		lignes_simples = new ArrayList<Ligne_simple>();
		
		derniere_ligne = 0;
		lignes_grid.setVgap(5);
	
		phrase_affichee = new SimpleObjectProperty<>();
		
		point_d_entree = new SimpleDoubleProperty();	
		
		projet = new Projet();
		projet.setXml("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/CASTOARCHI_VCASTO_MASTER_sous_titres_v2.xml");
		projet.setVideo("file:///home/autor/Desktop/CASTOARCHI_VCASTO_MASTER.mp4");
		projet.setNom("projet 1");
		
		media = new Media(projet.getVideo());
		mediaplayer = new MediaPlayer(media);
		mediaControl = new MediaControl(mediaplayer, this);
		
		mediaControl.setOnMouseClicked(a -> playerStatus(a));
		mediaControl.setPadding(new Insets(0, 20,0, 20));
		
		pane.getChildren().add(mediaControl);
		
//		degrade_fx = new Rectangle(20, 452, 960, 150);
//		LinearGradient lg = new LinearGradient(0.5,
//				                               0.0,
//				                               0.5,
//				                               1.0,
//				                               true, 
//				                               CycleMethod.NO_CYCLE,
//				                               new Stop[] { 
//				                                   new Stop(0.0, 
//				                            		        Color.web("#0d000000")),
//				                                   new Stop(1.0,
//				                            		        Color.web("#0d000088"))
//		                                       });
//		
//		degrade_fx.setFill(lg);
//		
//		
//		pane.getChildren().add(degrade_fx);	
		
		bloc_fx = new Rectangle(20, 450, 960, 79);
		bloc_fx.setFill(Color.color(1.0d, 1.0d, 1.0d, 0.7d));
		pane.getChildren().add(bloc_fx);
				
		text_sous_titre = new Text("Sous titre ...");
		text_sous_titre.setFont(Font.font("Lucida", 25.0));
		//text_sous_titre.setFill(Color.WHITE);
		text_sous_titre.setFill(Color.BLACK);
		text_sous_titre.setLayoutX(100);
		text_sous_titre.setLayoutY(515);
		text_sous_titre.setVisible(true);
		text_sous_titre.toFront();
		
		text_sous_titre.textProperty().bind(phrase_affichee);
			
		pane.getChildren().add(text_sous_titre);

		map_des_mots = new TreeMap<>();		
		map_des_lignes = new TreeMap<>();	
		map_des_lignes_simples = new TreeMap<>();	
	
		mots_observables = FXCollections.observableArrayList();
		mots = new ArrayList<>();
		
		try {
			$(new File(projet.getXml())).find("Word")
			.each(ctx -> {
				
				if (debut_texte){
					debut_texte = false;
				}				
				else if ($(ctx).text().startsWith(" ")){
					Espace espace = new Espace(this);
					
					espace.setOnMouseClicked(a -> onEspaceSelect(a));
					espace.setOnMouseEntered(a -> onEspaceEnter(a));
					espace.setOnMouseExited(a -> onEspaceExit(a));
					
					
			    	
					mots.add(espace);
				}
				
				Mot m = new Mot($(ctx).text().trim(),
						        Double.parseDouble($(ctx).attr("stime")),
						        Double.parseDouble($(ctx).attr("dur")),
						        $(ctx).text().startsWith(" ") ? true : false);
				
				m.setStyle("-fx-font-size:20;");
				
//				m.setOnMouseClicked(a -> onMotSelect(a));
//				m.setOnMouseEntered(a -> onMotEnter(a));
//				m.setOnMouseExited(a -> onMotExit(a));
				
				mots.add(m);
				map_des_mots.put($(ctx).attr("stime").replace('.', ','), m);
				
				for (double i = 0.01d; i < Double.parseDouble($(ctx).attr("dur")); i+=0.01){
					
					map_des_mots.put(String.format("%.02f", Double.parseDouble($(ctx).attr("stime")) + i ).replace('.', ','), m);
				}
				
				

				});
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		mots_observables.addAll(mots);
		
		flowpane.getChildren().addAll(mots_observables);
		
		observablePresets = FXCollections.observableArrayList();
		preset_choiceBox.setItems(observablePresets);
		
		
		
		Placement p1 = new Placement();
		Rectangle bloc_fx1 = new Rectangle(20, 450, 960, 79);
		p1.setRect(bloc_fx1);
		observablePresets.add(new Preset(p1, "Centre sur bandeau"));
		
		Placement p2 = new Placement();
		Rectangle bloc_fx2 = new Rectangle(20, 450, 960, 46);
		p2.setRect(bloc_fx2);
		observablePresets.add(new Preset(p2, "Centre sur synthé"));
		
		preset_choiceBox.getSelectionModel().select(0);
		
		choiceBoxListener = new ChangeListener<Preset>() {

			@Override
			public void changed(ObservableValue<? extends Preset> observable, Preset oldValue, Preset newValue) {
				
				ligneLue.getLigne_simple().setSimplePlacement(newValue.getPlacement());
				for (double i = 0.01d; i < ligneLue.getDuree(); i+=0.01){
					
					map_des_lignes.replace(String.format("%.02f", ligneLue.getDebut() + i ).replace('.', ','), ligneLue);
				}
				
				
			}
        };
		
		preset_choiceBox.getSelectionModel().selectedItemProperty().addListener(choiceBoxListener);
		
		//mock
		ArrayList<Ligne_simple> lignes_simples_mock = new ArrayList<>();
		Mot_simple mot_simple_mock = new Mot_simple("uuuuu", 3.2, 1.5, false);
		ArrayList<Mot_simple> mots_simples_mock = new ArrayList<>();
		mots_simples_mock.add(mot_simple_mock);
		Ligne_simple ligne_mock = new Ligne_simple(mots_simples_mock);
		Placement placement_mock = new Placement();
		placement_mock.setHaut(254);
		placement_mock.setLateral(444);
		placement_mock.setRect(new Rectangle(20, 450, 960, 79));
		placement_mock.setCouleur_rectangle(new double [] {1.0d, 1.0d, 1.0d, 0.7d});
		ligne_mock.setSimplePlacement(placement_mock);
		lignes_simples_mock.add(ligne_mock);
		
		Map<String, Ligne_simple> map_des_lignes_simples_mock = new TreeMap<>();
		map_des_lignes_simples_mock.put("ttt", new Ligne_simple(mots_simples_mock));
		
		
		serialize_button.setOnAction(a-> {
			                              projet.setPremiere_ligne(lignes_simples.get(0));
                                          SerialisationJson.serialise(projet);
		});
		
		

	}

	public Map<String, Mot> getMap_des_mots() {
		return map_des_mots;
	}

	public void setMap_des_mots(Map<String, Mot> map_des_mots) {
		this.map_des_mots = map_des_mots;
	}
	
	public Map<String, Ligne> getMap_des_lignes() {
		return map_des_lignes;
	}

	public void setMap_des_lignes(Map<String, Ligne> map_des_lignes) {
		this.map_des_lignes = map_des_lignes;
	}
}
