package tiquetes;

import java.util.ArrayList;

public class paqueteDeluxe {
private ArrayList<tiquete> tiquetesCortesia = new ArrayList<tiquete>();
private String beneficiosAdicionales;
private String mercanciaAdicional;
private tiquete tiquete;

public paqueteDeluxe(String beneficiosAdicionales, String mercanciaAdicional, tiquete tiquete) {
	this.beneficiosAdicionales = beneficiosAdicionales;
	this.mercanciaAdicional = mercanciaAdicional;
	this.tiquete = tiquete;
}



}
