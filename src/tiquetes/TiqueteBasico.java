package tiquetes;
import evento.Evento;
import evento.Localidad;
import usuarios.Usuario;



public class TiqueteBasico extends Tiquete{
	public double precioTiqueteBasico;
	
	
	public TiqueteBasico(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio, Usuario usuario) {
		super(id, transferible, silla, localidad, evento, usuario);
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
