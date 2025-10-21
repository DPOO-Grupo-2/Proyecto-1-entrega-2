package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evento.Evento;
import evento.Localidad;
import evento.Venue;
import tiquetes.Tiquete;

public class Administrador extends Usuario {

    protected static final int COSTOFIJOEMISION = 5000;
    private Map<String, Double> porcentajeServicioPorTipo;
    private List<Evento> solicitudesCancelacion;

    public Administrador(String login, String password, double saldo) {
        super(login, password, saldo);
        this.porcentajeServicioPorTipo = new HashMap<>();
        this.solicitudesCancelacion = new ArrayList<>();

        porcentajeServicioPorTipo.put("MUSICAL", 10.0);
        porcentajeServicioPorTipo.put("CULTURAL", 8.0);
        porcentajeServicioPorTipo.put("DEPORTIVO", 12.0);
        porcentajeServicioPorTipo.put("RELIGIOSO", 5.0);
        porcentajeServicioPorTipo.put("GENERAL", 10.0);
    }

    public int getCostoFijoEmision() {
        return COSTOFIJOEMISION;
    }

    public void setPorcentajeServicioPorTipo(Map<String, Double> mapa) {
        if (mapa == null) {
            System.err.println("Error: el mapa no puede ser nulo.");
            return;
        }
        this.porcentajeServicioPorTipo = new HashMap<>(mapa);
    }

    public Map<String, Double> getPorcentajeServicioPorTipo() {
        return new HashMap<>(porcentajeServicioPorTipo);
    }

    public void definirPorcentajeServicio(String tipoEvento, double porcentaje) {
        if (tipoEvento == null || tipoEvento.trim().equals("")) {
            System.err.println("Error: tipo de evento inválido.");
            return;
        }
        if (porcentaje < 0 || porcentaje > 100) {
            System.err.println("Error: porcentaje inválido.");
            return;
        }
        porcentajeServicioPorTipo.put(tipoEvento.toUpperCase(), porcentaje);
        System.out.println("Porcentaje de servicio para '" + tipoEvento + "' establecido en " + porcentaje + "%");
    }

    public double getPorcentajeServicio(String tipoEvento) {
        if (tipoEvento == null || tipoEvento.trim().equals("")) {
            return 0.0;
        }
        String tipo = tipoEvento.toUpperCase();
        return porcentajeServicioPorTipo.getOrDefault(tipo, 0.0);
    }

    public void aprobarVenue(Venue venue) {
        if (venue != null) {
            venue.setAprobado(true);
        } else {
            System.err.println("Error: venue nulo.");
        }
    }

    public void agregarSolicitudCancelacion(Evento evento) {
        if (evento == null) {
            System.err.println("Error: evento nulo.");
            return;
        }
        if (!solicitudesCancelacion.contains(evento)) {
            solicitudesCancelacion.add(evento);
        }
    }

    public List<Evento> revisarSolicitudesCancelacion() {
        return new ArrayList<>(solicitudesCancelacion);
    }

    public void autorizarCancelacionOrganizador(Evento evento) {
        if (evento == null) {
            System.err.println("Error: evento nulo.");
            return;
        }
        
        if (evento.getCancelado()) {
            System.err.println("Error: el evento ya está cancelado.");
            return;
        }
        
        if (!solicitudesCancelacion.contains(evento)) {
            System.err.println("Error: no existe solicitud de cancelación para este evento.");
            return;
        }

        cancelarEventoOrganizador(evento);
        solicitudesCancelacion.remove(evento);
    }

    public void cancelarEvento(Evento evento) {
        if (evento == null) {
            System.err.println("Error: evento nulo.");
            return;
        }

        if (evento.getCancelado()) {
            System.err.println("Error: el evento ya está cancelado.");
            return;
        }

        evento.setCancelado(true);
        
        if (evento.getOrganizador() != null) {
            evento.getOrganizador().removerEvento(evento);
        }

        ArrayList<Tiquete> tiquetesVendidos = evento.getTiquetesVendidos();
        for (int i = 0; i < tiquetesVendidos.size(); i++) {
            Tiquete tiquete = tiquetesVendidos.get(i);
            if (tiquete != null) {
                Usuario usuario = tiquete.getUsuario();
                if (usuario != null) {
                    double reembolso = tiquete.getPrecio() - COSTOFIJOEMISION;
                    if (reembolso > 0) {
                        usuario.actualizarSaldo(reembolso);
                    }
                }
            }
        }
    }

