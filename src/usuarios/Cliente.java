package usuarios;

import java.util.ArrayList;

import evento.Evento;
import evento.Localidad;
import tiquetes.Tiquete;

public class Cliente extends Usuario{
	
	private ArrayList<Tiquete> tiquetesComprados;
    private ArrayList<String> historialTransacciones;

	public Cliente(String login, String password, double saldo) {
		super(login, password, saldo);
		this.tiquetesComprados = new ArrayList<>();
        this.historialTransacciones = new ArrayList<>();
	}
	
	public ArrayList<Tiquete> getTiquetesComprados() {
        return tiquetesComprados;
    }
    
    public ArrayList<String> getHistorialTransacciones() {
        return historialTransacciones;
    }
    
    public void comprarTiquete(String tipoTiquete, Usuario usuario, Evento evento, Localidad localidad1, int numeroTiquetes){
    	Tiquete tiquete = evento.venderTiquete(tipoTiquete, this, evento, localidad1, numeroTiquetes);
    	this.tiquetesComprados.add(tiquete);
    	//Esto se tiene que hacer con persistencia
    	this.historialTransacciones.add("compra de tiquete realizada por un valor de " + tiquete.getPrecioTiquete());
    	
    }
    
    public void transferirTiquete(Usuario emisor, Usuario receptor, Tiquete tiquete) {
    	emisor.getTiquetesActivos().remove(tiquete);
    	receptor.getTiquetesActivos().add(tiquete);
    }

}