package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import models.Ligne;

public class ExportSRT {
	
	static String home =  System.getProperty("user.home");
	static File settings_file = new File(home, "Desktop/test_2.srt");
	static FileWriter fw = null;
	
	
	
	public static void export_srt_file(LinkedList<Ligne> lignes){
		
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
		    	
		    	String debut_secondes;
		    	String debut_minutes;
		    	
		    	String fin_secondes;
		    	String fin_minutes;
		    	
		    	int debut_centiemes = Integer.parseInt(debut.split("\\.")[1].substring(0, debut.split("\\.").length -1));
		    	
		    	if (debut.split("\\.")[0].split(":").length > 1){		    	
			    	debut_secondes = debut.split("\\.")[0].split(":")[1];
			    	debut_minutes = debut.split("\\.")[0].split(":")[0];
		    	}
		    	else {
		    		debut_secondes = debut.split("\\.")[0].split(":")[0];
		    		debut_minutes = "00";
		    	}
		    	
		    	int fin_centiemes = Integer.parseInt(fin.split("\\.")[1].substring(0, fin.split("\\.").length -1));
		    	
		    	if (fin.split("\\.")[0].split(":").length > 1){	
		    	   fin_secondes = fin.split("\\.")[0].split(":")[1];
		    	   fin_minutes = fin.split("\\.")[0].split(":")[0];
		    	}
		    	else {
		    		fin_secondes = fin.split("\\.")[0].split(":")[0];
			    	fin_minutes = "00";
		    	}
		    		
		    	String temporalite = String.format("00:%s:%s,%03d --> 00:%s:%s,%03d", debut_minutes, debut_secondes, debut_centiemes, fin_minutes, fin_secondes, fin_centiemes);

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
