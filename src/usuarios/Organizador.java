package usuarios;

import java.util.ArrayList;
import java.util.Date;

import evento.Evento;
import evento.Venue;

public class Organizador extends Usuario{
	
	private ArrayList<Evento> eventosCreados = new ArrayList<Evento>();

    public Organizador(String login, String password, double saldo) {
        super(login, password, saldo);
    }

    public ArrayList<Evento> getEventosCreados() {
        return new ArrayList<Evento>(eventosCreados);
    }

    public ArrayList<Evento> getEventosActivos() {
        ArrayList<Evento> activos = new ArrayList<Evento>();
        for (int i = 0; i < eventosCreados.size(); i++) {
            Evento e = eventosCreados.get(i);
            if (e != null && !e.getCancelado()) {
                activos.add(e);
            }
        }
        return activos;
    }

    public void agregarEvento(Evento evento) {
        if (evento == null) return;
        if (!eventosCreados.contains(evento)) {
            eventosCreados.add(evento);
        }
    }

    public boolean cancelarEvento(Evento evento) {
        if (evento == null) { 
            System.err.println("Error: evento nulo.");
            return false;
        }

        if (evento.getCancelado()) {
            return true; 
        }

        evento.setCancelado(true);
        return true;
    }

    public Evento crearEvento(String nombre, Date fecha, int cantBasicos, int cantMultiples, int cantDeluxe, double cargoPorcentual, double cuotaAdicional,int maxBasicos, int maxDeluxe, int maxMultiples, Venue venue) {
        
        if (venue == null) {
            System.err.println("Error: venue nulo.");
            return null;
        }
        if (nombre == null) {
            System.err.println("Error: el nombre no puede ser vacío.");
            return null;
        }
        if (fecha == null) {
            System.err.println("Error: la fecha no puede ser nula.");
            return null;
        }
        if (cantBasicos < 0 || cantMultiples < 0 || cantDeluxe < 0) {
            System.err.println("Error: las cantidades iniciales no pueden ser negativas.");
            return null;
        }
        if (maxBasicos < 0 || maxMultiples < 0 || maxDeluxe < 0) {
            System.err.println("Error: los máximos no pueden ser negativos.");
            return null;
        }
        if (cantBasicos > maxBasicos || cantMultiples > maxMultiples || cantDeluxe > maxDeluxe) {
            System.err.println("Error: las cantidades iniciales superan los máximos permitidos.");
            return null;
        }
        if (cargoPorcentual < 0 || cuotaAdicional < 0) {
            System.err.println("Error: cargos no pueden ser negativos.");
            return null;
        }


        if (!venue.isAprobado()) {
            System.err.println("Error: el venue debe estar aprobado.");
            return null;
        }
        if (!venue.validarDisponibilidad(fecha)) {
            System.err.println("Error: el venue no está disponible en esa fecha.");
            return null;
        }
        
        if (fecha.before(new Date())) {
            System.err.println("Error: no se puede crear un evento en el pasado.");
            return null;
        }

        Evento nuevoEvento = new Evento(
                nombre,
                fecha,
                cantBasicos,
                cantMultiples,
                cantDeluxe,
                cargoPorcentual,
                cuotaAdicional,
                maxBasicos,
                maxDeluxe,
                maxMultiples,
                this,      
                venue      
        );
        
        venue.setProximoEvento(nuevoEvento);
        agregarEvento(nuevoEvento);

        return nuevoEvento;
    }

}
