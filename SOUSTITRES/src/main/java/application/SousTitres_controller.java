package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import models.Espace;
import models.Mot;


public class SousTitres_controller implements Initializable {
	
	
	@FXML
	private FlowPane flowpane;
	
	@FXML
	private MediaView mediaview;
	
	@FXML
	private ImageView imageview;
	
	private DoubleProperty point_d_entree;
	private double bourage;
	
	private Espace labelCourant;
	
	private final int LONGUEUR_LIGNE_MAX = 60;
	
	private ObservableList<Label> mots_observables ;
	private ArrayList<Label> mots ;
	
	
	
	public void onMotSelect(MouseEvent me){
		
		System.out.println(me.getSource());
	}
	
    public void onMotEnter(MouseEvent me){
		
		((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #dddddd ;");
	}
    
    public void onMotExit(MouseEvent me){

    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
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
    }
    
    public void onEspaceSelect(MouseEvent me){
    	
    	labelCourant.setPermanent(true);
    	labelCourant.setText("][");
    	labelCourant.setPadding(new Insets(0, 0, 0, 0));
    	labelCourant.setStyle("-fx-font-size:25; -fx-text-fill: #aaaaee");
    	
    	Label padding = new Label("....");
    	padding.setPadding(new Insets(0, bourage - 20, 0, 0));
    	padding.setStyle("-fx-font-size:25; -fx-text-fill: #777777");
    	padding.setVisible(true);
    	
    	System.out.println(bourage);
    	
    	mots.add(mots.indexOf(labelCourant), padding);
    	
    	System.out.println(mots.size());

    	mots_observables.clear();
    	mots_observables.addAll(mots);
    	
    	labelCourant.setOnMouseClicked(a -> defaire());

    }
    public void onEspaceEnter(MouseEvent me){
    	
    	labelCourant = ((Espace)me.getSource());
    	
    	if (! labelCourant.isPermanent()){
    	
	    	point_d_entree.set(me.getSceneX());
	    	bourage = 896 - (me.getSceneX() - 385) - 20;	    	
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
	
	public void initialize(URL location, ResourceBundle resources) {
		
		point_d_entree = new SimpleDoubleProperty();
		
//		flowpane.addListener(new ChangeListener<MouseEvent>() {
//
//			@Override
//			public void changed(ObservableValue<? extends MouseEvent> observable, MouseEvent oldValue,
//					MouseEvent newValue) {
//				
//				if (newValue.getSceneX() - point_d_entree.get() > 20){
//					
//				}
//				
//			}
//
//
//		});
		
		mediaview.setMediaPlayer(new MediaPlayer(new Media("file:///home/autor/Main_18H39_vGood.mp4")));
		imageview.setImage(new Image("file:///home/autor/degrade.svg"));
		imageview.toFront();
	
		mots_observables = FXCollections.observableArrayList();
		mots = new ArrayList<>();
		
		try {
			$(new File("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/bcm.xml")).find("Word")
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

				});
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mots_observables.addAll(mots);
		
		flowpane.getChildren().addAll(mots_observables);
	}

}
