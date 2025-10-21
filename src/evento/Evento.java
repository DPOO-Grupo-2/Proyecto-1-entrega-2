package evento;

import usuarios.Administrador;
import usuarios.Organizador;
import usuarios.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import compra.VendedorTiquetes;
import tiquetes.Tiquete;

public class Evento {
    
    private String nombreEvento;
    private Date fecha;
    private String hora;
    private ArrayList<Tiquete> tiquetesVendidos;
    private int cantidadTiquetesBasicos;
    private int cantidadTiquetesMultiples;
    private int cantidadTiquetesDelux;
    private double cargoPorcentual;
    private double cuotaAdicional;
    private boolean cancelado;
    private VendedorTiquetes vendedorEvento;
    private int cantidadMaxTiquetesBasicos;
    private int cantidadMaxDeluxe;
    private int cantidadMaxMultiples;
    private int maxTiquetesPorTransaccion;
    private Administrador administrador;
    private HashSet<Localidad> localidades;
    private Organizador organizador;
    private Venue venue;
    private String tipoEvento;
    private static List<Evento> todosLosEventos = new ArrayList<>();

    
    public Evento(String nombreEvento, Date fecha, int cantidadTiquetesBasicos, int cantidadTiquetesMultiples, 
            int cantidadTiquetesDelux, double cargoPorcentual, double cuotaAdicional, 
            int cantidadMaxTiquetesBasicos, int cantidadMaxDeluxe, int cantidadMaxMultiples, 
            Organizador organizador, Venue venue, Administrador administrador) {
    	
    	this.nombreEvento = nombreEvento;
    	this.fecha = fecha;
    	this.hora = "00:00";
    	this.cantidadTiquetesBasicos = cantidadTiquetesBasicos;
    	this.cantidadTiquetesMultiples = cantidadTiquetesMultiples;
    	this.cantidadTiquetesDelux = cantidadTiquetesDelux;
    	this.cargoPorcentual = cargoPorcentual;
    	this.cuotaAdicional = cuotaAdicional;
    	this.cantidadMaxTiquetesBasicos = cantidadMaxTiquetesBasicos;
    	this.cantidadMaxDeluxe = cantidadMaxDeluxe;
    	this.cantidadMaxMultiples = cantidadMaxMultiples;
    	this.maxTiquetesPorTransaccion = 10;
    	this.organizador = organizador;
    	this.venue = venue;
    	this.administrador = administrador;
    	this.vendedorEvento = new VendedorTiquetes();
    	this.tiquetesVendidos = new ArrayList<Tiquete>();
    	this.localidades = new HashSet<Localidad>();
    	this.cancelado = false;
    	this.tipoEvento = "GENERAL";
    	
    	todosLosEventos.add(this);
}


    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getCargoPorcentual() {
        return cargoPorcentual;
    }

    public void setCargoPorcentual(double cargoPorcentual) {
        this.cargoPorcentual = cargoPorcentual;
    }

    public double getCuotaAdicional() {
        return cuotaAdicional;
    }

    public void setCuotaAdicional(double cuotaAdicional) {
        this.cuotaAdicional = cuotaAdicional;
    }

    public int getCantidadTiquetesBasicos() {
        return cantidadTiquetesBasicos;
    }

    public void setCantidadTiquetesBasicos(int cantidadTiquetesBasicos) {
        this.cantidadTiquetesBasicos = cantidadTiquetesBasicos;
    }

    public int getCantidadTiquetesMultiples() {
        return cantidadTiquetesMultiples;
    }

    public void setCantidadTiquetesMultiples(int cantidadTiquetesMultiples) {
        this.cantidadTiquetesMultiples = cantidadTiquetesMultiples;
    }

    public int getCantidadTiquetesDelux() {
        return cantidadTiquetesDelux;
    }

    public void setCantidadTiquetesDelux(int cantidadTiquetesDelux) {
        this.cantidadTiquetesDelux = cantidadTiquetesDelux;
    }

    public int getCantidadMaxTiquetesBasicos() {
        return cantidadMaxTiquetesBasicos;
    }

    public void setCantidadMaxTiquetesBasicos(int cantidadMaxTiquetesBasicos) {
        this.cantidadMaxTiquetesBasicos = cantidadMaxTiquetesBasicos;
    }

    public int getCantidadMaxDeluxe() {
        return cantidadMaxDeluxe;
    }

    public void setCantidadMaxDeluxe(int cantidadMaxDeluxe) {
        this.cantidadMaxDeluxe = cantidadMaxDeluxe;
    }

    public int getCantidadMaxMultiples() {
        return cantidadMaxMultiples;
    }

    public void setCantidadMaxMultiples(int cantidadMaxMultiples) {
        this.cantidadMaxMultiples = cantidadMaxMultiples;
    }

    public int getMaxTiquetesPorTransaccion() {
        return maxTiquetesPorTransaccion;
    }

    public void setMaxTiquetesPorTransaccion(int maxTiquetesPorTransaccion) {
        this.maxTiquetesPorTransaccion = maxTiquetesPorTransaccion;
    }

    public HashSet<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(HashSet<Localidad> localidades) {
        this.localidades = localidades;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
        if (cancelado && todosLosEventos.contains(this)) {
            todosLosEventos.remove(this);
        }
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public ArrayList<Tiquete> getTiquetesVendidos() {
        return tiquetesVendidos;
    }

    public void setTiquetesVendidos(ArrayList<Tiquete> tiquetesVendidos) {
        this.tiquetesVendidos = tiquetesVendidos;
    }

    public VendedorTiquetes getVendedorEvento() {
        return vendedorEvento;
    }

    public void setVendedorEvento(VendedorTiquetes vendedorEvento) {
        this.vendedorEvento = vendedorEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public void agregarLocalidad(Localidad localidad) {
        if (localidad != null) {
            this.localidades.add(localidad);
        }
    }

    public void removerLocalidad(Localidad localidad) {
        if (localidad != null) {
            this.localidades.remove(localidad);
        }
    }

    public Tiquete venderTiquete(String tipoTiquete, Usuario usuario, Evento evento, Localidad localidad, int numeroTiquetes) {
        if (tipoTiquete == null || usuario == null || localidad == null) {
            System.err.println("Error: datos nulos.");
            return null;
        }

        if (this.cancelado) {
            System.err.println("Error: el evento está cancelado.");
            return null;
        }

        if (!this.localidades.contains(localidad)) {
            System.err.println("Error: la localidad no pertenece a este evento.");
            return null;
        }

        Tiquete tiquete = vendedorEvento.venderTiquete(
            tipoTiquete, 
            0, 
            this.cargoPorcentual, 
            this.cuotaAdicional, 
            usuario, 
            this, 
            localidad, 
            numeroTiquetes
        );

        if (tiquete != null) {
            this.tiquetesVendidos.add(tiquete);
        }

        return tiquete;
    }

    public boolean validarFecha() {
        if (fecha == null) {
            return false;
        }
        Date ahora = new Date();
        return !fecha.before(ahora);
    }

    public boolean estaVencido() {
        if (fecha == null) {
            return true;
        }
        Date ahora = new Date();
        return fecha.before(ahora);
    }
    
    public static List<Evento> getTodosLosEventos() {
        return new ArrayList<>(todosLosEventos);
    }
}