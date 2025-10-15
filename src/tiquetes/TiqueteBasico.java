package tiquetes;
import evento.Evento;
import evento.Localidad;



public class TiqueteBasico extends Tiquete{
	public double precioTiqueteBasico;
	
	
	public TiqueteBasico(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precioTiqueteBasico = precio;
	}


	public double getPrecioTiquete() {
		return precioTiqueteBasico;
	}


	public void setPrecioTiqueteBasico(double precioTiqueteBasico) {
		this.precioTiqueteBasico = precioTiqueteBasico;
	}
	
public void usarTiqueteBasico() {
	this.usado = true;
}

	


	
	

}
