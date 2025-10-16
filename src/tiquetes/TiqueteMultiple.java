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

    public TiqueteMultiple(String id, boolean transferible, String silla, Localidad localidad,
                           Evento evento, double precio, int numeroDeTiquetes, Usuario usuario) {
        super(id, transferible, silla, localidad, evento, usuario);
        this.numeroDeTiquetes = numeroDeTiquetes;
        this.precioTiqueteMultiple = precio;
        this.setTransferible(false);
    }

    public void añadirTiquete(Tiquete nuevoTiquete) {
        this.tiquetes.add(nuevoTiquete);
    }

    public int getNumeroDeTiquetes() {
        return numeroDeTiquetes;
    }

    public void setNumeroDeTiquetes(int numeroDeTiquetes) {
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
        this.precioTiqueteMultiple = precioTiqueteMultiple;
    }

    public void usarTiqueteMultiple() {
        if (!tiquetes.isEmpty()) {
            Tiquete unidad = tiquetes.remove(tiquetes.size() - 1);
            unidad.setUsado(true);
            tiquetesUsados.add(unidad);

            if (tiquetes.isEmpty()) {
                setUsado(true);
            }
        }
    }
}