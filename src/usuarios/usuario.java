package usuarios;

import java.util.ArrayList;
import tiquetes.tiquete;

public class usuario {
	
	private String login;
    private String password;
    private double saldo;
    private ArrayList<tiquete> tiquetesActivos;
    
    public usuario(String login, String password, double saldo) {
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
    
    public ArrayList<tiquete> getTiquetesActivos() {
        return tiquetesActivos;
    }
    
    public void setTiquetesActivos(ArrayList<tiquete> tiquetes) {
        this.tiquetesActivos = tiquetes;
    }
    
    public void agregarTiquete(tiquete tiquete) {
        this.tiquetesActivos.add(tiquete);
    }

    public boolean removerTiquete(tiquete tiquete) {
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
    
    public void transferirTiquete(tiquete tiqueteATransferir, usuario usuarioDestino, String passwordEmisor) {
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
    
    
