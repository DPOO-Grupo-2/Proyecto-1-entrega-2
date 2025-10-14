package evento;

import usuarios.Organizador;
import java.util.Date;
import java.util.HashSet;

public class Evento {
	
    private String nombreEvento;
    private Date fecha;
    private int cantidadTiquetesBasicos;
    private int cantidadTiquetesMultiples;
    private int cantidadTiquetesDelux;
    private double cargoPorcentual;
    private double cuotaAdicional;
    private boolean cancelado;

    private int cantidadMaxTiquetesBasicos;
    private int cantidadMaxDeluxe;
    private int cantidadMaxMultiples;

    private HashSet<Localidad> localidades = new HashSet<>();
    private Organizador organizador;
    private Venue venue;

    public Evento(String nombreEvento, Date fecha, int cantidadTiquetesBasicos, int cantidadTiquetesMultiples, 
                  int cantidadTiquetesDelux, double cargoPorcentual, double cuotaAdicional, 
                  int cantidadMaxTiquetesBasicos, int cantidadMaxDeluxe, int cantidadMaxMultiples, 
                  Organizador organizador, Venue venue) {

        this.nombreEvento = nombreEvento;
        this.fecha = fecha;
        this.cantidadTiquetesBasicos = cantidadTiquetesBasicos;
        this.cantidadTiquetesMultiples = cantidadTiquetesMultiples;
        this.cantidadTiquetesDelux = cantidadTiquetesDelux;
        this.cargoPorcentual = cargoPorcentual;
        this.cuotaAdicional = cuotaAdicional;
        this.cantidadMaxTiquetesBasicos = cantidadMaxTiquetesBasicos;
        this.cantidadMaxDeluxe = cantidadMaxDeluxe;
        this.cantidadMaxMultiples = cantidadMaxMultiples;
        this.organizador = organizador;
        this.venue = venue;
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
    }}
