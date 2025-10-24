package persistencia;

import usuarios.*;
import evento.*;
import tiquetes.*;
import persistencia.GestorPersistencia.DatosSistema;

import java.util.*;


public class PruebaPersistencia {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL SISTEMA DE PERSISTENCIA ===\n");
        
        PruebaPersistencia prueba = new PruebaPersistencia();
        prueba.ejecutarPrueba();
    }
    
    public void ejecutarPrueba() {

        System.out.println("1. Creando datos de prueba...");
        DatosSistema datosOriginales = crearDatosPrueba();
        System.out.println("   ✓ Datos creados exitosamente");
        mostrarResumen(datosOriginales, "ORIGINALES");
        
        System.out.println("\n2. Guardando datos...");
        GestorPersistencia gestor = new GestorPersistencia();
        gestor.inicializar();
        gestor.guardarTodo(
            datosOriginales.usuarios,
            datosOriginales.eventos,
            datosOriginales.venues,
            datosOriginales.tiquetes
        );
        System.out.println("   ✓ Datos guardados exitosamente");
        
        System.out.println("\n3. Cargando datos...");
        DatosSistema datosCargados = gestor.cargarTodo();
        System.out.println("   ✓ Datos cargados exitosamente");
        mostrarResumen(datosCargados, "CARGADOS");
        
        System.out.println("\n4. Verificando integridad de datos...");
        verificarIntegridad(datosOriginales, datosCargados);
        
        System.out.println("\n=== PRUEBA COMPLETADA EXITOSAMENTE ===");
    }
    
    private DatosSistema crearDatosPrueba() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Venue> venues = new ArrayList<>();
        List<Evento> eventos = new ArrayList<>();
        List<Tiquete> tiquetes = new ArrayList<>();
        
        Administrador admin = new Administrador("admin", "admin123", 0);
        usuarios.add(admin);
        
        Organizador org1 = new Organizador("organizador1", "org123", 1000000);
        usuarios.add(org1);
        
        Cliente cliente1 = new Cliente("cliente1", "cli123", 500000);
        Cliente cliente2 = new Cliente("cliente2", "cli456", 300000);
        usuarios.add(cliente1);
        usuarios.add(cliente2);
        
        Venue estadio = new Venue("Estadio El Campín", "Estadio", 40000, "Bogotá", "No fumar");
        estadio.setAprobado(true);
        venues.add(estadio);
        
        Venue teatro = new Venue("Teatro Colón", "Teatro", 1200, "Bogotá", "Vestimenta formal");
        teatro.setAprobado(true);
        venues.add(teatro);
        
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.NOVEMBER, 15);
        Date fechaConcierto = cal.getTime();
        
        Evento concierto = new Evento(
            "Shakira en Concierto",
            fechaConcierto,
            1000,  // basicos
            100,   // multiples
            50,    // deluxe
            0.15,  // cargo porcentual
            5000,  // cuota adicional
            1000,  // max basicos
            50,    // max deluxe
            100,   // max multiples
            org1,
            estadio,
            admin
        );
        concierto.setTipoEvento("MUSICAL");
        concierto.setHora("20:00");
        
        Localidad vip = new Localidad();
        vip.setNombreLocalidad("VIP");
        vip.setTieneNumeracion(true);
        vip.setPrecioBasico(300000);
        vip.setPrecioDelux(500000);
        vip.setPrecioMultiple(250000);
        vip.setPrecioTemporada(280000);
        vip.setDescuento(0);
        concierto.agregarLocalidad(vip);
        
        Localidad general = new Localidad();
        general.setNombreLocalidad("General");
        general.setTieneNumeracion(false);
        general.setPrecioBasico(100000);
        general.setPrecioDelux(180000);
        general.setPrecioMultiple(90000);
        general.setPrecioTemporada(95000);
        general.setDescuento(0);
        concierto.agregarLocalidad(general);
        
        eventos.add(concierto);
        
        TiqueteBasico tiq1 = new TiqueteBasico(
            "TIQ-001",
            true,
            "A-15",
            vip,
            concierto,
            vip.getPrecioBasico(),
            cliente1
        );
        tiquetes.add(tiq1);
        cliente1.agregarTiquete(tiq1);
        concierto.getTiquetesVendidos().add(tiq1);
        
        TiqueteBasico tiq2 = new TiqueteBasico(
            "TIQ-002",
            true,
            "B-23",
            general,
            concierto,
            general.getPrecioBasico(),
            cliente2
        );
        tiquetes.add(tiq2);
        cliente2.agregarTiquete(tiq2);
        concierto.getTiquetesVendidos().add(tiq2);
        
        TiqueteMultiple tiqMulti = new TiqueteMultiple(
            "TIQ-MULTI-001",
            true,
            "C-10",
            vip,
            concierto,
            vip.getPrecioMultiple() * 4,
            4,
            cliente1
        );
        tiquetes.add(tiqMulti);
        cliente1.agregarTiquete(tiqMulti);
        concierto.getTiquetesVendidos().add(tiqMulti);
        
        cal.set(2025, Calendar.DECEMBER, 1);
        Evento partido1 = new Evento(
            "Millonarios vs América",
            cal.getTime(),
            5000, 500, 100,
            0.10, 3000,
            5000, 100, 500,
            org1, estadio, admin
        );
        partido1.setTipoEvento("DEPORTIVO");
        
        Localidad tribuna = new Localidad();
        tribuna.setNombreLocalidad("Tribuna");
        tribuna.setTieneNumeracion(true);
        tribuna.setPrecioBasico(50000);
        tribuna.setPrecioTemporada(45000);
        tribuna.setDescuento(0);
        partido1.agregarLocalidad(tribuna);
        eventos.add(partido1);
        
        cal.set(2025, Calendar.DECEMBER, 15);
        Evento partido2 = new Evento(
            "Santa Fe vs Nacional",
            cal.getTime(),
            5000, 500, 100,
            0.10, 3000,
            5000, 100, 500,
            org1, estadio, admin
        );
        partido2.setTipoEvento("DEPORTIVO");
        partido2.agregarLocalidad(tribuna);
        eventos.add(partido2);
        
        TiqueteTemporada tiqTemp = new TiqueteTemporada(
            "TIQ-TEMP-001",
            true,
            "D-50",
            tribuna,
            partido1,
            80000,
            cliente2
        );
        tiqTemp.agregarEvento(partido2);
        tiquetes.add(tiqTemp);
        cliente2.agregarTiquete(tiqTemp);
        partido1.getTiquetesVendidos().add(tiqTemp);
        
        return new DatosSistema(usuarios, eventos, venues, tiquetes);
    }
    
    private void mostrarResumen(DatosSistema datos, String etiqueta) {
        System.out.println("\n   --- DATOS " + etiqueta + " ---");
        System.out.println("   Usuarios: " + datos.usuarios.size());
        for (Usuario u : datos.usuarios) {
            System.out.println("     - " + u.getLogin() + " (" + u.getClass().getSimpleName() + 
                             ") - Tiquetes: " + u.getTiquetesActivos().size());
        }
        
        System.out.println("   Venues: " + datos.venues.size());
        for (Venue v : datos.venues) {
            System.out.println("     - " + v.getNombre() + " (Cap: " + v.getCapacidad() + 
                             ", Aprobado: " + v.isAprobado() + ")");
        }
        
        System.out.println("   Eventos: " + datos.eventos.size());
        for (Evento e : datos.eventos) {
            System.out.println("     - " + e.getNombreEvento() + " (Localidades: " + 
                             e.getLocalidades().size() + ", Tiquetes vendidos: " + 
                             e.getTiquetesVendidos().size() + ")");
        }
        
        System.out.println("   Tiquetes: " + datos.tiquetes.size());
        for (Tiquete t : datos.tiquetes) {
            System.out.println("     - " + t.getId() + " (" + t.getClass().getSimpleName() + 
                             ") - Usuario: " + t.getUsuario().getLogin() + 
                             ", Precio: $" + t.getPrecioTiquete());
        }
    }
    
    private void verificarIntegridad(DatosSistema originales, DatosSistema cargados) {
        int errores = 0;
        
        if (originales.usuarios.size() != cargados.usuarios.size()) {
            System.out.println("   ✗ ERROR: Número de usuarios no coincide");
            errores++;
        } else {
            System.out.println("   ✓ Número de usuarios correcto: " + cargados.usuarios.size());
        }
        
        if (originales.venues.size() != cargados.venues.size()) {
            System.out.println("   ✗ ERROR: Número de venues no coincide");
            errores++;
        } else {
            System.out.println("   ✓ Número de venues correcto: " + cargados.venues.size());
        }

        if (originales.eventos.size() != cargados.eventos.size()) {
            System.out.println("   ✗ ERROR: Número de eventos no coincide");
            errores++;
        } else {
            System.out.println("   ✓ Número de eventos correcto: " + cargados.eventos.size());
        }
        
        if (originales.tiquetes.size() != cargados.tiquetes.size()) {
            System.out.println("   ✗ ERROR: Número de tiquetes no coincide");
            errores++;
        } else {
            System.out.println("   ✓ Número de tiquetes correcto: " + cargados.tiquetes.size());
        }
        
        for (Usuario u : cargados.usuarios) {
            if (u.getTiquetesActivos().isEmpty()) {
                continue;
            }
            for (Tiquete t : u.getTiquetesActivos()) {
                if (!t.getUsuario().getLogin().equals(u.getLogin())) {
                    System.out.println("   ✗ ERROR: Relación usuario-tiquete incorrecta");
                    errores++;
                }
            }
        }
        System.out.println("   ✓ Relaciones usuario-tiquete correctas");
        
        for (Evento e : cargados.eventos) {
            if (e.getLocalidades().isEmpty()) {
                System.out.println("   ✗ ERROR: Evento sin localidades: " + e.getNombreEvento());
                errores++;
            }
        }
        System.out.println("   ✓ Todos los eventos tienen localidades");
        
        if (errores == 0) {
            System.out.println("\n   ✓✓✓ TODOS LOS DATOS SE CARGARON CORRECTAMENTE ✓✓✓");
        } else {
            System.out.println("\n   ✗✗✗ SE ENCONTRARON " + errores + " ERRORES ✗✗✗");
        }
    }
}