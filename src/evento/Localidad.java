package evento;

import java.util.ArrayList;

public class Localidad {
	
    public String nombreLocalidad;
    public boolean tieneNumeracion;
    public double descuento;
    public double precioBasico;
    public double precioDelux;
    public double precioMultiple;
    public double precioTemporada;
    
    private ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
     
    public String getNombreLocalidad() {
        return nombreLocalidad;
    }
    
    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }
    
    public boolean isTieneNumeracion() {
        return tieneNumeracion;
    }
    
    public void setTieneNumeracion(boolean tieneNumeracion) {
        this.tieneNumeracion = tieneNumeracion;
    }
    
    public double getDescuento() {
        return descuento;
    }
    
    public void setDescuento(double descuento) {
        this.descuento = descuento;
        
    }
    
    public double getPrecioBasicoConDescuento() {
        return precioBasico * (1 - descuento / 100.0);
    }
    
    public double getPrecioDeluxConDescuento() {
        return precioDelux * (1 - descuento / 100.0);
    }
    
    public double getPrecioMultipleConDescuento() {
        return precioMultiple * (1 - descuento / 100.0);
    }
    
    public double getPrecioTemporadaConDescuento() {
        return precioTemporada * (1 - descuento / 100.0);
    }
    
    public double getPrecioBasico() {
        return precioBasico;
    }
    
    public void setPrecioBasico(double precioBasico) {
        this.precioBasico = precioBasico;
    }
    
    public double getPrecioDelux() {
        return precioDelux;
    }
    
    public void setPrecioDelux(double precioDelux) {
        this.precioDelux = precioDelux;
    }
    
    public double getPrecioMultiple() {
        return precioMultiple;
    }
    
    public void setPrecioMultiple(double precioMultiple) {
        this.precioMultiple = precioMultiple;
    }
    
    public double getPrecioTemporada() {
        return precioTemporada;
    }
    
    public void setPrecioTemporada(double precioTemporada) {
        this.precioTemporada = precioTemporada;
    }
    
    public void agregarOferta(Oferta oferta) {
        if (oferta != null) {
            ofertas.add(oferta);
        }
    }
    
    public void removerOferta(Oferta oferta) {
        if (oferta != null) {
            ofertas.remove(oferta);
        }
    }   
    
    public ArrayList<Oferta> getOfertas() {
        return new ArrayList<Oferta>(ofertas);
    }
}