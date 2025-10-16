package tiquetes;

import java.util.ArrayList;
import evento.Evento;
import evento.Localidad;
import usuarios.Usuario;

public class TiqueteTemporada extends Tiquete {
    
    private ArrayList<Evento> eventosUsados = new ArrayList<Evento>();
    public double precio;

    public TiqueteTemporada(String id, boolean transferible, String silla, Localidad localidad, 
                            Evento evento, double precio, Usuario usuario) {
        super(id, transferible, silla, localidad, evento, usuario);
        this.precio = precio;
    }

    public double getPrecioTiquete() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<Evento> getUsados() {
        return eventosUsados;
    }

    public void setUsados(ArrayList<Evento> usados) {
        this.eventosUsados = usados;
    }

    public int size() {
        return this.eventos.size();
    }

    public void usarTiqueteTemporada(String nombreEvento) {
        if (nombreEvento == null || nombreEvento.trim().equals("")) {
            System.err.println("Error: nombre de evento inválido.");
            return;
        }

        boolean encontrado = false;
        int i = 0;
        int pos = 0;
        
        while (!encontrado && i < this.eventos.size()) {
            String evento1 = this.eventos.get(i).getNombreEvento();
            
            if (evento1.equals(nombreEvento)) {
                encontrado = true;
                pos = i;
            }
            i++;
        }

        if (encontrado) {
            Evento event = this.eventos.remove(pos);
            this.eventosUsados.add(event);
            
            if (this.eventos.isEmpty()) {
                this.setUsado(true);
            }
            
            System.out.println("Tiquete usado para el evento: " + nombreEvento);
        } else {
            System.err.println("Error: evento no encontrado en el tiquete de temporada.");
        }
    }
    
    public void usarTiqueteTemporada(TiqueteTemporada tiquete, String nombreEvento) {
        tiquete.usarTiqueteTemporada(nombreEvento);
    }
}