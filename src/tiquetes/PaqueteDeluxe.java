package tiquetes;

import java.util.ArrayList;

public class PaqueteDeluxe {
	
    private ArrayList<Tiquete> tiquetesCortesia = new ArrayList<Tiquete>();
    private String beneficiosAdicionales;
    private String mercanciaAdicional;
    private Tiquete tiquete;

    public PaqueteDeluxe(String beneficiosAdicionales, String mercanciaAdicional, Tiquete tiquete) {
        if (tiquete == null) {
            System.err.println("Error: el tiquete no puede ser nulo.");
            return;
        }
        
        this.beneficiosAdicionales = beneficiosAdicionales;
        this.mercanciaAdicional = mercanciaAdicional;
        this.tiquete = tiquete;
        
        tiquete.setTransferible(false);
    }

    public ArrayList<Tiquete> getTiquetesCortesia() {
        return tiquetesCortesia;
    }

    public void agregarTiqueteCortesia(Tiquete tiquete) {
        if (tiquete != null) {
            tiquete.setTransferible(false);
            tiquetesCortesia.add(tiquete);
        } else {
            System.err.println("Error: no se puede agregar un tiquete nulo.");
        }
    }

    public String getBeneficiosAdicionales() {
        return beneficiosAdicionales;
    }

    public void setBeneficiosAdicionales(String beneficiosAdicionales) {
        if (beneficiosAdicionales == null) {
            System.err.println("Error: beneficios adicionales no puede ser nulo.");
            return;
        }
        this.beneficiosAdicionales = beneficiosAdicionales;
    }

    public String getMercanciaAdicional() {
        return mercanciaAdicional;
    }

    public void setMercanciaAdicional(String mercanciaAdicional) {
        if (mercanciaAdicional == null) {
            System.err.println("Error: mercancía adicional no puede ser nulo.");
            return;
        }
        this.mercanciaAdicional = mercanciaAdicional;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public void setTiquete(Tiquete tiquete) {
        if (tiquete == null) {
            System.err.println("Error: el tiquete no puede ser nulo.");
            return;
        }
        tiquete.setTransferible(false);
        this.tiquete = tiquete;
    }
}