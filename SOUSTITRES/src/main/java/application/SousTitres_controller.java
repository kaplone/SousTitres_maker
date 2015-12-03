package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import models.Mot;


public class SousTitres_controller implements Initializable {
	
	
	@FXML
	private FlowPane flowpane;
	
	@FXML
	private MediaView mediaview;
	
	@FXML
	private ImageView imageview;
	
	private final int LONGUEUR_LIGNE_MAX = 60;
	
	
	
	public void onMotSelect(MouseEvent me){
		
		System.out.println(me.getSource());
	}
	
    public void onMotEnter(MouseEvent me){
		
		((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #008800 ;");
	}
    
    public void onMotExit(MouseEvent me){

    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
	}
    
    public void onEspaceSelect(MouseEvent me){
    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #cccccc;");
    }
    public void onEspaceEnter(MouseEvent me){
    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #000000;");
    }
    public void onEspaceExit(MouseEvent me){
    	((Label)me.getSource()).setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
    }
	
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		mediaview.setMediaPlayer(new MediaPlayer(new Media("file:///home/autor/Main_18H39_vGood.mp4")));
		imageview.setImage(new Image("file:///home/autor/degrade.svg"));
		imageview.toFront();
	
		ObservableList<Label> mots = FXCollections.observableArrayList();
		
		try {
			$(new File("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/bcm.xml")).find("Word")
			.each(ctx -> {
				
				Mot m = new Mot($(ctx).text().trim(),
						        Double.parseDouble($(ctx).attr("stime")),
						        Double.parseDouble($(ctx).attr("dur")),
						        $(ctx).text().startsWith(" ") ? true : false);
				
				m.setStyle("-fx-font-size:25;");
				
				m.setOnMouseClicked(a -> onMotSelect(a));
				m.setOnMouseEntered(a -> onMotEnter(a));
				m.setOnMouseExited(a -> onMotExit(a));
				
				mots.add(m);
				
				if (m.isEspace()){
					Label espace = new Label(" ");
					espace.setStyle("-fx-font-size:25; -fx-background-color: #eeeeee;");
					
					espace.setOnMouseClicked(a -> onEspaceSelect(a));
					espace.setOnMouseEntered(a -> onEspaceEnter(a));
					espace.setOnMouseExited(a -> onEspaceExit(a));
					
					mots.add(espace);
				}

				});
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(mots.size());
		
		flowpane.getChildren().addAll(mots);
	}

}
