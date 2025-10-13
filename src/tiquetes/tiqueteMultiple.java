package tiquetes;
import evento.localidad;

import java.util.ArrayList;

import evento.evento;
public class tiqueteMultiple extends tiquete {
	public int numeroDeTiquetes;
	public ArrayList<tiquete> tiquetes= new ArrayList<tiquete>();
	public ArrayList<tiquete> tiquetesUsados= new ArrayList<tiquete>();
	public double precioTiqueteMultiple;
	
	
	
	
	public tiqueteMultiple(String id, boolean transferible, String silla, localidad localidad,
			evento evento, double precio, int numeroDeTiquetes) {
		super(id, transferible, silla, localidad, evento);
		this.numeroDeTiquetes = numeroDeTiquetes;
		this.precioTiqueteMultiple = precio;
		
		
	}
	
public void  añadirTiquete(tiqueteMultiple tiquete, tiqueteBasico tiquete2) {
	ArrayList<tiquete> array = this.tiquetes;
	array.add(tiquete2);
			
}

public int getNumeroDeTiquetes() {
	return numeroDeTiquetes;
}

public void setNumeroDeTiquetes(int numeroDeTiquetes) {
	this.numeroDeTiquetes = numeroDeTiquetes;
}

public ArrayList<tiquete> getTiquetes() {
	return tiquetes;
}

public void setTiquetes(ArrayList<tiquete> tiquetes) {
	this.tiquetes = tiquetes;
}

public ArrayList<tiquete> getTiquetesUsados() {
	return tiquetesUsados;
}

public void setTiquetesUsados(ArrayList<tiquete> tiquetesUsados) {
	this.tiquetesUsados = tiquetesUsados;
}

public double getPrecioTiqueteMultiple() {
	return precioTiqueteMultiple;
}

public void setPrecioTiqueteMultiple(double precioTiqueteMultiple) {
	this.precioTiqueteMultiple = precioTiqueteMultiple;
}

}
