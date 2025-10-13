package tiquetes;
import evento.evento;
import evento.localidad;
import compra.vendedorTiquetes;



public class tiqueteBasico extends tiquete{
	public double precioTiqueteBasico;
	
	
	public tiqueteBasico(String id, boolean transferible, String silla, localidad localidad, evento evento, double precio) {
		super(id, transferible, silla, localidad, evento);
		this.precioTiqueteBasico = precio;
	}
	


	


	
	

}
