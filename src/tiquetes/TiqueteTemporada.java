package tiquetes;

import java.util.ArrayList;
import evento.Evento;
import evento.Localidad;
import usuarios.Usuario;

public class TiqueteTemporada extends Tiquete {
    
    private ArrayList<Evento> eventosUsados = new ArrayList<Evento>();
    public double precio;

    public TiqueteTemporada(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio, Usuario usuario) {
    	super(id, transferible, silla, localidad, evento, usuario);
    	if (precio < 0) {
            System.err.println("Error: el precio no puede ser negativo.");
            this.precio = 0;
        } else {
            this.precio = precio;
        }
    }

    public double getPrecioTiquete() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            System.err.println("Error: el precio no puede ser negativo.");
            return;
        }
        this.precio = precio;
    }

    public ArrayList<Evento> getUsados() {
        return eventosUsados;
    }

    public void setUsados(ArrayList<Evento> usados) {
        if (usados == null) {
            System.err.println("Error: la lista de eventos usados no puede ser nula.");
            return;
        }
        this.eventosUsados = new ArrayList<>(usados);
    }

    public int size() {
        return this.eventos.size();
    }

    public void usarTiqueteTemporada(String nombreEvento) {
        if (nombreEvento == null || nombreEvento.trim().equals("")) {
            System.err.println("Error: nombre de evento inválido.");
            return;
        }

        Evento eventoEncontrado = null;
        int posicion = -1;
        boolean encontrado = false;
        int i = 0;
        
        while (!encontrado && i < this.eventos.size()) {
            Evento evento = this.eventos.get(i);
            if (evento != null && nombreEvento.equals(evento.getNombreEvento())) {
                eventoEncontrado = evento;
                posicion = i;
                encontrado = true;
            }
            i++;
        }

        if (eventoEncontrado != null) {
            Evento event = this.eventos.remove(posicion);
            this.eventosUsados.add(event);
            
            if (this.eventos.isEmpty()) {
                this.setUsado(true);
            }
            
            System.out.println("Tiquete usado para el evento: " + nombreEvento);
        } else {
            System.err.println("Error: evento no encontrado en el tiquete de temporada.");
        }
    }
    
}