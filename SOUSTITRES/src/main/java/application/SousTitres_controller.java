package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Espace;
import models.Mot;
import utils.MediaControl;


public class SousTitres_controller implements Initializable {
	
	
	@FXML
	private FlowPane flowpane;
	@FXML
	private Pane pane;

	@FXML
	private Rectangle degrade;
	
	private Media media;
	private MediaPlayer mediaplayer;
	private MediaControl mediaControl;
	
	private DoubleProperty point_d_entree;
	private double bourage;
	
	private Espace labelCourant;
	
	private final int LONGUEUR_LIGNE_MAX = 60;
	
	private ObservableList<Label> mots_observables ;
	private ArrayList<Label> mots ;
	
	private int index_label;
	
	private Map<String, Mot> map_des_mots;
	
	private Mot mot_lu;
	
	private Text text_sous_titre;
	
	private ObjectProperty<String> phrase_affichee; 
	
	public void onMotSelect(MouseEvent me){
		
		System.out.println(me.getSource());
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
    	
    	phrase_affichee.set(lu.getText());
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
    	
    	Label padding = new Label("");
    	padding.setPadding(new Insets(0, bourage - 20, 0, 0));
    	padding.setStyle("-fx-font-size:25; -fx-text-fill: #777777");
    	padding.setVisible(true);
    	
//    	System.out.println(bourage);
    	
    	index_label = mots.indexOf(labelCourant);
    	
    	mots.add(index_label + 1, padding);

    	mots_observables.clear();
    	mots_observables.addAll(mots);
    	
    	flowpane.getChildren().clear();
    	flowpane.getChildren().addAll(mots_observables);
    	
    	labelCourant = (Espace) mots.get(index_label);
    	
    	labelCourant.setOnMouseClicked(a -> defaire());

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
	
	public void initialize(URL location, ResourceBundle resources) {
		
		phrase_affichee = new SimpleObjectProperty<>();
		
		point_d_entree = new SimpleDoubleProperty();
		
		media = new Media("file:///home/autor/Main_18H39_vGood.mp4");
		mediaplayer = new MediaPlayer(media);
		mediaControl = new MediaControl(mediaplayer, this);
		
		mediaControl.setOnMouseClicked(a -> playerStatus(a));
		mediaControl.setPadding(new Insets(150, 20, 80, 20));
		
		pane.getChildren().add(mediaControl);
		degrade.toFront();
		
		text_sous_titre = new Text("Sous titre ...");
		text_sous_titre.setFont(Font.font("Lucida", 30.0));
		text_sous_titre.setFill(Color.WHITE);
		text_sous_titre.setLayoutX(100);
		text_sous_titre.setLayoutY(630);
		text_sous_titre.setVisible(true);
		text_sous_titre.toFront();
		text_sous_titre.textProperty().bind(phrase_affichee);
			
		pane.getChildren().add(text_sous_titre);

		map_des_mots = new TreeMap<>();		
	
		mots_observables = FXCollections.observableArrayList();
		mots = new ArrayList<>();
		
		try {
			$(new File("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/18h39_sous_titres.xml")).find("Word")
			.each(ctx -> {
				
				if ($(ctx).text().startsWith(" ")){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mots_observables.addAll(mots);
		
		flowpane.getChildren().addAll(mots_observables);
	}

	public Map<String, Mot> getMap_des_mots() {
		return map_des_mots;
	}

	public void setMap_des_mots(Map<String, Mot> map_des_mots) {
		this.map_des_mots = map_des_mots;
	}
	
	

}
