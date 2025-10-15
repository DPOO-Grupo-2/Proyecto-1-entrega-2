package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import evento.Evento;
import evento.Localidad;
import evento.Oferta;
import evento.Venue;

public class Organizador extends Usuario{
	
	private ArrayList<Evento> eventosCreados = new ArrayList<Evento>();
    private ArrayList<Evento> solicitudesPendientes = new ArrayList<>();

    public Organizador(String login, String password, double saldo) {
        super(login, password, saldo);
    }

    public ArrayList<Evento> getEventosCreados() {
        return new ArrayList<>(eventosCreados);
    }
    
    public List<Evento> getEventosActivos() {
        ArrayList<Evento> activos = new ArrayList<>();
        for (int i = 0; i < eventosCreados.size(); i++) {
            Evento e = eventosCreados.get(i);
            if (e != null && !e.getCancelado()) {
                activos.add(e);
            }
        }
        return activos;
    }
    
    public void setEventosActivos(List<Evento> eventos) {
        if (eventos == null) {
            System.err.println("Error: la lista no puede ser nula.");
            return;
        }
        this.eventosCreados.clear();
        this.eventosCreados.addAll(eventos);
    }

    public ArrayList<Evento> getSolicitudesPendientes() {
        return new ArrayList<>(solicitudesPendientes);
    }

    public void agregarEvento(Evento evento) {
        if (evento == null) {
            return;
        }
        if (!eventosCreados.contains(evento)) {
            eventosCreados.add(evento);
        }
    }

    public void removerEvento(Evento evento) {
        if (evento == null) {
            return;
        }
        eventosCreados.remove(evento);
    }

    public boolean solicitarCancelacion(Evento evento, String motivo) {
        if (evento == null) {
            return false;
        }
        if (motivo == null || motivo.trim().equals("")) {
            return false;
        }
        if (evento.getOrganizador() == null || !evento.getOrganizador().equals(this)) {
            return false;
        }
        if (evento.getCancelado()) {
            return false;
        }
        if (solicitudesPendientes.contains(evento)) {
            return false;
        }

        solicitudesPendientes.add(evento);
        return true;
    }
    
    public void asignarLocalidad(Evento evento, Localidad localidad) {
        if (evento == null || localidad == null) {
            System.err.println("Error: evento/localidad nulos.");
            return;
        }
        if (!this.eventosCreados.contains(evento)) {
            System.err.println("Error: no eres dueño de este evento.");
            return;
        }
        if (!evento.getLocalidades().contains(localidad)) {
            evento.getLocalidades().add(localidad);
        }
    }

    public void configurarLocalidades(Evento evento, Localidad[] localidades) {
        if (evento == null || localidades == null) {
            System.err.println("Error: evento/localidades nulos.");
            return;
        }
        if (!this.eventosCreados.contains(evento)) {
            System.err.println("Error: no eres dueño de este evento.");
            return;
        }
        for (int i = 0; i < localidades.length; i++) {
            Localidad loc = localidades[i];
            if (loc != null && !evento.getLocalidades().contains(loc)) {
                evento.getLocalidades().add(loc);
            }
        }
    }

    public Evento crearEvento(String nombre, Date fecha, int cantBasicos, int cantMultiples, int cantDeluxe, double cargoPorcentual, double cuotaAdicional, int maxBasicos, int maxDeluxe, int maxMultiples, Venue venue) {
        
        if (venue == null) {
            System.err.println("Error: venue nulo.");
            return null;
        }
        
        if (nombre == null || nombre.trim().equals("")) {
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

    public Oferta crearOferta(Evento evento, Localidad localidad, double descuento, Date fechaInicio, Date fechaFin) {
    	if (evento == null || localidad == null) {
            System.err.println("Error: evento o localidad nulos.");
            return null;
        }

        if (!eventosCreados.contains(evento)) {
            System.err.println("Error: no eres dueño de este evento.");
            return null;
        }

        if (!evento.getLocalidades().contains(localidad)) {
            System.err.println("Error: la localidad no pertenece a este evento.");
            return null;
        }

        if (descuento <= 0 || descuento > 100) {
            System.err.println("Error: el descuento debe estar entre 0 y 100.");
            return null;
        }

        if (fechaInicio == null || fechaFin == null) {
            System.err.println("Error: las fechas no pueden ser nulas.");
            return null;
        }

        if (fechaInicio.after(fechaFin)) {
            System.err.println("Error: la fecha de inicio debe ser anterior a la fecha fin.");
            return null;
        }

        if (fechaFin.after(evento.getFecha())) {
            System.err.println("Error: la oferta no puede extenderse más allá de la fecha del evento.");
            return null;
        }

        Oferta nuevaOferta = new Oferta(evento, localidad, descuento, fechaInicio, fechaFin);

        return nuevaOferta;
    }
    
}
    