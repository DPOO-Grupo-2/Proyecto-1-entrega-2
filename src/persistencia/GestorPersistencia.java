package persistencia;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import usuarios.*;
import evento.*;
import tiquetes.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.lang.reflect.Type;

public class GestorPersistencia {
    
    private static final String CARPETA_DATOS = "./datos";
    private static final String ARCHIVO_USUARIOS = "usuarios.json";
    private static final String ARCHIVO_EVENTOS = "eventos.json";
    private static final String ARCHIVO_VENUES = "venues.json";
    private static final String ARCHIVO_TIQUETES = "tiquetes.json";
    
    private Gson gson;
    

    private Map<String, Usuario> mapaUsuarios;
    private Map<String, Evento> mapaEventos;
    private Map<String, Venue> mapaVenues;
    private Map<String, Localidad> mapaLocalidades;
    private Map<String, Tiquete> mapaTiquetes;
    
    public GestorPersistencia() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .serializeNulls()
                .create();
        
        this.mapaUsuarios = new HashMap<>();
        this.mapaEventos = new HashMap<>();
        this.mapaVenues = new HashMap<>();
        this.mapaLocalidades = new HashMap<>();
        this.mapaTiquetes = new HashMap<>();
    }
    
    public void inicializar() {
        try {
            Path path = Paths.get(CARPETA_DATOS);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Carpeta de datos creada: " + CARPETA_DATOS);
            }
        } catch (IOException e) {
            System.err.println("Error al crear carpeta de datos: " + e.getMessage());
        }
    }
    
    public void guardarTodo(List<Usuario> usuarios, List<Evento> eventos, 
                           List<Venue> venues, List<Tiquete> tiquetes) {
        try {
            guardarUsuarios(usuarios);
            guardarVenues(venues);
            guardarEventos(eventos);
            guardarTiquetes(tiquetes);
            System.out.println("Datos guardados exitosamente");
        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public DatosSistema cargarTodo() {
        try {

            mapaUsuarios.clear();
            mapaEventos.clear();
            mapaVenues.clear();
            mapaLocalidades.clear();
            mapaTiquetes.clear();
            
            List<Usuario> usuarios = cargarUsuarios();
            List<Venue> venues = cargarVenues();
            List<Evento> eventos = cargarEventos();
            List<Tiquete> tiquetes = cargarTiquetes();
            

            reconstruirRelaciones(usuarios, eventos, venues, tiquetes);
            
            System.out.println("Datos cargados exitosamente");
            return new DatosSistema(usuarios, eventos, venues, tiquetes);
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
            return new DatosSistema();
        }
    }
    

    private void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        List<Map<String, Object>> usuariosData = new ArrayList<>();
        
        for (Usuario usuario : usuarios) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("login", usuario.getLogin());
            userData.put("password", usuario.getPassword());
            userData.put("saldo", usuario.getSaldo());
            

            if (usuario instanceof Administrador) {
                userData.put("tipo", "ADMINISTRADOR");
                Administrador admin = (Administrador) usuario;
                userData.put("porcentajesServicio", admin.getPorcentajeServicioPorTipo());
            } else if (usuario instanceof Organizador) {
                userData.put("tipo", "ORGANIZADOR");
            } else if (usuario instanceof Cliente) {
                userData.put("tipo", "CLIENTE");
                Cliente cliente = (Cliente) usuario;
                userData.put("historialTransacciones", cliente.getHistorialTransacciones());
            } else {
                userData.put("tipo", "USUARIO");
            }
            
            List<String> tiquetesIds = new ArrayList<>();
            for (Tiquete t : usuario.getTiquetesActivos()) {
                tiquetesIds.add(t.getId());
            }
            userData.put("tiquetesIds", tiquetesIds);
            
            usuariosData.add(userData);
        }
        
        String json = gson.toJson(usuariosData);
        escribirArchivo(ARCHIVO_USUARIOS, json);
    }
    
    private void guardarVenues(List<Venue> venues) throws IOException {
        List<Map<String, Object>> venuesData = new ArrayList<>();
        
        for (Venue venue : venues) {
            Map<String, Object> venueData = new HashMap<>();
            venueData.put("nombre", venue.getNombre());
            venueData.put("tipoVenue", venue.getTipoVenue());
            venueData.put("capacidad", venue.getCapacidad());
            venueData.put("ubicacion", venue.getUbicacion());
            venueData.put("restriccionesDeUso", venue.getRestriccionesUso());
            venueData.put("aprobado", venue.isAprobado());
            
            if (venue.getProximoEvento() != null) {
                venueData.put("proximoEvento", venue.getProximoEvento());
            }
            if (venue.getFechaProximoEvento() != null) {
                venueData.put("fechaProximoEvento", venue.getFechaProximoEvento().getTime());
            }
            
            venuesData.add(venueData);
        }
        
        String json = gson.toJson(venuesData);
        escribirArchivo(ARCHIVO_VENUES, json);
    }
    
    private void guardarEventos(List<Evento> eventos) throws IOException {
        List<Map<String, Object>> eventosData = new ArrayList<>();
        
        for (Evento evento : eventos) {
            Map<String, Object> eventoData = new HashMap<>();
            eventoData.put("nombreEvento", evento.getNombreEvento());
            eventoData.put("fecha", evento.getFecha());
            eventoData.put("hora", evento.getHora());
            eventoData.put("cantidadTiquetesBasicos", evento.getCantidadTiquetesBasicos());
            eventoData.put("cantidadTiquetesMultiples", evento.getCantidadTiquetesMultiples());
            eventoData.put("cantidadTiquetesDelux", evento.getCantidadTiquetesDelux());
            eventoData.put("cargoPorcentual", evento.getCargoPorcentual());
            eventoData.put("cuotaAdicional", evento.getCuotaAdicional());
            eventoData.put("cancelado", evento.getCancelado());
            eventoData.put("cantidadMaxTiquetesBasicos", evento.getCantidadMaxTiquetesBasicos());
            eventoData.put("cantidadMaxDeluxe", evento.getCantidadMaxDeluxe());
            eventoData.put("cantidadMaxMultiples", evento.getCantidadMaxMultiples());
            eventoData.put("maxTiquetesPorTransaccion", evento.getMaxTiquetesPorTransaccion());
            eventoData.put("tipoEvento", evento.getTipoEvento());
            

            if (evento.getOrganizador() != null) {
                eventoData.put("organizadorLogin", evento.getOrganizador().getLogin());
            }
            if (evento.getVenue() != null) {
                eventoData.put("venueNombre", evento.getVenue().getNombre());
            }
            if (evento.getAdministrador() != null) {
                eventoData.put("administradorLogin", evento.getAdministrador().getLogin());
            }
            

            List<Map<String, Object>> localidadesData = new ArrayList<>();
            for (Localidad loc : evento.getLocalidades()) {
                Map<String, Object> locData = new HashMap<>();
                locData.put("nombreLocalidad", loc.getNombreLocalidad());
                locData.put("tieneNumeracion", loc.isTieneNumeracion());
                locData.put("descuento", loc.getDescuento());
                locData.put("precioBasico", loc.getPrecioBasico());
                locData.put("precioDelux", loc.getPrecioDelux());
                locData.put("precioMultiple", loc.getPrecioMultiple());
                locData.put("precioTemporada", loc.getPrecioTemporada());
                localidadesData.add(locData);
                

                String localidadKey = evento.getNombreEvento() + "_" + loc.getNombreLocalidad();
                mapaLocalidades.put(localidadKey, loc);
            }
            eventoData.put("localidades", localidadesData);
            

            List<String> tiquetesIds = new ArrayList<>();
            for (Tiquete t : evento.getTiquetesVendidos()) {
                tiquetesIds.add(t.getId());
            }
            eventoData.put("tiquetesVendidosIds", tiquetesIds);
            
            eventosData.add(eventoData);
        }
        
        String json = gson.toJson(eventosData);
        escribirArchivo(ARCHIVO_EVENTOS, json);
    }
    
    private void guardarTiquetes(List<Tiquete> tiquetes) throws IOException {
        List<Map<String, Object>> tiquetesData = new ArrayList<>();
        
        for (Tiquete tiquete : tiquetes) {
            Map<String, Object> tiqueteData = new HashMap<>();
            tiqueteData.put("id", tiquete.getId());
            tiqueteData.put("precio", tiquete.getPrecio());
            tiqueteData.put("usado", tiquete.isUsado());
            tiqueteData.put("transferible", tiquete.isTransferible());
            

            String className = tiquete.getClass().getSimpleName();
            tiqueteData.put("tipo", className);
            

            if (tiquete.getUsuario() != null) {
                tiqueteData.put("usuarioLogin", tiquete.getUsuario().getLogin());
            }
            

            if (tiquete.getLocalidad() != null) {
                tiqueteData.put("localidadNombre", tiquete.getLocalidad().getNombreLocalidad());
            }
            

            if (tiquete.getSilla() != null) {
                tiqueteData.put("silla", tiquete.getSilla());
            }
            

            List<String> eventosNombres = new ArrayList<>();
            if (tiquete.getEventos() != null) {
                for (Evento e : tiquete.getEventos()) {
                    eventosNombres.add(e.getNombreEvento());
                }
            }
            tiqueteData.put("eventosNombres", eventosNombres);
            

            if (tiquete instanceof tiquetes.TiqueteMultiple) {
                tiquetes.TiqueteMultiple tm = (tiquetes.TiqueteMultiple) tiquete;
                tiqueteData.put("numeroDeTiquetes", tm.getNumeroDeTiquetes());
            }
            
            tiquetesData.add(tiqueteData);
        }
        
        String json = gson.toJson(tiquetesData);
        escribirArchivo(ARCHIVO_TIQUETES, json);
    }
    

    
    private List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        String contenido = leerArchivo(ARCHIVO_USUARIOS);
        if (contenido == null || contenido.trim().isEmpty()) {
            return usuarios;
        }
        
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> usuariosData = gson.fromJson(contenido, listType);
        
        for (Map<String, Object> userData : usuariosData) {
            String login = (String) userData.get("login");
            String password = (String) userData.get("password");
            double saldo = ((Number) userData.get("saldo")).doubleValue();
            String tipo = (String) userData.get("tipo");
            
            Usuario usuario;
            
            switch (tipo) {
                case "ADMINISTRADOR":
                    Administrador admin = new Administrador(login, password, saldo);
                    if (userData.containsKey("porcentajesServicio")) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> porcentajes = (Map<String, Object>) userData.get("porcentajesServicio");
                        Map<String, Double> porcentajesConvertidos = new HashMap<>();
                        for (Map.Entry<String, Object> entry : porcentajes.entrySet()) {
                            porcentajesConvertidos.put(entry.getKey(), ((Number) entry.getValue()).doubleValue());
                        }
                        admin.setPorcentajeServicioPorTipo(porcentajesConvertidos);
                    }
                    usuario = admin;
                    break;
                    
                case "ORGANIZADOR":
                    usuario = new Organizador(login, password, saldo);
                    break;
                    
                case "CLIENTE":
                    usuario = new Cliente(login, password, saldo);
                    break;
                    
                default:
                    usuario = new Usuario(login, password, saldo);
            }
            
            mapaUsuarios.put(login, usuario);
            usuarios.add(usuario);
        }
        
        return usuarios;
    }
    
    private List<Venue> cargarVenues() throws IOException {
        List<Venue> venues = new ArrayList<>();
        String contenido = leerArchivo(ARCHIVO_VENUES);
        if (contenido == null || contenido.trim().isEmpty()) {
            return venues;
        }
        
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> venuesData = gson.fromJson(contenido, listType);
        
        for (Map<String, Object> venueData : venuesData) {
            String nombre = (String) venueData.get("nombre");
            String tipo = (String) venueData.get("tipoVenue");
            int capacidad = ((Number) venueData.get("capacidad")).intValue();
            String ubicacion = (String) venueData.get("ubicacion");
            String restricciones = (String) venueData.get("restriccionesDeUso");
            
            Venue venue = new Venue(nombre, tipo, capacidad, ubicacion, restricciones);
            venue.setAprobado((Boolean) venueData.get("aprobado"));
            
            if (venueData.containsKey("proximoEvento")) {
                venue.setProximoEvento((String) venueData.get("proximoEvento"));
            }
            if (venueData.containsKey("fechaProximoEvento")) {
                long timestamp = ((Number) venueData.get("fechaProximoEvento")).longValue();
                venue.setFechaProximoEvento(new Date(timestamp));
            }
            
            mapaVenues.put(nombre, venue);
            venues.add(venue);
        }
        
        return venues;
    }
    
    private List<Evento> cargarEventos() throws IOException {
        List<Evento> eventos = new ArrayList<>();
        String contenido = leerArchivo(ARCHIVO_EVENTOS);
        if (contenido == null || contenido.trim().isEmpty()) {
            return eventos;
        }
        
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> eventosData = gson.fromJson(contenido, listType);
        
        for (Map<String, Object> eventoData : eventosData) {
            String nombreEvento = (String) eventoData.get("nombreEvento");
            

            String fechaStr = (String) eventoData.get("fecha");
            Date fecha = gson.fromJson("\"" + fechaStr + "\"", Date.class);
            
            int cantBasicos = ((Number) eventoData.get("cantidadTiquetesBasicos")).intValue();
            int cantMultiples = ((Number) eventoData.get("cantidadTiquetesMultiples")).intValue();
            int cantDeluxe = ((Number) eventoData.get("cantidadTiquetesDelux")).intValue();
            double cargo = ((Number) eventoData.get("cargoPorcentual")).doubleValue();
            double cuota = ((Number) eventoData.get("cuotaAdicional")).doubleValue();
            int maxBasicos = ((Number) eventoData.get("cantidadMaxTiquetesBasicos")).intValue();
            int maxDeluxe = ((Number) eventoData.get("cantidadMaxDeluxe")).intValue();
            int maxMultiples = ((Number) eventoData.get("cantidadMaxMultiples")).intValue();
            

            String orgLogin = (String) eventoData.get("organizadorLogin");
            String venueNombre = (String) eventoData.get("venueNombre");
            String adminLogin = (String) eventoData.get("administradorLogin");
            
            Organizador organizador = (Organizador) mapaUsuarios.get(orgLogin);
            Venue venue = mapaVenues.get(venueNombre);
            Administrador admin = (Administrador) mapaUsuarios.get(adminLogin);
            
            if (organizador == null || venue == null || admin == null) {
                System.err.println("Error: Referencias faltantes para evento " + nombreEvento);
                continue;
            }
            

            Evento evento = new Evento(
                nombreEvento, fecha,
                cantBasicos, cantMultiples, cantDeluxe,
                cargo, cuota,
                maxBasicos, maxDeluxe, maxMultiples,
                organizador, venue, admin
            );
            
            evento.setHora((String) eventoData.get("hora"));
            evento.setCancelado((Boolean) eventoData.get("cancelado"));
            evento.setMaxTiquetesPorTransaccion(((Number) eventoData.get("maxTiquetesPorTransaccion")).intValue());
            evento.setTipoEvento((String) eventoData.get("tipoEvento"));
            

            if (eventoData.containsKey("localidades")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> localidadesData = (List<Map<String, Object>>) eventoData.get("localidades");
                
                for (Map<String, Object> locData : localidadesData) {
                    Localidad localidad = new Localidad();
                    localidad.setNombreLocalidad((String) locData.get("nombreLocalidad"));
                    localidad.setTieneNumeracion((Boolean) locData.get("tieneNumeracion"));
                    localidad.setDescuento(((Number) locData.get("descuento")).doubleValue());
                    localidad.setPrecioBasico(((Number) locData.get("precioBasico")).doubleValue());
                    localidad.setPrecioDelux(((Number) locData.get("precioDelux")).doubleValue());
                    localidad.setPrecioMultiple(((Number) locData.get("precioMultiple")).doubleValue());
                    localidad.setPrecioTemporada(((Number) locData.get("precioTemporada")).doubleValue());
                    evento.agregarLocalidad(localidad);

                    String localidadKey = nombreEvento + "_" + localidad.getNombreLocalidad();
                    mapaLocalidades.put(localidadKey, localidad);
                }
            }
            
            mapaEventos.put(nombreEvento, evento);
            eventos.add(evento);
        }
        
        return eventos;
    }
    
    private List<Tiquete> cargarTiquetes() throws IOException {
        List<Tiquete> tiquetes = new ArrayList<>();
        String contenido = leerArchivo(ARCHIVO_TIQUETES);
        if (contenido == null || contenido.trim().isEmpty()) {
            return tiquetes;
        }
        
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> tiquetesData = gson.fromJson(contenido, listType);
        
        for (Map<String, Object> tiqueteData : tiquetesData) {
            String id = (String) tiqueteData.get("id");
            String tipo = (String) tiqueteData.get("tipo");
            boolean usado = (Boolean) tiqueteData.get("usado");
            boolean transferible = (Boolean) tiqueteData.get("transferible");
            double precio = tiqueteData.containsKey("precio") ? 
                ((Number) tiqueteData.get("precio")).doubleValue() : 0.0;
            

            String usuarioLogin = (String) tiqueteData.get("usuarioLogin");
            Usuario usuario = mapaUsuarios.get(usuarioLogin);
            
            @SuppressWarnings("unchecked")
            List<String> eventosNombres = (List<String>) tiqueteData.get("eventosNombres");
            List<Evento> eventos = new ArrayList<>();
            if (eventosNombres != null && !eventosNombres.isEmpty()) {
                for (String nombreEvento : eventosNombres) {
                    Evento evento = mapaEventos.get(nombreEvento);
                    if (evento != null) {
                        eventos.add(evento);
                    }
                }
            }
            
            if (eventos.isEmpty()) {
                System.err.println("Error: Tiquete " + id + " no tiene eventos válidos");
                continue;
            }
            
            Evento primerEvento = eventos.get(0);
            

            Localidad localidad = null;
            if (tiqueteData.containsKey("localidadNombre")) {
                String localidadNombre = (String) tiqueteData.get("localidadNombre");
                String localidadKey = primerEvento.getNombreEvento() + "_" + localidadNombre;
                localidad = mapaLocalidades.get(localidadKey);
            }
            

            String silla = tiqueteData.containsKey("silla") ? 
                (String) tiqueteData.get("silla") : null;
            

            Tiquete tiquete = null;
            
            try {
                if (tipo.equals("TiqueteBasico")) {
                    
                    tiquete = new tiquetes.TiqueteBasico(id, transferible, silla, localidad, primerEvento, precio, usuario);
                    
                } else if (tipo.equals("TiqueteMultiple")) {
                    
                    int numeroDeTiquetes = tiqueteData.containsKey("numeroDeTiquetes") ? 
                        ((Number) tiqueteData.get("numeroDeTiquetes")).intValue() : 1;
                    tiquete = new tiquetes.TiqueteMultiple(id, transferible, silla, localidad, primerEvento, precio, numeroDeTiquetes, usuario);
                    
                } else if (tipo.equals("TiqueteTemporada")) {
                   
                    tiquete = new tiquetes.TiqueteTemporada(id, transferible, silla, localidad, primerEvento, precio, usuario);
                    
                    
                    if (eventos.size() > 1) {
                        for (int i = 1; i < eventos.size(); i++) {
                            tiquete.agregarEvento(eventos.get(i));
                        }
                    }
                }
                
                if (tiquete != null) {
                    tiquete.setUsado(usado);
                    
                    mapaTiquetes.put(id, tiquete);
                    tiquetes.add(tiquete);
                }
            } catch (Exception e) {
                System.err.println("Error al crear tiquete " + id + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return tiquetes;
    }
    
   
    
    private void reconstruirRelaciones(List<Usuario> usuarios, List<Evento> eventos, 
                                      List<Venue> venues, List<Tiquete> tiquetes) {

        for (Usuario usuario : usuarios) {
            usuario.getTiquetesActivos().clear();
            for (Tiquete tiquete : tiquetes) {
                if (tiquete.getUsuario() != null && 
                    tiquete.getUsuario().getLogin().equals(usuario.getLogin())) {
                    usuario.agregarTiquete(tiquete);
                }
            }
        }
        

        for (Usuario usuario : usuarios) {
            if (usuario instanceof Organizador) {
                Organizador org = (Organizador) usuario;
                List<Evento> eventosOrg = new ArrayList<>();
                for (Evento evento : eventos) {
                    if (evento.getOrganizador() != null && 
                        evento.getOrganizador().getLogin().equals(org.getLogin())) {
                        eventosOrg.add(evento);
                    }
                }
                org.setEventosActivos(eventosOrg);
            }
        }
        
     
        for (Evento evento : eventos) {
            evento.getTiquetesVendidos().clear();
            for (Tiquete tiquete : tiquetes) {
                if (tiquete.getEventos() != null) {
                    for (Evento e : tiquete.getEventos()) {
                        if (e.getNombreEvento().equals(evento.getNombreEvento())) {
                            evento.getTiquetesVendidos().add(tiquete);
                            break;
                        }
                    }
                }
            }
        }
    }
    

    
    private void escribirArchivo(String nombreArchivo, String contenido) throws IOException {
        Path path = Paths.get(CARPETA_DATOS, nombreArchivo);
        Files.write(path, contenido.getBytes("UTF-8"));
    }
    
    private String leerArchivo(String nombreArchivo) throws IOException {
        Path path = Paths.get(CARPETA_DATOS, nombreArchivo);
        if (!Files.exists(path)) {
            return null;
        }
        return new String(Files.readAllBytes(path), "UTF-8");
    }
    

    public boolean existenDatos() {
        Path path = Paths.get(CARPETA_DATOS, ARCHIVO_USUARIOS);
        return Files.exists(path);
    }
    

    public static class DatosSistema {
        public List<Usuario> usuarios;
        public List<Evento> eventos;
        public List<Venue> venues;
        public List<Tiquete> tiquetes;
        
        public DatosSistema() {
            this.usuarios = new ArrayList<>();
            this.eventos = new ArrayList<>();
            this.venues = new ArrayList<>();
            this.tiquetes = new ArrayList<>();
        }
        
        public DatosSistema(List<Usuario> usuarios, List<Evento> eventos, 
                          List<Venue> venues, List<Tiquete> tiquetes) {
            this.usuarios = usuarios;
            this.eventos = eventos;
            this.venues = venues;
            this.tiquetes = tiquetes;
        }
    }
}