    public void cancelarEventoOrganizador(Evento evento) {
        if (evento == null) {
            System.err.println("Error: evento nulo.");
            return;
        }

        if (evento.getCancelado()) {
            System.err.println("Error: el evento ya está cancelado.");
            return;
        }

        evento.setCancelado(true);
        
        if (evento.getOrganizador() != null) {
            evento.getOrganizador().removerEvento(evento);
        }

        ArrayList<Tiquete> tiquetesVendidos = evento.getTiquetesVendidos();
        for (int i = 0; i < tiquetesVendidos.size(); i++) {
            Tiquete tiquete = tiquetesVendidos.get(i);
            if (tiquete != null) {
                Usuario usuario = tiquete.getUsuario();
                if (usuario != null) {
                    double reembolso = tiquete.getPrecio() - evento.getCuotaAdicional() - COSTOFIJOEMISION;
                    
                    if (reembolso > 0) {
                        usuario.actualizarSaldo(reembolso);
                    }
                }
            }
        }
    }

    public boolean reembolso(Usuario usuario, Tiquete tiquete) {
        if (usuario == null || tiquete == null) {
            System.err.println("Error: usuario o tiquete nulos.");
            return false;
        }

        if (!usuario.getTiquetesActivos().contains(tiquete)) {
            System.err.println("Error: el tiquete no pertenece al usuario.");
            return false;
        }

        if (tiquete.isUsado()) {
            System.err.println("Error: no se puede reembolsar un tiquete ya usado.");
            return false;
        }

        double montoReembolso = tiquete.getPrecio() - COSTOFIJOEMISION;
        if (montoReembolso < 0) {
            montoReembolso = 0;
        }

        usuario.actualizarSaldo(montoReembolso);
        usuario.removerTiquete(tiquete);

        return true;
    }

    public void setOfertaLocalidad(Evento evento, double oferta) {
        if (evento == null) {
            System.err.println("Error: evento nulo.");
            return;
        }
        
        if (oferta < 0 || oferta > 100) {
            System.err.println("Error: el descuento debe estar entre 0 y 100.");
            return;
        }

        for (Localidad localidad : evento.getLocalidades()) {
            if (localidad != null) {
                localidad.setDescuento(oferta);
            }
        }
    }

    public double consultarGananciasGenerales() {
        double total = 0.0;
        List<Evento> eventos = Evento.getTodosLosEventos();
        
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento != null && !evento.getCancelado()) {
                total += consultarGananciasPorEvento(evento);
            }
        }
        return total;
    }

    public double consultarGananciasPorFecha(Date fecha) {
        if (fecha == null) {
            System.err.println("Error: fecha nula.");
            return 0.0;
        }
        
        double total = 0.0;
        List<Evento> eventos = Evento.getTodosLosEventos();
        
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento != null && !evento.getCancelado()) {
                if (sonMismoDia(fecha, evento.getFecha())) {
                    total += consultarGananciasPorEvento(evento);
                }
            }
        }
        
        return total;
    }

    private boolean sonMismoDia(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) {
            return false;
        }
        
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(fecha1);
        cal2.setTime(fecha2);
        
        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
               cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) &&
               cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH);
    }

    public double consultarGananciasPorEvento(Evento evento) {
        if (evento == null) {
            return 0.0;
        }

        double total = 0.0;
        ArrayList<Tiquete> tiquetesVendidos = evento.getTiquetesVendidos();
        
        for (int i = 0; i < tiquetesVendidos.size(); i++) {
            Tiquete tiquete = tiquetesVendidos.get(i);
            if (tiquete != null) {
                total += evento.getCuotaAdicional() + COSTOFIJOEMISION;
            }
        }

        return total;
    }

    public double consultarGananciasPorOrganizador(Organizador organizador) {
        if (organizador == null) {
            return 0.0;
        }

        double total = 0.0;
        List<Evento> eventos = organizador.getEventosCreados();

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            if (e != null && !e.getCancelado()) {
                total += consultarGananciasPorEvento(e);
            }
        }

        return total;
    }
}