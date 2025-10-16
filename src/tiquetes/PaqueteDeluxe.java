package tiquetes;

import java.util.ArrayList;

public class PaqueteDeluxe {
	
    private ArrayList<Tiquete> tiquetesCortesia = new ArrayList<Tiquete>();
    private String beneficiosAdicionales;
    private String mercanciaAdicional;
    private Tiquete tiquete;

    public PaqueteDeluxe(String beneficiosAdicionales, String mercanciaAdicional, Tiquete tiquete) {
        this.beneficiosAdicionales = beneficiosAdicionales;
        this.mercanciaAdicional = mercanciaAdicional;
        this.tiquete = tiquete;
        
        if (tiquete != null) {
            tiquete.setTransferible(false);
        }
    }

    public ArrayList<Tiquete> getTiquetesCortesia() {
        return tiquetesCortesia;
    }

    public void agregarTiqueteCortesia(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetesCortesia.add(tiquete);
        }
    }

    public String getBeneficiosAdicionales() {
        return beneficiosAdicionales;
    }

    public void setBeneficiosAdicionales(String beneficiosAdicionales) {
        this.beneficiosAdicionales = beneficiosAdicionales;
    }

    public String getMercanciaAdicional() {
        return mercanciaAdicional;
    }

    public void setMercanciaAdicional(String mercanciaAdicional) {
        this.mercanciaAdicional = mercanciaAdicional;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public void setTiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
    }
}