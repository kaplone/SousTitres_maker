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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import models.Mot;


public class SousTitres_controller implements Initializable {
	
	
	@FXML
	private FlowPane flowpane;
	
	
	
	public void onMotSelect(MouseEvent me){
		
		System.out.println(me.getSource());
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Mot> mots = FXCollections.observableArrayList();
		
		try {
			$(new File("/mnt/nfs_public/pour David/TRANSCRIPTION/vocapia/bcm.xml")).find("Word")
			.each(ctx -> {
				
				Mot m = new Mot($(ctx).text(),
						        Double.parseDouble($(ctx).attr("stime")),
						        Double.parseDouble($(ctx).attr("dur")),
						        $(ctx).text().startsWith(" ") ? true : false);
				
				m.setOnMouseClicked(a -> onMotSelect(a));
				
				mots.add(m);
                				
//				System.out.println(
//				$(ctx).text() +
//				"   start time : " + 
//				$(ctx).attr("stime") +
//				"   durée : " + 
//				$(ctx).attr("dur") 
//				);
				
				
				
				});
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(mots.size());
		
		flowpane.getChildren().addAll(mots);
	}

}
