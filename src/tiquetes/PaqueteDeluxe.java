package tiquetes;

import java.util.ArrayList;

public class PaqueteDeluxe {
private ArrayList<Tiquete> tiquetesCortesia = new ArrayList<Tiquete>();
private String beneficiosAdicionales;
private String mercanciaAdicional;
private Tiquete tiquete;

public PaqueteDeluxe(String beneficiosAdicionales, String mercanciaAdicional, Tiquete tiquete) {
	this.beneficiosAdicionales = beneficiosAdicionales;
	this.mercanciaAdicional = mercanciaAdicional;
	this.tiquete = tiquete;
}



}
