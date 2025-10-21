package tiquetes;

import evento.Localidad;
import usuarios.Usuario;
import java.util.ArrayList;
import evento.Evento;

public class TiqueteMultiple extends Tiquete {
    public int numeroDeTiquetes;
    public ArrayList<Tiquete> tiquetes = new ArrayList<Tiquete>();
    public ArrayList<Tiquete> tiquetesUsados = new ArrayList<Tiquete>();
    public double precioTiqueteMultiple;

    public TiqueteMultiple(String id, boolean transferible, String silla, Localidad localidad, Evento evento, double precio, int numeroDeTiquetes, Usuario usuario) {
        super(id, transferible, silla, localidad, evento, usuario);
        if (numeroDeTiquetes <= 0) {
            System.err.println("Error: el número de tiquetes debe ser mayor a 0.");
            this.numeroDeTiquetes = 1;
        } else {
            this.numeroDeTiquetes = numeroDeTiquetes;
        }
        
        if (precio < 0) {
            System.err.println("Error: el precio no puede ser negativo.");
            this.precioTiqueteMultiple = 0;
        } else {
            this.precioTiqueteMultiple = precio;
        }
        this.setTransferible(false);
    }

    public void añadirTiquete(Tiquete nuevoTiquete) {
    	if (nuevoTiquete != null) {
            this.tiquetes.add(nuevoTiquete);
        } else {
            System.err.println("Error: no se puede agregar un tiquete nulo.");
        }
    }

    public int getNumeroDeTiquetes() {
        return numeroDeTiquetes;
    }

    public void setNumeroDeTiquetes(int numeroDeTiquetes) {
    	 if (numeroDeTiquetes <= 0) {
             System.err.println("Error: el número de tiquetes debe ser mayor a 0.");
             return;
         }
         this.numeroDeTiquetes = numeroDeTiquetes;
    }

    public ArrayList<Tiquete> getTiquetes() {
        return tiquetes;
    }

    public ArrayList<Tiquete> getTiquetesUsados() {
        return tiquetesUsados;
    }

    public double getPrecioTiquete() {
        return precioTiqueteMultiple;
    }

    public void setPrecioTiqueteMultiple(double precioTiqueteMultiple) {
    	if (precioTiqueteMultiple < 0) {
            System.err.println("Error: el precio no puede ser negativo.");
            return;
        }
        this.precioTiqueteMultiple = precioTiqueteMultiple;
    }

    public void usarTiqueteMultiple() {
    	if (!tiquetes.isEmpty()) {
            Tiquete usado = tiquetes.remove(tiquetes.size() - 1);
            usado.setUsado(true);
            tiquetesUsados.add(usado);

            if (tiquetes.isEmpty()) {
                setUsado(true);
            }
        } else {
            System.err.println("Error: no hay tiquetes disponibles para usar.");
        }
    }
}