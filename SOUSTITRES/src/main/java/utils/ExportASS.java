package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import models.Ligne;

public class ExportASS {
	
	static String home =  System.getProperty("user.home");
	static File ass_file = new File(home, "Desktop/test_3.ass");
	static FileWriter fw = null;
	
	static File base_file = new File(home, "Desktop/base.ass");
	static FileReader fr = null;
	
	
	
	public static void export_ass_file(LinkedList<Ligne> lignes){

		try {
		    fw = new FileWriter(ass_file);
		    fr = new FileReader(base_file);
		    
		    BufferedReader br = new BufferedReader(fr); 
		    String s = ""; 
		    String s_;
		    while((s_ = br.readLine()) != null) { 
		       s += (s_ + System.getProperty("line.separator")) ; 
		    }

		    
		    fw.write(s);
		    
		    for (Ligne l : lignes){

		    	
//		    	String contenu = l.getContenu()
//		    			          .stream()
//		    			          .map(a -> a.getText())
//		    			          .collect(Collectors.joining());
		    	
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
		    		
		    	String temporalite = String.format("0:%s:%s.%02d,0:%s:%s.%02d", debut_minutes, debut_secondes, debut_centiemes, fin_minutes, fin_secondes, fin_centiemes);

	    		fw.write("Dialogue: 0,");
	    		fw.write(temporalite);
	    		fw.write(",Simple,,0,0,20,,");
	    		//fw.write(contenu);
	    		fw.write(System.getProperty("line.separator"));
	    	}
		    
		 	fw.flush();
	    	fw.close();
	    	
	    	

	    	
		}catch (IOException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	    }
		
	}

}


//* ffmpeg -i '/mnt/nfs_public/pour David/TRANSCRIPTION/Main_18H39_vGood.mp4' -i '/mnt/nfs_public/pour David/TRANSCRIPTION/degrade.png' -filter_complex "[0:v][1:v] overlay=0:442:enable='between(t,0,60)'" -vcodec mpeg2video -q:v 0 -f mpeg2video - | ffmpeg -y -i -  -vf ass='/home/autor/Desktop/test_2.ass' -vcodec mpeg2video -q:v 0 '/mnt/nfs_public/pour David/TRANSCRIPTION/Main_18H39_vGood_overlay_ass.mpg
