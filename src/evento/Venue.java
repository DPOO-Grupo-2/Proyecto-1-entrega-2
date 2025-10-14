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
            eventosReservados.put(evento.getFecha(), evento);
        }
    }

	public boolean validarDisponibilidad(Date fecha) {
		if (fecha == null) {
	        System.err.println("Error: ingresa una fecha valida");
	        return false;
	    }

	    for (Date fechaReservada : eventosReservados.keySet()) {
	        if (mismoDiaYHora(fecha, fechaReservada)) {
	            return false; 
	        }
	    }
	    return true; 
	}

	private boolean mismoDiaYHora(Date f1, Date f2) {
	    return f1 != null && f2 != null && f1.equals(f2);
	
	}
    
    
    
    
}
