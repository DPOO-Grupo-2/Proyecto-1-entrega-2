package usuarios;

import java.util.HashMap;
import java.util.Map;

import evento.Evento;
import evento.Localidad;
import evento.Venue;

public class Administrador extends Usuario {

    protected static final int COSTOFIJOEMISION = 5000;
    private Map<String, Double> porcentajeServicioPorTipo;

    public Administrador(String login, String password, double saldo) {
        super(login, password, saldo);
        this.porcentajeServicioPorTipo = new HashMap<>();

        porcentajeServicioPorTipo.put("MUSICAL", 0.10);
        porcentajeServicioPorTipo.put("CULTURAL", 0.08);
        porcentajeServicioPorTipo.put("DEPORTIVO", 0.12);
        porcentajeServicioPorTipo.put("RELIGIOSO", 0.05);
    }

    public int getCostoFijoEmision() {
        return COSTOFIJOEMISION;
    }
    
    public void setPorcentajeServicioPorTipo(Map<String, Double> mapa) {
        this.porcentajeServicioPorTipo = mapa;
    }

    public Map<String, Double> getPorcentajeServicioPorTipo() {
        return porcentajeServicioPorTipo;
    }

    public void aprobarVenue(Venue venue) {
        if (venue != null) {
            System.out.println("El venue " + venue.getNombre() + " ha sido aprobado.");
            venue.setAprobado(true);  
        } else {
            System.out.println("Error: escribe de nuevo el nombre del venue.");
        }
    }
    
    public boolean aprobarCancelacionEvento(Evento evento, Organizador organizador) {
        if (evento == null || organizador == null) {
            return false;
        }

        if (!organizador.tieneSolicitudPendiente(evento)) {
            return false;
        }

        if (evento.getCancelado()) {
            return false;
        }

        evento.setCancelado(true);
        organizador.removerSolicitudPendiente(evento);
        return true;
    }


    public void cancelarEvento(Evento evento) {
        if (evento != null) {
            System.out.println("El evento " + evento.getNombreEvento() + " ha sido cancelado.");
            evento.setCancelado(true); 
        } else {
            System.out.println("Error: el evento es nulo.");
        }
    
    }
    public void setOfertaLocalidad(Evento evento, double oferta) {
        for (Localidad localidad : evento.getLocalidades()) {
            localidad.setDescuento(oferta);
        }
    }
}