package sistema;

import persistencia.*;
import persistencia.GestorPersistencia.DatosSistema;
import usuarios.*;
import evento.*;
import tiquetes.*;
import java.util.*;


public class SistemaBoletaMaster {
    

    private List<Usuario> usuarios;
    private List<Evento> eventos;
    private List<Venue> venues;
    private List<Tiquete> tiquetes;
    

    private GestorPersistencia persistencia;
    

    private Map<String, Usuario> mapaUsuarios;
    private Map<String, Evento> mapaEventos;
    private Map<String, Venue> mapaVenues;
    

    public SistemaBoletaMaster() {
        this.persistencia = new GestorPersistencia();
        this.persistencia.inicializar();
        

        this.mapaUsuarios = new HashMap<>();
        this.mapaEventos = new HashMap<>();
        this.mapaVenues = new HashMap<>();
        

        if (persistencia.existenDatos()) {
            cargarDatos();
            System.out.println("Datos cargados del archivo");
        } else {
            inicializarNuevo();
            System.out.println("Sistema inicializado (sin datos previos)");
        }
    }
    

    public void cargarDatos() {
        DatosSistema datos = persistencia.cargarTodo();
        this.usuarios = datos.usuarios;
        this.eventos = datos.eventos;
        this.venues = datos.venues;
        this.tiquetes = datos.tiquetes;
        

        reconstruirMapas();
    }
    

    public void guardarDatos() {
        persistencia.guardarTodo(usuarios, eventos, venues, tiquetes);
    }
    

    private void inicializarNuevo() {
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.venues = new ArrayList<>();
        this.tiquetes = new ArrayList<>();
        

        Administrador admin = new Administrador("admin", "admin123", 0);
        usuarios.add(admin);
        mapaUsuarios.put(admin.getLogin(), admin);
        

        guardarDatos();
    }
    

    private void reconstruirMapas() {
        mapaUsuarios.clear();
        mapaEventos.clear();
        mapaVenues.clear();
        
        for (Usuario u : usuarios) {
            mapaUsuarios.put(u.getLogin(), u);
        }
        
        for (Evento e : eventos) {
            mapaEventos.put(e.getNombreEvento(), e);
        }
        
        for (Venue v : venues) {
            mapaVenues.put(v.getNombre(), v);
        }
    }
    

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public List<Evento> getEventos() {
        return new ArrayList<>(eventos);
    }
    
    public List<Venue> getVenues() {
        return new ArrayList<>(venues);
    }
    
    public List<Tiquete> getTiquetes() {
        return new ArrayList<>(tiquetes);
    }
    

    public Usuario buscarUsuario(String login) {
        return mapaUsuarios.get(login);
    }
    
    public Evento buscarEvento(String nombre) {
        return mapaEventos.get(nombre);
    }
    
    public Venue buscarVenue(String nombre) {
        return mapaVenues.get(nombre);
    }
    

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || mapaUsuarios.containsKey(usuario.getLogin())) {
            return false;
        }
        
        usuarios.add(usuario);
        mapaUsuarios.put(usuario.getLogin(), usuario);
        guardarDatos();
        return true;
    }
    

    public boolean registrarVenue(Venue venue) {
        if (venue == null || mapaVenues.containsKey(venue.getNombre())) {
            return false;
        }
        
        venues.add(venue);
        mapaVenues.put(venue.getNombre(), venue);
        guardarDatos();
        return true;
    }
    

    public boolean crearEvento(Evento evento) {
        if (evento == null || mapaEventos.containsKey(evento.getNombreEvento())) {
            return false;
        }
        
        eventos.add(evento);
        mapaEventos.put(evento.getNombreEvento(), evento);
        guardarDatos();
        return true;
    }
    

    public Tiquete venderTiquete(String tipoTiquete, Usuario usuario, 
                                  Evento evento, Localidad localidad, 
                                  int numeroTiquetes) {
        Tiquete tiquete = evento.venderTiquete(tipoTiquete, usuario, 
                                               evento, localidad, numeroTiquetes);
        if (tiquete != null) {
            tiquetes.add(tiquete);
            guardarDatos();
        }
        return tiquete;
    }
    

    public Usuario autenticar(String login, String password) {
        Usuario usuario = mapaUsuarios.get(login);
        if (usuario != null && usuario.autenticar(password)) {
            return usuario;
        }
        return null;
    }
}