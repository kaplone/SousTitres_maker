package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Projet;

public class SerialisationJson {
	
	public static String serialise(Projet p){
		
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
		//mapper.writeValue(new File("c:\\user.json"), l);

		//Object to JSON in String
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(p);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(jsonInString);
		
		return jsonInString;
		
	}

}
