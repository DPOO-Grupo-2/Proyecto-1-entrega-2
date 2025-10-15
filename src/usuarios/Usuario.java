package usuarios;

import java.util.ArrayList;

import evento.Evento;
import tiquetes.Tiquete;

public class Usuario {
	
	private String login;
    private String password;
    private double saldo;
    private ArrayList<Tiquete> tiquetesActivos;
    
    public Usuario(String login, String password, double saldo) {
        this.login = login;
        this.password = password;
        this.saldo = saldo;
        this.tiquetesActivos = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public ArrayList<Tiquete> getTiquetesActivos() {
        return tiquetesActivos;
    }
    
    public void setTiquetesActivos(ArrayList<Tiquete> tiquetes) {
        this.tiquetesActivos = tiquetes;
    }
    
    public void agregarTiquete(Tiquete tiquete) {
        this.tiquetesActivos.add(tiquete);
    }

    public boolean removerTiquete(Tiquete tiquete) {
        return this.tiquetesActivos.remove(tiquete);
    }
    
    public boolean autenticar(String password) {
        return this.password.equals(password);
    }
    
    public void actualizarSaldo(double monto) {
        this.saldo += monto;
    }
    

    public boolean validarCredenciales(String login, String password) {
        return this.login.equals(login) && this.password.equals(password);
    }
    
    public void transferirTiquete(Tiquete tiqueteATransferir, Usuario usuarioDestino, String passwordEmisor) {
    	if (tiqueteATransferir == null || usuarioDestino == null) {
            System.out.println("Error: datos incompletos.");
            return;
        }
    	
        if (!autenticar(passwordEmisor)) {
            System.out.println("Error: Contraseña incorrecta");
            return;
        }
        if (!tiqueteATransferir.isTransferible()) {
            System.out.println("Error: este tiquete no es transferible.");
            return;
        }
        if (tiqueteATransferir.isUsado()) {
            System.out.println("Error: el tiquete ya fue usado.");
            return;
        }

    	this.removerTiquete(tiqueteATransferir);
    	usuarioDestino.agregarTiquete(tiqueteATransferir);
    	
    }



}
    
    
