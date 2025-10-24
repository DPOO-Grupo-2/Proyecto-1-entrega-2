package interfaz;

import persistencia.GestorPersistencia;
import persistencia.GestorPersistencia.DatosSistema;
import usuarios.*;
import evento.*;
import tiquetes.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    
    private static GestorPersistencia persistencia;
    private static List<Usuario> usuarios;
    private static List<Evento> eventos;
    private static List<Venue> venues;
    private static List<Tiquete> tiquetes;
    private static Scanner scanner;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {

        persistencia = new GestorPersistencia();
        persistencia.inicializar();
        scanner = new Scanner(System.in);
        

        if (persistencia.existenDatos()) {
            cargarDatos();
        } else {
            inicializarNuevo();
        }
        
        System.out.println("=== BIENVENIDO A BOLETAMASTER ===");
        System.out.println("Usuarios registrados: " + usuarios.size());
        System.out.println("Eventos disponibles: " + eventos.size());
        System.out.println();
        

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    login();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    listarEventos();
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
        

        guardarDatos();
        System.out.println("\n¡Hasta pronto!");
        scanner.close();
    }
    
    private static void cargarDatos() {
        DatosSistema datos = persistencia.cargarTodo();
        usuarios = datos.usuarios;
        eventos = datos.eventos;
        venues = datos.venues;
        tiquetes = datos.tiquetes;
        System.out.println("Datos cargados del archivo");
    }
    
    private static void guardarDatos() {
        persistencia.guardarTodo(usuarios, eventos, venues, tiquetes);
    }
    
    private static void inicializarNuevo() {
        usuarios = new ArrayList<>();
        eventos = new ArrayList<>();
        venues = new ArrayList<>();
        tiquetes = new ArrayList<>();
        

        Administrador admin = new Administrador("admin", "admin123", 0);
        usuarios.add(admin);
        
        guardarDatos();
        System.out.println("Sistema inicializado (sin datos previos)");
    }
    
    private static Usuario buscarUsuario(String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }
    
    private static Evento buscarEvento(String nombre) {
        for (Evento e : eventos) {
            if (e.getNombreEvento().equals(nombre)) {
                return e;
            }
        }
        return null;
    }
    
    private static Venue buscarVenue(String nombre) {
        for (Venue v : venues) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }
    
    private static Administrador obtenerAdministrador() {
        for (Usuario u : usuarios) {
            if (u instanceof Administrador) {
                return (Administrador) u;
            }
        }
        return null;
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Ver eventos");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }
    
    private static void login() {
        System.out.print("Usuario: ");
        String login = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        Usuario usuario = buscarUsuario(login);
        if (usuario != null && usuario.autenticar(password)) {
            System.out.println("¡Bienvenido, " + login + "!");
            
            if (usuario instanceof Administrador) {
                menuAdministrador((Administrador) usuario);
            } else if (usuario instanceof Organizador) {
                menuOrganizador((Organizador) usuario);
            } else if (usuario instanceof Cliente) {
                menuCliente((Cliente) usuario);
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }
    }
    
    private static void registrarCliente() {
        System.out.print("Usuario: ");
        String login = scanner.nextLine();
        

        if (buscarUsuario(login) != null) {
            System.out.println("Error: el usuario ya existe");
            return;
        }
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = leerDouble();
        scanner.nextLine(); 
        
        Cliente cliente = new Cliente(login, password, saldo);
        usuarios.add(cliente);
        guardarDatos(); 
        System.out.println("¡Registro exitoso!");
    }
    
    private static void listarEventos() {
        System.out.println("\n--- EVENTOS DISPONIBLES ---");
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos disponibles");
            return;
        }
        
        for (Evento e : eventos) {
            if (!e.getCancelado()) {
                System.out.println("- " + e.getNombreEvento() + 
                                 " (" + sdf.format(e.getFecha()) + ") - Venue: " + e.getVenue().getNombre());
            }
        }
    }
    
    // ==================== MENÚ CLIENTE ====================
    
    private static void menuCliente(Cliente cliente) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("Saldo actual: $" + cliente.getSaldo());
            System.out.println("1. Comprar tiquete");
            System.out.println("2. Ver mis tiquetes");
            System.out.println("3. Transferir tiquete");
            System.out.println("4. Cancelar tiquete");
            System.out.println("5. Ver historial de transacciones");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    comprarTiquete(cliente);
                    break;
                case 2:
                    verMisTiquetes(cliente);
                    break;
                case 3:
                    transferirTiquete(cliente);
                    break;
                case 4:
                    cancelarTiquete(cliente);
                    break;
                case 5:
                    verHistorialTransacciones(cliente);
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    
    private static void comprarTiquete(Cliente cliente) {
        listarEventos();
        
        if (eventos.isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = buscarEvento(nombreEvento);
        if (evento == null || evento.getCancelado()) {
            System.out.println("Evento no encontrado o cancelado");
            return;
        }
        
        System.out.println("\n--- LOCALIDADES DISPONIBLES ---");
        List<Localidad> localidades = new ArrayList<>(evento.getLocalidades());
        if (localidades.isEmpty()) {
            System.out.println("No hay localidades disponibles");
            return;
        }
        
        for (int i = 0; i < localidades.size(); i++) {
            Localidad loc = localidades.get(i);
            System.out.println((i + 1) + ". " + loc.getNombreLocalidad() + " - Precio básico: $" + loc.getPrecioBasico() + 
                             " (Disponibles: " + "N/A" + ")");
        }
        
        System.out.print("Seleccione localidad (número): ");
        int indiceLocalidad = leerEntero() - 1;
        
        if (indiceLocalidad < 0 || indiceLocalidad >= localidades.size()) {
            System.out.println("Localidad inválida");
            scanner.nextLine();
            return;
        }
        
        Localidad localidad = localidades.get(indiceLocalidad);
        
        System.out.print("Cantidad de tiquetes: ");
        int cantidad = leerEntero();
        scanner.nextLine();
        
        System.out.println("\n--- TIPO DE TIQUETE ---");
        System.out.println("1. Básico");
        System.out.println("2. Múltiple");
        System.out.println("3. Deluxe");
        System.out.println("4. Temporada");
        System.out.print("Opción: ");
        int tipoOpcion = leerEntero();
        scanner.nextLine();
        
        String tipoTiquete;
        switch (tipoOpcion) {
            case 1:
                tipoTiquete = "basico";
                break;
            case 2:
                tipoTiquete = "multiple";
                break;
            case 3:
                tipoTiquete = "deluxe";
                break;
            case 4:
                tipoTiquete = "temporada";
                break;
            default:
                System.out.println("Tipo inválido");
                return;
        }
        
        cliente.comprarTiquete(tipoTiquete, cliente, evento, localidad, cantidad);
        guardarDatos(); 
    }
    
    private static void verMisTiquetes(Cliente cliente) {
        System.out.println("\n--- MIS TIQUETES ---");
        List<Tiquete> tiquetes = cliente.getTiquetesActivos();
        
        if (tiquetes.isEmpty()) {
            System.out.println("No tienes tiquetes");
            return;
        }
        
        for (Tiquete t : tiquetes) {
            System.out.println("ID: " + t.getId() + " | Precio: $" + t.getPrecio() + 
                             " | Usado: " + (t.isUsado() ? "Sí" : "No"));
        }
    }
    
    private static void transferirTiquete(Cliente cliente) {
        verMisTiquetes(cliente);
        
        if (cliente.getTiquetesActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nID del tiquete a transferir: ");
        String idTiquete = scanner.nextLine();
        
        Tiquete tiquete = null;
        for (Tiquete t : cliente.getTiquetesActivos()) {
            if (t.getId().equals(idTiquete)) {
                tiquete = t;
                break;
            }
        }
        
        if (tiquete == null) {
            System.out.println("Tiquete no encontrado");
            return;
        }
        
        System.out.print("Usuario receptor: ");
        String loginReceptor = scanner.nextLine();
        
        Usuario receptor = buscarUsuario(loginReceptor);
        if (receptor == null) {
            System.out.println("Usuario receptor no encontrado");
            return;
        }
        
        System.out.print("Confirme su contraseña: ");
        String password = scanner.nextLine();
        
        cliente.transferirTiquete(tiquete, receptor, password);
        guardarDatos(); 
    }
    
    private static void cancelarTiquete(Cliente cliente) {
        verMisTiquetes(cliente);
        
        if (cliente.getTiquetesActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nID del tiquete a cancelar: ");
        String idTiquete = scanner.nextLine();
        
        System.out.print("Nombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        cliente.cancelarTiquete(idTiquete, nombreEvento);
        guardarDatos();
    }
    
    private static void verHistorialTransacciones(Cliente cliente) {
        System.out.println("\n--- HISTORIAL DE TRANSACCIONES ---");
        List<String> historial = cliente.getHistorialTransacciones();
        
        if (historial.isEmpty()) {
            System.out.println("No hay transacciones registradas");
            return;
        }
        
        for (String transaccion : historial) {
            System.out.println("- " + transaccion);
        }
    }
    
    
    private static void menuOrganizador(Organizador organizador) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ ORGANIZADOR ---");
            System.out.println("Saldo actual: $" + organizador.getSaldo());
            System.out.println("1. Crear evento");
            System.out.println("2. Ver mis eventos");
            System.out.println("3. Configurar localidades");
            System.out.println("4. Crear oferta");
            System.out.println("5. Consultar ganancias");
            System.out.println("6. Solicitar cancelación de evento");
            System.out.println("7. Sugerir venue");
            System.out.println("8. Comprar tiquetes como cortesía");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    crearEvento(organizador);
                    break;
                case 2:
                    verMisEventos(organizador);
                    break;
                case 3:
                    configurarLocalidades(organizador);
                    break;
                case 4:
                    crearOferta(organizador);
                    break;
                case 5:
                    consultarGananciasOrganizador(organizador);
                    break;
                case 6:
                    solicitarCancelacion(organizador);
                    break;
                case 7:
                    sugerirVenue(organizador);
                    break;
                case 8:
                    comprarCortesia(organizador);
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    
    private static void crearEvento(Organizador organizador) {
        System.out.print("Nombre del evento: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        Date fecha;
        try {
            fecha = sdf.parse(fechaStr);
        } catch (Exception e) {
            System.out.println("Fecha inválida");
            return;
        }
        
        System.out.print("Cantidad de tiquetes básicos: ");
        int cantBasicos = leerEntero();
        
        System.out.print("Cantidad de tiquetes múltiples: ");
        int cantMultiples = leerEntero();
        
        System.out.print("Cantidad de tiquetes deluxe: ");
        int cantDeluxe = leerEntero();
        
        System.out.print("Cargo porcentual: ");
        double cargoPorcentual = leerDouble();
        
        System.out.print("Cuota adicional: ");
        double cuotaAdicional = leerDouble();
        
        System.out.print("Máximo de tiquetes básicos por transacción: ");
        int maxBasicos = leerEntero();
        
        System.out.print("Máximo de tiquetes múltiples por transacción: ");
        int maxMultiples = leerEntero();
        
        System.out.print("Máximo de tiquetes deluxe por transacción: ");
        int maxDeluxe = leerEntero();
        scanner.nextLine();
        

        System.out.println("\n--- VENUES DISPONIBLES ---");
        if (venues.isEmpty()) {
            System.out.println("No hay venues disponibles");
            return;
        }
        
        int contador = 0;
        for (int i = 0; i < venues.size(); i++) {
            Venue v = venues.get(i);
            if (v.isAprobado()) {
                System.out.println((contador + 1) + ". " + v.getNombre() + " - Capacidad: " + v.getCapacidad());
                contador++;
            }
        }
        
        if (contador == 0) {
            System.out.println("No hay venues aprobados");
            return;
        }
        
        System.out.print("Seleccione venue (número): ");
        int indiceVenue = leerEntero() - 1;
        scanner.nextLine();
        
        if (indiceVenue < 0 || indiceVenue >= venues.size()) {
            System.out.println("Venue inválido");
            return;
        }
        
        Venue venue = venues.get(indiceVenue);
        
        Administrador admin = obtenerAdministrador();
        if (admin == null) {
            System.out.println("Error: no hay administrador en el sistema");
            return;
        }
        
        Evento evento = organizador.crearEvento(nombre, fecha, cantBasicos, cantMultiples, cantDeluxe,
                                               cargoPorcentual, cuotaAdicional, maxBasicos, maxDeluxe,
                                               maxMultiples, venue, admin);
        
        if (evento != null) {
            eventos.add(evento);
            guardarDatos(); 
            System.out.println("Evento creado exitosamente");
        }
    }
    
    private static void verMisEventos(Organizador organizador) {
        System.out.println("\n--- MIS EVENTOS ---");
        List<Evento> eventosOrg = organizador.getEventosActivos();
        
        if (eventosOrg.isEmpty()) {
            System.out.println("No tienes eventos activos");
            return;
        }
        
        for (Evento e : eventosOrg) {
            System.out.println("- " + e.getNombreEvento() + " (" + sdf.format(e.getFecha()) + ")");
            System.out.println("  Venue: " + e.getVenue().getNombre());
            System.out.println("  Tiquetes vendidos: " + e.getTiquetesVendidos().size());
            System.out.println("  % Ventas: " + String.format("%.2f", organizador.calcularPorcentajeVentas(e)) + "%");
        }
    }
    
    private static void configurarLocalidades(Organizador organizador) {
        verMisEventos(organizador);
        
        if (organizador.getEventosActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = null;
        for (Evento e : organizador.getEventosActivos()) {
            if (e.getNombreEvento().equals(nombreEvento)) {
                evento = e;
                break;
            }
        }
        
        if (evento == null) {
            System.out.println("Evento no encontrado");
            return;
        }
        
        System.out.print("Nombre de la localidad: ");
        String nombreLocalidad = scanner.nextLine();
        
        System.out.print("Precio básico: ");
        double precioBasico = leerDouble();
        
        System.out.print("Precio múltiple: ");
        double precioMultiple = leerDouble();
        
        System.out.print("Precio deluxe: ");
        double precioDeluxe = leerDouble();
        
        System.out.print("Precio temporada: ");
        double precioTemporada = leerDouble();
        
        
        System.out.print("¿Es numerada? (s/n): ");
        scanner.nextLine();
        String numeradaStr = scanner.nextLine();
        boolean numerada = numeradaStr.equalsIgnoreCase("s");
        
        Localidad localidad = new Localidad();
        localidad.setNombreLocalidad(nombreLocalidad);
        localidad.setPrecioBasico(precioBasico);
        localidad.setPrecioMultiple(precioMultiple);
        localidad.setPrecioDelux(precioDeluxe);
        localidad.setPrecioTemporada(precioTemporada);
        
        localidad.setTieneNumeracion(numerada);
        
        organizador.asignarLocalidad(evento, localidad);
        guardarDatos(); 
        
        System.out.println("Localidad agregada exitosamente");
    }
    
    private static void crearOferta(Organizador organizador) {
        verMisEventos(organizador);
        
        if (organizador.getEventosActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = null;
        for (Evento e : organizador.getEventosActivos()) {
            if (e.getNombreEvento().equals(nombreEvento)) {
                evento = e;
                break;
            }
        }
        
        if (evento == null) {
            System.out.println("Evento no encontrado");
            return;
        }
        

        System.out.println("\n--- LOCALIDADES ---");
        List<Localidad> localidades = new ArrayList<>(evento.getLocalidades());
        for (int i = 0; i < localidades.size(); i++) {
            System.out.println((i + 1) + ". " + localidades.get(i).getNombreLocalidad());
        }
        
        System.out.print("Seleccione localidad (número): ");
        int indiceLocalidad = leerEntero() - 1;
        scanner.nextLine();
        
        if (indiceLocalidad < 0 || indiceLocalidad >= localidades.size()) {
            System.out.println("Localidad inválida");
            return;
        }
        
        Localidad localidad = localidades.get(indiceLocalidad);
        
        System.out.print("Descuento (%): ");
        double descuento = leerDouble();
        scanner.nextLine();
        
        System.out.print("Fecha de inicio (dd/MM/yyyy): ");
        String fechaInicioStr = scanner.nextLine();
        
        System.out.print("Fecha de fin (dd/MM/yyyy): ");
        String fechaFinStr = scanner.nextLine();
        
        try {
            Date fechaInicio = sdf.parse(fechaInicioStr);
            Date fechaFin = sdf.parse(fechaFinStr);
            
            organizador.crearOferta(evento, localidad, descuento, fechaInicio, fechaFin);
            guardarDatos();
            System.out.println("Oferta creada exitosamente");
        } catch (Exception e) {
            System.out.println("Error en las fechas");
        }
    }
    
    private static void consultarGananciasOrganizador(Organizador organizador) {
        System.out.println("\n--- CONSULTA DE GANANCIAS ---");
        System.out.println("1. Ganancias totales");
        System.out.println("2. Ganancias por evento");
        System.out.println("3. Ganancias por localidad");
        System.out.print("Opción: ");
        
        int opcion = leerEntero();
        scanner.nextLine();
        
        switch (opcion) {
            case 1:
                double total = organizador.consultarGananciasTotales();
                System.out.println("Ganancias totales: $" + String.format("%.2f", total));
                break;
                
            case 2:
                verMisEventos(organizador);
                System.out.print("\nNombre del evento: ");
                String nombreEvento = scanner.nextLine();
                
                Evento evento = null;
                for (Evento e : organizador.getEventosActivos()) {
                    if (e.getNombreEvento().equals(nombreEvento)) {
                        evento = e;
                        break;
                    }
                }
                
                if (evento != null) {
                    double ganancia = organizador.consultarGananciasPorEvento(evento);
                    System.out.println("Ganancias del evento: $" + String.format("%.2f", ganancia));
                } else {
                    System.out.println("Evento no encontrado");
                }
                break;
                
            case 3:
                verMisEventos(organizador);
                System.out.print("\nNombre del evento: ");
                nombreEvento = scanner.nextLine();
                
                evento = null;
                for (Evento e : organizador.getEventosActivos()) {
                    if (e.getNombreEvento().equals(nombreEvento)) {
                        evento = e;
                        break;
                    }
                }
                
                if (evento != null) {
                    List<Localidad> localidades = new ArrayList<>(evento.getLocalidades());
                    for (int i = 0; i < localidades.size(); i++) {
                        System.out.println((i + 1) + ". " + localidades.get(i).getNombreLocalidad());
                    }
                    
                    System.out.print("Seleccione localidad (número): ");
                    int indiceLocalidad = leerEntero() - 1;
                    scanner.nextLine();
                    
                    if (indiceLocalidad >= 0 && indiceLocalidad < localidades.size()) {
                        Localidad localidad = localidades.get(indiceLocalidad);
                        double ganancia = organizador.consultarGananciasPorLocalidad(evento, localidad);
                        System.out.println("Ganancias de la localidad: $" + String.format("%.2f", ganancia));
                    } else {
                        System.out.println("Localidad inválida");
                    }
                } else {
                    System.out.println("Evento no encontrado");
                }
                break;
                
            default:
                System.out.println("Opción inválida");
        }
    }
    
    private static void solicitarCancelacion(Organizador organizador) {
        verMisEventos(organizador);
        
        if (organizador.getEventosActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = null;
        for (Evento e : organizador.getEventosActivos()) {
            if (e.getNombreEvento().equals(nombreEvento)) {
                evento = e;
                break;
            }
        }
        
        if (evento == null) {
            System.out.println("Evento no encontrado");
            return;
        }
        
        System.out.print("Motivo de la cancelación: ");
        String motivo = scanner.nextLine();
        
        if (organizador.solicitarCancelacion(evento, motivo)) {
            guardarDatos(); 
            System.out.println("Solicitud enviada al administrador");
        } else {
            System.out.println("Error al solicitar la cancelación");
        }
    }
    
    private static void sugerirVenue(Organizador organizador) {
        System.out.print("Nombre del venue: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Tipo (MUSICAL/CULTURAL/DEPORTIVO/GENERAL): ");
        String tipo = scanner.nextLine();
        
        System.out.print("Capacidad: ");
        int capacidad = leerEntero();
        scanner.nextLine();
        
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        
        System.out.print("Restricciones de uso: ");
        String restricciones = scanner.nextLine();
        
        Venue venue = new Venue(nombre, tipo, capacidad, ubicacion, restricciones);
        organizador.sugerirVenue(venue);
        venues.add(venue);
        guardarDatos(); 
        
        System.out.println("Venue sugerido. Pendiente de aprobación del administrador");
    }
    
    private static void comprarCortesia(Organizador organizador) {
        verMisEventos(organizador);
        
        if (organizador.getEventosActivos().isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = null;
        for (Evento e : organizador.getEventosActivos()) {
            if (e.getNombreEvento().equals(nombreEvento)) {
                evento = e;
                break;
            }
        }
        
        if (evento == null) {
            System.out.println("Evento no encontrado");
            return;
        }
        

        System.out.println("\n--- LOCALIDADES ---");
        List<Localidad> localidades = new ArrayList<>(evento.getLocalidades());
        for (int i = 0; i < localidades.size(); i++) {
            System.out.println((i + 1) + ". " + localidades.get(i).getNombreLocalidad());
        }
        
        System.out.print("Seleccione localidad (número): ");
        int indiceLocalidad = leerEntero() - 1;
        
        System.out.print("Cantidad: ");
        int cantidad = leerEntero();
        scanner.nextLine();
        
        if (indiceLocalidad >= 0 && indiceLocalidad < localidades.size()) {
            Localidad localidad = localidades.get(indiceLocalidad);
            organizador.comprarTiqueteComoCortesia(evento, localidad, cantidad);
            guardarDatos(); 
            System.out.println("Tiquetes de cortesía generados");
        } else {
            System.out.println("Localidad inválida");
        }
    }
    

    
    private static void menuAdministrador(Administrador admin) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Definir porcentaje de servicio");
            System.out.println("2. Aprobar venue");
            System.out.println("3. Ver solicitudes de cancelación");
            System.out.println("4. Cancelar evento");
            System.out.println("5. Consultar ganancias");
            System.out.println("6. Procesar reembolso");
            System.out.println("7. Ver todos los usuarios");
            System.out.println("8. Ver todos los eventos");
            System.out.println("9. Registrar organizador");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    definirPorcentajeServicio(admin);
                    break;
                case 2:
                    aprobarVenue(admin);
                    break;
                case 3:
                    verSolicitudesCancelacion(admin);
                    break;
                case 4:
                    cancelarEvento(admin);
                    break;
                case 5:
                    consultarGananciasAdmin(admin);
                    break;
                case 6:
                    procesarReembolso(admin);
                    break;
                case 7:
                    verTodosUsuarios();
                    break;
                case 8:
                    verTodosEventos();
                    break;
                case 9:
                    registrarOrganizador();
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    
    private static void definirPorcentajeServicio(Administrador admin) {
        System.out.println("\n--- TIPOS DE EVENTO ---");
        System.out.println("1. MUSICAL");
        System.out.println("2. CULTURAL");
        System.out.println("3. DEPORTIVO");
        System.out.println("4. RELIGIOSO");
        System.out.println("5. GENERAL");
        System.out.print("Seleccione tipo: ");
        
        int tipoOpcion = leerEntero();
        scanner.nextLine();
        
        String tipoEvento;
        switch (tipoOpcion) {
            case 1: tipoEvento = "MUSICAL"; break;
            case 2: tipoEvento = "CULTURAL"; break;
            case 3: tipoEvento = "DEPORTIVO"; break;
            case 4: tipoEvento = "RELIGIOSO"; break;
            case 5: tipoEvento = "GENERAL"; break;
            default:
                System.out.println("Tipo inválido");
                return;
        }
        
        System.out.print("Porcentaje de servicio: ");
        double porcentaje = leerDouble();
        scanner.nextLine();
        
        admin.definirPorcentajeServicio(tipoEvento, porcentaje);
        guardarDatos(); 
    }
    
    private static void aprobarVenue(Administrador admin) {
        System.out.println("\n--- VENUES PENDIENTES DE APROBACIÓN ---");
        List<Venue> pendientes = new ArrayList<>();
        
        for (Venue v : venues) {
            if (!v.isAprobado()) {
                pendientes.add(v);
                System.out.println("- " + v.getNombre() + " (Capacidad: " + v.getCapacidad() + ")");
            }
        }
        
        if (pendientes.isEmpty()) {
            System.out.println("No hay venues pendientes");
            return;
        }
        
        System.out.print("\nNombre del venue a aprobar: ");
        String nombreVenue = scanner.nextLine();
        
        Venue venue = buscarVenue(nombreVenue);
        if (venue != null) {
            admin.aprobarVenue(venue);
            guardarDatos(); 
            System.out.println("Venue aprobado exitosamente");
        } else {
            System.out.println("Venue no encontrado");
        }
    }
    
    private static void verSolicitudesCancelacion(Administrador admin) {
        System.out.println("\n--- SOLICITUDES DE CANCELACIÓN ---");
        List<Evento> solicitudes = admin.revisarSolicitudesCancelacion();
        
        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes");
            return;
        }
        
        for (Evento e : solicitudes) {
            System.out.println("- " + e.getNombreEvento() + " (" + sdf.format(e.getFecha()) + ")");
        }
        
        System.out.print("\n¿Desea autorizar alguna cancelación? (s/n): ");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Nombre del evento: ");
            String nombreEvento = scanner.nextLine();
            
            for (Evento e : solicitudes) {
                if (e.getNombreEvento().equals(nombreEvento)) {
                    admin.autorizarCancelacionOrganizador(e);
                    guardarDatos(); 
                    System.out.println("Cancelación autorizada");
                    return;
                }
            }
            System.out.println("Evento no encontrado");
        }
    }
    
    private static void cancelarEvento(Administrador admin) {
        listarEventos();
        
        if (eventos.isEmpty()) {
            return;
        }
        
        System.out.print("\nNombre del evento a cancelar: ");
        String nombreEvento = scanner.nextLine();
        
        Evento evento = buscarEvento(nombreEvento);
        if (evento != null) {
            admin.cancelarEvento(evento);
            guardarDatos(); 
            System.out.println("Evento cancelado y reembolsos procesados");
        } else {
            System.out.println("Evento no encontrado");
        }
    }
    
    private static void consultarGananciasAdmin(Administrador admin) {
        System.out.println("\n--- CONSULTA DE GANANCIAS ---");
        System.out.println("1. Ganancias generales");
        System.out.println("2. Ganancias por fecha");
        System.out.println("3. Ganancias por evento");
        System.out.println("4. Ganancias por organizador");
        System.out.print("Opción: ");
        
        int opcion = leerEntero();
        scanner.nextLine();
        
        switch (opcion) {
            case 1:
                double total = admin.consultarGananciasGenerales();
                System.out.println("Ganancias generales: $" + String.format("%.2f", total));
                break;
                
            case 2:
                System.out.print("Fecha (dd/MM/yyyy): ");
                String fechaStr = scanner.nextLine();
                try {
                    Date fecha = sdf.parse(fechaStr);
                    double ganancia = admin.consultarGananciasPorFecha(fecha);
                    System.out.println("Ganancias en la fecha: $" + String.format("%.2f", ganancia));
                } catch (Exception e) {
                    System.out.println("Fecha inválida");
                }
                break;
                
            case 3:
                listarEventos();
                System.out.print("\nNombre del evento: ");
                String nombreEvento = scanner.nextLine();
                
                Evento evento = buscarEvento(nombreEvento);
                if (evento != null) {
                    double ganancia = admin.consultarGananciasPorEvento(evento);
                    System.out.println("Ganancias del evento: $" + String.format("%.2f", ganancia));
                } else {
                    System.out.println("Evento no encontrado");
                }
                break;
                
            case 4:
                System.out.print("Login del organizador: ");
                String loginOrg = scanner.nextLine();
                
                Usuario usuario = buscarUsuario(loginOrg);
                if (usuario instanceof Organizador) {
                    double ganancia = admin.consultarGananciasPorOrganizador((Organizador) usuario);
                    System.out.println("Ganancias del organizador: $" + String.format("%.2f", ganancia));
                } else {
                    System.out.println("Organizador no encontrado");
                }
                break;
                
            default:
                System.out.println("Opción inválida");
        }
    }
    
    private static void procesarReembolso(Administrador admin) {
        System.out.print("Login del usuario: ");
        String loginUsuario = scanner.nextLine();
        
        Usuario usuario = buscarUsuario(loginUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return;
        }
        
        System.out.println("\n--- TIQUETES DEL USUARIO ---");
        List<Tiquete> tiquetes = usuario.getTiquetesActivos();
        
        if (tiquetes.isEmpty()) {
            System.out.println("El usuario no tiene tiquetes");
            return;
        }
        
        for (Tiquete t : tiquetes) {
            System.out.println("ID: " + t.getId() + " | Precio: $" + t.getPrecio());
        }
        
        System.out.print("\nID del tiquete a reembolsar: ");
        String idTiquete = scanner.nextLine();
        
        Tiquete tiquete = null;
        for (Tiquete t : tiquetes) {
            if (t.getId().equals(idTiquete)) {
                tiquete = t;
                break;
            }
        }
        
        if (tiquete != null) {
            if (admin.reembolso(usuario, tiquete)) {
                guardarDatos(); 
                System.out.println("Reembolso procesado exitosamente");
            } else {
                System.out.println("Error al procesar el reembolso");
            }
        } else {
            System.out.println("Tiquete no encontrado");
        }
    }
    
    private static void verTodosUsuarios() {
        System.out.println("\n--- TODOS LOS USUARIOS ---");
        
        for (Usuario u : usuarios) {
            String tipo = "";
            if (u instanceof Administrador) tipo = "Administrador";
            else if (u instanceof Organizador) tipo = "Organizador";
            else if (u instanceof Cliente) tipo = "Cliente";
            
            System.out.println("- " + u.getLogin() + " (" + tipo + ") - Saldo: $" + u.getSaldo());
        }
    }
    
    private static void verTodosEventos() {
        System.out.println("\n--- TODOS LOS EVENTOS ---");
        
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados");
            return;
        }
        
        for (Evento e : eventos) {
            String estado = e.getCancelado() ? "CANCELADO" : "ACTIVO";
            System.out.println("- " + e.getNombreEvento() + " (" + sdf.format(e.getFecha()) + ") - " + estado);
            System.out.println("  Venue: " + e.getVenue().getNombre());
            System.out.println("  Tiquetes vendidos: " + e.getTiquetesVendidos().size());
        }
    }
    
    private static void registrarOrganizador() {
        System.out.print("Usuario: ");
        String login = scanner.nextLine();
        
      
        if (buscarUsuario(login) != null) {
            System.out.println("Error: el usuario ya existe");
            return;
        }
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = leerDouble();
        scanner.nextLine();
        
        Organizador organizador = new Organizador(login, password, saldo);
        usuarios.add(organizador);
        guardarDatos(); 
        System.out.println("¡Organizador registrado exitosamente!");
    }
    
    
    private static int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor ingrese un número: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
    
    private static double leerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor ingrese un número válido: ");
            scanner.next();
        }
        double numero = scanner.nextDouble();
        return numero;
    }
}