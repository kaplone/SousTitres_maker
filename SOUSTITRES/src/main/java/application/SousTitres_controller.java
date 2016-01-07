package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.StopBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Allignement;
import models.ButtonGrid;
import models.Espace;
import models.Ligne;
import models.Mot;
import utils.ExportASS;
import utils.ExportSRT;
import utils.MediaControl;

public class SousTitres_controller implements Initializable {
		
	@FXML
	private FlowPane flowpane;
	@FXML
	private Pane pane;
	
	private Rectangle degrade_fx;
	
	@FXML
	private Button export_button;
	
	@FXML
	private VBox index_vbox;
	
	@FXML
	private ChoiceBox<Allignement> allignement_choiceBox;
	
	@FXML
	private GridPane lignes_grid;
	
	@FXML
	private TextField textProperty_textField;
	
	private StringProperty observableString;
	private StringProperty affichableString;
	
	private int derniere_ligne;
	
	
	private Media media;
	private MediaPlayer mediaplayer;
	private MediaControl mediaControl;
	
	private DoubleProperty point_d_entree;
	private double bourage;
	
	private Espace labelCourant;
	
	private ObservableList<Label> mots_observables ;
	private ArrayList<Mot> mots ;
	private ArrayList<Ligne> lignes ;
	
	private int index_label;
	
	private Map<String, Mot> map_des_mots;
	private Map<String, Ligne> map_des_lignes;
	
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
	
	public void onExport_button(){
		
		ExportSRT.export_srt_file(lignes);
		ExportASS.export_ass_file(lignes);
		
	}
	
	public void onMotSelect(MouseEvent me){
		
		System.out.println(me.getSource());
		System.out.println(((Mot)me.getSource()).getLayoutX());
	}
	
