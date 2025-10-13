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
	
public void añadirTiquete(tiquete nuevoTiquete) {
	    this.tiquetes.add(nuevoTiquete);
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


public ArrayList<tiquete> getTiquetesUsados() {
	return tiquetesUsados;
}


public double getPrecioTiqueteMultiple() {
	return precioTiqueteMultiple;
}

public void setPrecioTiqueteMultiple(double precioTiqueteMultiple) {
	this.precioTiqueteMultiple = precioTiqueteMultiple;
}

public void usarTiqueteMultiple() {
    if (!tiquetes.isEmpty()) {
        tiquete unidad = tiquetes.remove(tiquetes.size() - 1);
        unidad.setUsado(true);
        tiquetesUsados.add(unidad);


        if (tiquetes.isEmpty()) {
            setUsado(true);
        }
    }
}



}
