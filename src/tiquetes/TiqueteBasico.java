package tiquetes;

import evento.Evento;
import evento.Localidad;
import usuarios.Usuario;

public class TiqueteBasico extends Tiquete {
    public double precioTiqueteBasico;

    public TiqueteBasico(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio, Usuario usuario) {
    	super(id, transferible, silla, localidad, evento, usuario);
    	if (precio < 0) {
    		System.err.println("Error: el precio no puede ser negativo.");
    		this.precioTiqueteBasico = 0;
    		} 
    	else {
    		this.precioTiqueteBasico = precio;
    		}
    	}

    public double getPrecioTiquete() {
    	return precioTiqueteBasico;
    	}
    
    public void setPrecioTiqueteBasico(double precioTiqueteBasico) {
    	if (precioTiqueteBasico < 0) {
    		System.err.println("Error: el precio no puede ser negativo.");
    		return;
    		}
    	this.precioTiqueteBasico = precioTiqueteBasico;
    	}

    public void usarTiqueteBasico() {
    	this.setUsado(true);
    	}
}
