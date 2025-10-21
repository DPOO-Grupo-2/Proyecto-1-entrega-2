package evento;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Venue {
	
	private String nombre;
    private String tipoVenue;
    private int capacidad;
    private String ubicacion;
    private String restriccionesDeUso;

    private String proximoEvento;       
    private Date fechaProximoEvento;    
    private boolean aprobado;
    
    private Map<Date, Evento> eventosReservados;
    
    public Venue(String nombre, String tipo, int capacidad, String ubicacion, String restricciones) {
        this.nombre = nombre;
        this.tipoVenue = tipo;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.restriccionesDeUso = restricciones;
        this.aprobado = false;
        this.eventosReservados = new HashMap<>();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipoVenue() {
        return tipoVenue;
    }
    
    public void setTipoVenue(String tipo) {
        this.tipoVenue = tipo;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getRestriccionesUso() {
        return restriccionesDeUso;
    }
    
    public void setRestriccionesUso(String restricciones) {
        this.restriccionesDeUso = restricciones;
    }
    
    public String getProximoEvento() {
        return proximoEvento;
    }
    
    public void setProximoEvento(String evento) {
        this.proximoEvento = evento;
    }
    
    public Date getFechaProximoEvento() {
        return fechaProximoEvento;
    }
    
    public void setFechaProximoEvento(Date fecha) {
        this.fechaProximoEvento = fecha;
    }
    
    public boolean isAprobado() {
        return aprobado;
    }
    
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public void setProximoEvento(Evento evento) {
        if (evento != null) {
            this.proximoEvento = evento.getNombreEvento();
            this.fechaProximoEvento = evento.getFecha();
            if (validarDisponibilidad(evento.getFecha())) {
                eventosReservados.put(evento.getFecha(), evento);
            }
        }
    }

    public boolean validarDisponibilidad(Date fecha) {
        if (fecha == null) {
            System.err.println("Error: ingresa una fecha valida");
            return false;
        }

        for (Date fechaReservada : eventosReservados.keySet()) {
            if (mismoDia(fecha, fechaReservada)) {
                return false; 
            }
        }
        return true; 
    }

    private boolean mismoDia(Date f1, Date f2) {
        if (f1 == null || f2 == null) {
            return false;
        }
        
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(f1);
        cal2.setTime(f2);
        
        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
               cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) &&
               cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH);
    }
    
    public boolean agregarEvento(Evento evento) {
        if (evento == null || evento.getFecha() == null) {
            System.err.println("Error: evento o fecha nulos");
            return false;
        }
        
        if (!validarDisponibilidad(evento.getFecha())) {
            System.err.println("Error: el venue no está disponible en la fecha ");
            return false;
        }
        
        eventosReservados.put(evento.getFecha(), evento);
        this.proximoEvento = evento.getNombreEvento();
        this.fechaProximoEvento = evento.getFecha();
        return true;
    }

    public void removerEvento(Evento evento) {
        if (evento != null && evento.getFecha() != null) {
            eventosReservados.remove(evento.getFecha());
            if (evento.getNombreEvento().equals(this.proximoEvento)) {
                this.proximoEvento = null;
                this.fechaProximoEvento = null;
            }
        }
    }
    
    public Map<Date, Evento> getEventosReservados() {
        return new HashMap<>(eventosReservados);
    }
    
}
