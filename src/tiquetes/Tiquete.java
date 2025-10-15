package tiquetes;

import java.util.ArrayList;

import evento.Evento;
import evento.Localidad;
public abstract class Tiquete {

	private String id;
	private boolean transferible;
	public String silla;
	protected boolean usado;
	public Localidad localidad;
	public ArrayList<Evento> eventos;
	
	
public Tiquete(String id, boolean transferible, String silla, Localidad localidad, Evento evento) {
	this.id = id;
	this.localidad = localidad;
	this.silla = silla;
	this.transferible = transferible;
	this.usado = false;
	this.eventos  = new ArrayList<Evento>();
	eventos.addLast(evento);
}


public boolean isUsado() {
	return usado;
}


public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public void setTransferible(boolean transferible) {
	this.transferible = transferible;
}

public void setSilla(String silla) {
	this.silla = silla;
}

public void setLocalidad(Localidad localidad) {
	this.localidad = localidad;
}

public void setUsado(boolean usado) {
	this.usado = usado;
}


public boolean isTransferible() {
	return transferible;
}


public String getSilla() {
	return silla;
}


public Localidad getLocalidad() {
	return localidad;
}


public abstract double getPrecioTiquete();







}
