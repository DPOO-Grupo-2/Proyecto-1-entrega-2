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

    public void cancelarTiquete(String codigoTiquete, String nombreEvento) {
        ArrayList<Tiquete> tiquetesUsuario = this.tiquetesComprados;
        boolean encontrado = false;
        int i = 0;
        
        while (!encontrado && i < tiquetesUsuario.size()){
            Tiquete tiquete = tiquetesUsuario.get(i);
            if (tiquete.getId().equals(codigoTiquete)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        
        if (encontrado) {
            Tiquete tiquete = tiquetesUsuario.get(i); 
            ArrayList<Evento> eventos = tiquete.getEventos();
            boolean encontrado2 = false;
            int j = 0;
            
            while (!encontrado2 && j < eventos.size()) {  
                Evento evento = eventos.get(j);
                if (nombreEvento.equals(evento.getNombreEvento())){
                    encontrado2 = true;
                } else {
                    j++; 
                }
            }
            
            if (encontrado2) {
                Evento evento = eventos.get(j);
                if (evento.getAdministrador().aprobarCancelacionCLiente(evento)) {
                    tiquetesUsuario.remove(i);
                    evento.getTiquetesVedidos().remove(tiquete);
      
                    this.historialTransacciones.add("Cancelación de tiquete: " + codigoTiquete);
}
      }
        }
    }
    
}