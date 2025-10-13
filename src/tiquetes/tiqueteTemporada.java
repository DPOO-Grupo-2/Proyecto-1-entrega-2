package tiquetes;

import java.util.ArrayList;
import evento.evento;
import evento.localidad;
public class tiqueteTemporada extends tiquete {
	
	private ArrayList<evento> eventosUsados= new ArrayList<evento>();
	public double precio;
		
//No se ha añadido metodo para añadir cada evento
	
public tiqueteTemporada(String id, boolean transferible, String silla, localidad localidad, evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precio = precio;
		
}


public ArrayList<evento> getEventos() {
	return eventos;
}


public void setEventos(ArrayList<evento> eventos) {
	this.eventos = eventos;
}


public ArrayList<evento> getUsados() {
	return eventosUsados;
}


public void setUsados(ArrayList<evento> usados) {
	this.eventosUsados = usados;
}


public double getPrecio() {
	return precio;
}


public void setPrecio(double precio) {
	this.precio = precio;
}
public int size() {
	return this.eventos.size();
}

public void usarTiqueteTemporada(tiqueteTemporada tiquete, String nombreEvento) {
	boolean encontrado = false;
	int i = 0;
	int pos = 0;
	while ( !encontrado && i < tiquete.getEventos().size()) {
		
		String evento1 = tiquete.getEventos().get(i).getNombreEvento();
		
		if (evento1.equals(nombreEvento)) {
			encontrado = true;
			pos = i;
		}
		i++;
	}
	
	if(encontrado) {
		evento event = tiquete.getEventos().remove(pos);
		tiquete.getUsados().add(event);
		if (tiquete.getEventos().isEmpty()) {
			tiquete.setUsado(true);
		}
		
	}		
		
	
		
	}
	
}



