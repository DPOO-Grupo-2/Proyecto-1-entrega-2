package tiquetes;

import java.util.ArrayList;
import evento.evento;
import evento.localidad;
public class tiqueteTemporada extends tiquete {
	private ArrayList<evento> eventos = new ArrayList<evento>();
public double precio;
		
	
public tiqueteTemporada(String id, boolean transferible, String silla, localidad localidad, evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precio = precio;
		
}


}
