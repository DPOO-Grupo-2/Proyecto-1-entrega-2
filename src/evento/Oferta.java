package evento;

import java.util.Date;

public class Oferta {

    private Evento evento;
    private Localidad localidad;
    private double descuento; 
    private Date fechaInicio;
    private Date fechaFin;

    public Oferta(Evento evento, Localidad localidad, double descuento, Date fechaInicio, Date fechaFin) {
        this.evento = evento;
        this.localidad = localidad;
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Evento getEvento() {
        return evento;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public boolean estaVigente(Date fechaActual) {
        if (fechaActual == null || fechaInicio == null || fechaFin == null) {
            return false;
        }
        return !fechaActual.before(fechaInicio) && !fechaActual.after(fechaFin);
    }

    public double calcularPrecioConDescuento(double precioBase, Date fechaActual) {
        if (estaVigente(fechaActual)) {
            double precioConDescuento = precioBase * (1 - descuento / 100.0);
            return precioConDescuento;
        }
        return precioBase;
    }
}