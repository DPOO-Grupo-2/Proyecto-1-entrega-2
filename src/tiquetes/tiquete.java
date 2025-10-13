package tiquetes;

import evento.evento;
import evento.localidad;
public abstract class tiquete {

	private String id;
	private boolean transferible;
	public String silla;
	private boolean usado;
	public localidad localidad;
	public evento evento;
	
	
public tiquete(String id, boolean transferible, String silla, localidad localidad, evento evento) {
	this.id = id;
	this.localidad = localidad;
	this.silla = silla;
	this.transferible = transferible;
	this.usado = false;
	this.evento = evento;
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

public void setLocalidad(localidad localidad) {
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


public localidad getLocalidad() {
	return localidad;
}

public void UsarTiquete(tiquete tiquete) {
	setUsado(true);
}






}
