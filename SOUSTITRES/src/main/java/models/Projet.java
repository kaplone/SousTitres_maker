package models;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import serializables.Ligne_simple;


public class Projet {
	
	private String nom;
	private Ligne_simple premiere_ligne;
	private String video;
	private String xml;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Ligne_simple getPremiere_ligne() {
		return premiere_ligne;
	}
	public void setPremiere_ligne(Ligne_simple premiere_ligne) {
		this.premiere_ligne = premiere_ligne;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	
	
	

}
