package tiquetes;

import java.util.ArrayList;

import evento.Evento;
import evento.Localidad;
import usuarios.Usuario;

public abstract class Tiquete {
    private Usuario usuario;
    private String id;
    private boolean transferible;
    public String silla;
    protected boolean usado;
    public Localidad localidad;
    public ArrayList<Evento> eventos;

    public Tiquete(String id, boolean transferible, String silla, Localidad localidad, Evento evento, Usuario usuario) {
        this.id = id;
        this.localidad = localidad;
        this.silla = silla;
        this.transferible = transferible;
        this.usado = false;
        this.eventos = new ArrayList<Evento>();
        this.usuario = usuario;
        if (evento != null) {
            eventos.add(evento);
        }
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isUsado() {
        return usado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransferible(boolean transferible) {
        this.transferible = transferible;
    }

    public boolean isTransferible() {
        return transferible;
    }

    public String getSilla() {
        return silla;
    }

    public void setSilla(String silla) {
        this.silla = silla;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public abstract double getPrecioTiquete();
    
    public double getPrecio() {
        return getPrecioTiquete();
    }
}