    public void onMotEnter(MouseEvent me){
		
		((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #dddddd ;");
	}
    
    public void onMotExit(MouseEvent me){

    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
	}
    
    public void mot_lecture(Mot lu){
    	
    	lu.setStyle("-fx-font-size:25; -fx-background-color: #aa88dd ;");
    	
    	if (mot_lu != null) {
    		mot_lu.setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
    	}
    	mot_lu = lu;
    }
    
    public void ligne_lecture(Ligne lu){
    	
    	phrase_affichee.unbind();
    	phrase_affichee.bind(lu.getContenu_edite());
    }
    
    public void editer(Event e){

    	((ButtonGrid) e.getSource()).getLigne().getContenu_edite().unbind();

    	textProperty_textField.setText(((ButtonGrid) e.getSource()).getLigne().getContenu_edite().get());
    	
    	((ButtonGrid) e.getSource()).getLigne().getContenu_edite().bind(textProperty_textField.textProperty());
    	
    }
    
    public void ajout_ligne_dans_grille(Ligne ligne){
    	
    	Button a = new Button();

		imv = new ImageView(IMG_LIGNE);
		
		imv.setPreserveRatio(true);
		imv.setFitHeight(16);
		a.setGraphic(imv);
		a.setOnAction((e) -> sautLigne(e));
		
		Button b = new ButtonGrid(String.format("%03d >", ++derniere_ligne), ligne);
		b.setOnAction((e) -> editer(e));
		
		TextField c = new TextField();
		c.setText(ligne.toString());
		
		Button d = new ButtonGrid(derniere_ligne, a, b, c, lignes_grid, ligne);
        imv = new ImageView(IMG_DELETE);
		
		imv.setPreserveRatio(true);
		imv.setFitHeight(16);
		d.setGraphic(imv);
		d.setOnAction((e) -> ligneDelete( (ButtonGrid) e.getSource()));
		
		lignes_grid.add(a, 0, derniere_ligne);
		lignes_grid.add(b, 1, derniere_ligne);
		lignes_grid.add(c, 2, derniere_ligne);
		lignes_grid.add(d, 3, derniere_ligne);

    }
    
    public void ligneDelete(ButtonGrid bg){
    	
    	bg.removeLine();
    	
    }
    
    public void defaire(){
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
    	labelCourant.setStyle("-fx-font-size:25; -fx-text-fill: #aaaaee");
    	
    	Mot padding = new Espace(this);
    	padding.setPadding(new Insets(0, bourage - 20, 0, 0));
    	padding.setStyle("-fx-font-size:25; -fx-text-fill: #777777");
    	padding.setVisible(true);
    	
    	index_label = mots.indexOf(labelCourant);
    	
    	mots.add(index_label + 1, padding);

    	mots_observables.clear();
    	mots_observables.addAll(mots);
    	
    	flowpane.getChildren().clear();
    	flowpane.getChildren().addAll(mots_observables);
    	
    	labelCourant = (Espace) mots.get(index_label);
    	
    	labelCourant.setOnMouseClicked(a -> defaire());
    	
    	Label l = labelCourant;
    	int index_l = mots.indexOf(l);
    	
    	index_l -= 1 ;
    	l = mots.get(index_l);

    	
    	while (l.getLayoutX() > 0){
    		
    		index_l -= 1 ;
    		l = mots.get(index_l);
    	}
    	
    	List<Mot> mots_ligne =  new ArrayList<>();
    	for (int i = index_l; i < index_label; i++){
    		mots_ligne.add(mots.get(i));
    	}
    	
    	
    	Ligne ligne = new Ligne(mots_ligne);
	
    	System.out.println(ligne.toString());
    	System.out.println(ligne.getPremier_mot().getText() + " --> " + ligne.getDernier_mot().getText());
    	System.out.println("début : " + ligne.getDebut());
    	System.out.println(String.format("durée : %.02f", ligne.getDuree()));
    	
    	map_des_lignes.put(String.format("%.02f", ligne.getDebut()), ligne);
    	lignes.add(ligne);
		
		for (double i = 0.01d; i < ligne.getDuree(); i+=0.01){
			
			map_des_lignes.put(String.format("%.02f", ligne.getDebut() + i ).replace('.', ','), ligne);
		}
		
		ajout_ligne_dans_grille(ligne);
    	

    }
    public void onEspaceEnter(MouseEvent me){
    	
    	labelCourant = ((Espace)me.getSource());
    	
    	if (! labelCourant.isPermanent()){
    	
	    	point_d_entree.set(me.getSceneX());
	    	bourage = 896 - (me.getSceneX() - 1000) - 20;	    	
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
		
		System.out.println(IMG_FICHIER);
		System.out.println(IMG_LIGNE);
		
		System.out.println(((ImageView)((Button) e.getSource()).getGraphic()).getImage());
		
		if ( ((ImageView)((Button) e.getSource()).getGraphic()).getImage().equals(IMG_FICHIER)){
			
			ImageView im = new ImageView(IMG_LIGNE);
			im.setPreserveRatio(true);
			im.setFitHeight(16);
			
			((Button) e.getSource()).setGraphic(im);
			
			((HBox) ((Button) e.getSource()).getParent()).getChildren().get(1).setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058);");
		}
		else {
			
			ImageView im = new ImageView(IMG_FICHIER);
			im.setPreserveRatio(true);
			im.setFitHeight(16);
			
			((Button) e.getSource()).setGraphic(im);
			
			((HBox) ((Button) e.getSource()).getParent()).getChildren().get(1).setStyle("-fx-background-color: linear-gradient(#999999, #bbbbbb);");
			
			System.out.println(((Button) ((HBox) ((Button) e.getSource()).getParent()).getChildren().get(1)).getText().split(" ")[0]);
			
		}
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		lignes = new ArrayList<>();
		
		derniere_ligne = 0;
		
		observableString = new SimpleStringProperty();
		affichableString = new SimpleStringProperty();
		
		phrase_affichee = new SimpleObjectProperty<>();
		
		point_d_entree = new SimpleDoubleProperty();
		
		media = new Media("file:///home/autor/Main_18H39_vGood.mp4");
		mediaplayer = new MediaPlayer(media);
		mediaControl = new MediaControl(mediaplayer, this);
		
		mediaControl.setOnMouseClicked(a -> playerStatus(a));
		mediaControl.setPadding(new Insets(0, 20,0, 20));
		
		pane.getChildren().add(mediaControl);
		
		degrade_fx = new Rectangle(20, 452, 960, 150);
		LinearGradient lg = new LinearGradient(0.5,
				                               0.0,
				                               0.5,
				                               1.0,
				                               true, 
				                               CycleMethod.NO_CYCLE,
				                               new Stop[] { 
				                                   new Stop(0.0, 
				                            		        Color.web("#0d000000")),
				                                   new Stop(1.0,
				                            		        Color.web("#0d000088"))
		                                       });
		
		degrade_fx.setFill(lg);
		
		
		pane.getChildren().add(degrade_fx);	
				
		text_sous_titre = new Text("Sous titre ...");
		text_sous_titre.setFont(Font.font("Lucida", 30.0));
		text_sous_titre.setFill(Color.WHITE);
		text_sous_titre.setLayoutX(100);
		text_sous_titre.setLayoutY(530);
		text_sous_titre.setVisible(true);
		text_sous_titre.toFront();
		text_sous_titre.textProperty().bind(phrase_affichee);
			
		pane.getChildren().add(text_sous_titre);

		map_des_mots = new TreeMap<>();		
		map_des_lignes = new TreeMap<>();	
	
		mots_observables = FXCollections.observableArrayList();
		mots = new ArrayList<>();
		
		try {
			$(new File("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/18h39_sous_titres.xml")).find("Word")
			.each(ctx -> {
				
				if (debut_texte){
					debut_texte = false;
				}				
				else if ($(ctx).text().startsWith(" ")){
					Espace espace = new Espace(this);
					mots.add(espace);
				}
				
				Mot m = new Mot($(ctx).text().trim(),
						        Double.parseDouble($(ctx).attr("stime")),
						        Double.parseDouble($(ctx).attr("dur")),
						        $(ctx).text().startsWith(" ") ? true : false);
				
				m.setStyle("-fx-font-size:25;");
				
				m.setOnMouseClicked(a -> onMotSelect(a));
				m.setOnMouseEntered(a -> onMotEnter(a));
				m.setOnMouseExited(a -> onMotExit(a));
				
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
		
		textProperty_textField.setEditable(true);
		
//		index_vbox.setSpacing(4);
//		index_vbox.setPadding(new Insets(0, 0, 0, 10));
		
		
//		for (int k=1; k < 40; k++){
//			
//			Button a = new Button();
//
//			imv = new ImageView(IMG_LIGNE);
//			
//			imv.setPreserveRatio(true);
//			imv.setFitHeight(16);
//			a.setGraphic(imv);
//			
//			a.setOnAction((e) -> sautLigne(e));
//			
//			Button b = new Button(String.format("%03d >", k));
//			
//			HBox hb = new HBox();
//			hb.getChildren().add(a);
//			hb.getChildren().add(b);
//			
//			index_vbox.getChildren().add(hb);
//		}
		
//		allignement_choiceBox.setItems(FXCollections.observableArrayList(Allignement.values()));
	}

	public Map<String, Mot> getMap_des_mots() {
		return map_des_mots;
	}

	public void setMap_des_mots(Map<String, Mot> map_des_lignes) {
		this.map_des_mots = map_des_mots;
	}
	
	public Map<String, Ligne> getMap_des_lignes() {
		return map_des_lignes;
	}

	public void setMap_des_lignes(Map<String, Ligne> map_des_lignes) {
		this.map_des_lignes = map_des_lignes;
	}
}
