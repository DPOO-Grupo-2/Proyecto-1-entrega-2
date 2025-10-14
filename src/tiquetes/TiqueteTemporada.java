package tiquetes;

import java.util.ArrayList;
import evento.Evento;
import evento.Localidad;
public class TiqueteTemporada extends Tiquete {
	
	private ArrayList<Evento> eventosUsados= new ArrayList<Evento>();
	public double precio;
		
//No se ha añadido metodo para añadir cada evento
	
public TiqueteTemporada(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precio = precio;
		
}


public ArrayList<Evento> getEventos() {
	return eventos;
}


public void setEventos(ArrayList<Evento> eventos) {
	this.eventos = eventos;
}


public ArrayList<Evento> getUsados() {
	return eventosUsados;
}


public void setUsados(ArrayList<Evento> usados) {
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

public void usarTiqueteTemporada(TiqueteTemporada tiquete, String nombreEvento) {
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
		Evento event = tiquete.getEventos().remove(pos);
		tiquete.getUsados().add(event);
		if (tiquete.getEventos().isEmpty()) {
			tiquete.setUsado(true);
		}
		
	}		
		
	
		
	}
	
}



