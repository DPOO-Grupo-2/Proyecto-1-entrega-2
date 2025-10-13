package tiquetes;
import evento.evento;
import evento.localidad;



public class tiqueteBasico extends tiquete{
	public double precioTiqueteBasico;
	
	
	public tiqueteBasico(String id, boolean transferible, String silla, localidad localidad, evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precioTiqueteBasico = precio;
	}


	public double getPrecioTiqueteBasico() {
		return precioTiqueteBasico;
	}


	public void setPrecioTiqueteBasico(double precioTiqueteBasico) {
		this.precioTiqueteBasico = precioTiqueteBasico;
	}
	
public void usarTiqueteBasico() {
	this.usado = true;
}

	


	
	

}
