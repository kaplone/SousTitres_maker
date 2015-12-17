package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import models.Ligne;

public class ExportSRT {
	
	static String home =  System.getProperty("user.home");
	static File settings_file = new File(home, "Desktop/test.srt");
	static FileWriter fw = null;
	
	
	
	public static void export_srt_file(ArrayList<Ligne> lignes){
		
		int compteur = 1;
		
		try {
		    fw = new FileWriter(settings_file);
		    
		    for (Ligne l : lignes){

		    	
		    	String contenu = l.getContenu()
		    			          .stream()
		    			          .map(a -> a.getText())
		    			          .collect(Collectors.joining());
		    	
		    	String debut = l.getDebut() + "";
		    	
		    	String fin = (l.getDebut() + l.getDuree()) + "";
		    	
		    	String temporalite = String.format("00:%s --> 00:%s", debut, fin);

	    		fw.write(compteur + "");
	    		fw.write(System.getProperty("line.separator"));
	    		fw.write(temporalite);
	    		fw.write(System.getProperty("line.separator"));
	    		fw.write(contenu);
	    		fw.write(System.getProperty("line.separator"));
	    		fw.write(System.getProperty("line.separator"));
	    		
	    		compteur ++;
	    		
	    	}
		    
		 	fw.flush();
	    	fw.close();
	    	
	    	

	    	
		}catch (IOException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	    }
		
	}

}
