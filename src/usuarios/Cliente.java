package usuarios;

import java.util.ArrayList;

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

}