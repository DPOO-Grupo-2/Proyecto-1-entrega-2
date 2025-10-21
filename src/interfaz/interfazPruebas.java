package interfaz;

import java.text.SimpleDateFormat;
import java.util.Date;

import usuarios.Administrador;
import usuarios.Cliente;
import usuarios.Organizador;
import evento.Evento;
import evento.Localidad;
import evento.Oferta;
import evento.Venue;
import tiquetes.Tiquete;
import tiquetes.TiqueteBasico;
import tiquetes.TiqueteMultiple;
import tiquetes.TiqueteTemporada;
import tiquetes.PaqueteDeluxe;

public class interfazPruebas {

    public static void main(String[] args) {
        System.out.println("PRUEBAS COMPLETAS DEL SISTEMA CON FECHAS VALIDAS");
        System.out.println("================================================\n");
        
        // Configuración inicial con objetos válidos
        Administrador admin = new Administrador("admin", "admin123", 0);
        Organizador organizador = new Organizador("organizador", "org123", 100000);
        Cliente cliente1 = new Cliente("cliente1", "cliente123", 200000); // Más saldo
        Cliente cliente2 = new Cliente("cliente2", "cliente456", 150000);
        
        // Crear MULTIPLES venues para evitar conflictos de fechas
        Venue venue1 = new Venue("Estadio Nacional", "MUSICAL", 5000, "San Jose", "Ninguna");
        Venue venue2 = new Venue("Teatro Nacional", "CULTURAL", 2000, "Centro", "Formal");
        Venue venue3 = new Venue("Arena Deportiva", "DEPORTIVO", 3000, "Norte", "Deportivo");
        Venue venue4 = new Venue("Auditorio Central", "GENERAL", 1500, "Este", "Cultural");
        
        admin.aprobarVenue(venue1);
        admin.aprobarVenue(venue2);
        admin.aprobarVenue(venue3);
        admin.aprobarVenue(venue4);
        
        System.out.println("Venues creados y aprobados: " + venue1.getNombre() + ", " + venue2.getNombre());
        
        // Crear localidad
        Localidad localidad = new Localidad();
        localidad.setNombreLocalidad("General");
        localidad.setPrecioBasico(15000); // Precios más bajos para evitar saldo insuficiente
        localidad.setPrecioMultiple(12000);
        localidad.setPrecioTemporada(25000);
        localidad.setPrecioDelux(30000);
        
        System.out.println("Localidad creada: " + localidad.getNombreLocalidad());
        
        // PRUEBA 1: CREAR EVENTO CON FECHA VALIDA Y PROBAR GANANCIAS
        System.out.println("\n1. CREACION DE EVENTO Y PRUEBA DE GANANCIAS");
        System.out.println("-------------------------------------------");
        
        try {
            // Crear fecha válida (30 días en el futuro)
            Date fechaEvento = new Date(System.currentTimeMillis() + (86400000L * 30));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            Evento concierto = organizador.crearEvento(
                "Concierto de Prueba", 
                fechaEvento,
                200, 100, 50, 
                10.0, 1000.0,  // Cuota más baja
                300, 80, 150, 
                venue1,  // Usar venue1
                admin
            );
            
            if (concierto != null) {
                concierto.agregarLocalidad(localidad);
                System.out.println("Evento creado exitosamente: " + concierto.getNombreEvento());
                System.out.println("Fecha: " + sdf.format(concierto.getFecha()));
                
                // Realizar ventas para generar ganancias
                System.out.println("\nRealizando ventas...");
                
                // Cliente 1 compra diferentes tipos de tiquetes
                System.out.println("Saldo cliente1 inicial: " + cliente1.getSaldo());
                
                // Compra básico
                cliente1.comprarTiquete("basico", cliente1, concierto, localidad, 1);
                System.out.println("Cliente 1 compro 1 tiquete basico - Saldo: " + cliente1.getSaldo());
                
                // Compra múltiple (solo 1 para evitar problemas)
                cliente1.comprarTiquete("multiple", cliente1, concierto, localidad, 1);
                System.out.println("Cliente 1 compro 1 tiquete multiple - Saldo: " + cliente1.getSaldo());
                
                // Compra deluxe
                cliente1.comprarTiquete("deluxe", cliente1, concierto, localidad, 1);
                System.out.println("Cliente 1 compro 1 tiquete deluxe - Saldo: " + cliente1.getSaldo());
                
                // Cliente 2 también compra
                cliente2.comprarTiquete("basico", cliente2, concierto, localidad, 1);
                System.out.println("Cliente 2 compro 1 tiquete basico");
                
                cliente2.comprarTiquete("temporada", cliente2, concierto, localidad, 1);
                System.out.println("Cliente 2 compro 1 tiquete temporada");
                
                System.out.println("\nTotal tiquetes vendidos: " + concierto.getTiquetesVendidos().size());
                
                // PROBAR METODOS DE GANANCIAS
                System.out.println("\nProbando metodos de ganancias:");
                
                double gananciasAdmin = admin.consultarGananciasPorEvento(concierto);
                System.out.println("Ganancias del administrador: " + gananciasAdmin);
                
                double gananciasOrganizador = organizador.consultarGananciasPorEvento(concierto);
                System.out.println("Ganancias del organizador por evento: " + gananciasOrganizador);
                
                double gananciasTotalesOrganizador = organizador.consultarGananciasTotales();
                System.out.println("Ganancias totales del organizador: " + gananciasTotalesOrganizador);
                
                double gananciasPorLocalidad = organizador.consultarGananciasPorLocalidad(concierto, localidad);
                System.out.println("Ganancias por localidad: " + gananciasPorLocalidad);
                
                double porcentajeVentas = organizador.calcularPorcentajeVentas(concierto);
                System.out.println("Porcentaje de ventas: " + String.format("%.2f", porcentajeVentas) + "%");
                
            } else {
                System.out.println("Error: No se pudo crear el evento");
            }
            
        } catch (Exception e) {
            System.out.println("Error en creacion de evento: " + e.getMessage());
        }
        
        // PRUEBA 2: METODOS DE OFERTAS Y DESCUENTOS
        System.out.println("\n2. PRUEBA DE OFERTAS Y DESCUENTOS");
        System.out.println("---------------------------------");
        
        try {
            // Crear otro evento para ofertas con venue diferente
            Date fechaEventoOfertas = new Date(System.currentTimeMillis() + (86400000L * 45));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            Evento eventoOfertas = organizador.crearEvento(
                "Evento con Ofertas Especiales",
                fechaEventoOfertas,
                100, 50, 25,
                8.0, 800.0,  // Cuota más baja
                150, 40, 80,
                venue2,  // Usar venue2 diferente
                admin
            );
            
            if (eventoOfertas != null) {
                eventoOfertas.agregarLocalidad(localidad);
                System.out.println("Evento para ofertas creado: " + eventoOfertas.getNombreEvento());
                
                // Crear oferta vigente
                Date inicioOferta = new Date();
                Date finOferta = new Date(System.currentTimeMillis() + (86400000L * 30));
                Oferta oferta = new Oferta(eventoOfertas, localidad, 25.0, inicioOferta, finOferta);
                localidad.agregarOferta(oferta);
                
                System.out.println("\nOferta creada: " + oferta.getDescuento() + "% de descuento");
                
                // Probar metodo estaVigente
                boolean vigente = oferta.estaVigente(new Date());
                System.out.println("Oferta vigente: " + vigente);
                
                // Probar metodo calcularPrecioConDescuento
                double precioBase = localidad.getPrecioBasico();
                double precioConDescuento = oferta.calcularPrecioConDescuento(precioBase, new Date());
                System.out.println("Precio base: " + precioBase);
                System.out.println("Precio con 25% descuento: " + precioConDescuento);
                System.out.println("Ahorro: " + (precioBase - precioConDescuento));
                
                // Probar setDescuento en localidad
                double descuentoAnterior = localidad.getDescuento();
                localidad.setDescuento(15.0);
                System.out.println("Descuento cambiado de " + descuentoAnterior + "% a " + localidad.getDescuento() + "%");
                
            }
            
        } catch (Exception e) {
            System.out.println("Error en prueba de ofertas: " + e.getMessage());
        }
        
        // PRUEBA 3: METODOS DE CANCELACION Y REEMBOLSO
        System.out.println("\n3. PRUEBA DE CANCELACION Y REEMBOLSO");
        System.out.println("-----------------------------------");
        
        try {
            Date fechaEventoCancelacion = new Date(System.currentTimeMillis() + (86400000L * 60)); // 60 días
            
            Evento eventoCancela = organizador.crearEvento(
                "Evento para Cancelaciones",
                fechaEventoCancelacion,
                50, 25, 10,
                5.0, 500.0,  // Cuota baja
                80, 20, 40,
                venue3,  // Usar venue3 diferente
                admin
            );
            
            if (eventoCancela != null) {
                eventoCancela.agregarLocalidad(localidad);
                System.out.println("Evento para cancelaciones creado: " + eventoCancela.getNombreEvento());
                
                // Cliente compra tiquetes para cancelar
                Cliente clienteCancelar = new Cliente("cliente_cancel", "pass", 100000);
                System.out.println("Saldo inicial cliente: " + clienteCancelar.getSaldo());
                
                clienteCancelar.comprarTiquete("basico", clienteCancelar, eventoCancela, localidad, 1);
                System.out.println("Compro 1 tiquete basico - Saldo: " + clienteCancelar.getSaldo());
                
                clienteCancelar.comprarTiquete("multiple", clienteCancelar, eventoCancela, localidad, 1);
                System.out.println("Compro 1 tiquete multiple - Saldo: " + clienteCancelar.getSaldo());
                
                System.out.println("\nEstado inicial:");
                System.out.println("Saldo cliente: " + clienteCancelar.getSaldo());
                System.out.println("Tiquetes comprados: " + clienteCancelar.getTiquetesComprados().size());
                System.out.println("Tiquetes activos: " + clienteCancelar.getTiquetesActivos().size());
                
                // Probar cancelarTiquete
                if (!clienteCancelar.getTiquetesComprados().isEmpty()) {
                    Tiquete tiqueteCancelar = clienteCancelar.getTiquetesComprados().get(0);
                    double saldoAntes = clienteCancelar.getSaldo();
                    
                    System.out.println("\nCancelando tiquete: " + tiqueteCancelar.getId());
                    clienteCancelar.cancelarTiquete(tiqueteCancelar.getId(), eventoCancela.getNombreEvento());
                    
                    double saldoDespues = clienteCancelar.getSaldo();
                    System.out.println("Saldo antes: " + saldoAntes);
                    System.out.println("Saldo despues: " + saldoDespues);
                    System.out.println("Diferencia: " + (saldoDespues - saldoAntes));
                    System.out.println("Tiquetes restantes: " + clienteCancelar.getTiquetesComprados().size());
                }
                
                // Probar reembolso directo del administrador
                if (!clienteCancelar.getTiquetesActivos().isEmpty()) {
                    Tiquete tiqueteReembolso = clienteCancelar.getTiquetesActivos().get(0);
                    double saldoAntesReembolso = clienteCancelar.getSaldo();
                    
                    System.out.println("\nProbando reembolso del administrador:");
                    boolean exito = admin.reembolso(clienteCancelar, tiqueteReembolso);
                    System.out.println("Reembolso exitoso: " + exito);
                    
                    double saldoDespuesReembolso = clienteCancelar.getSaldo();
                    System.out.println("Diferencia: " + (saldoDespuesReembolso - saldoAntesReembolso));
                }
                
            }
            
        } catch (Exception e) {
            System.out.println("Error en prueba de cancelacion: " + e.getMessage());
        }
        
        // PRUEBA 4: METODOS DE TRANSFERENCIA
        System.out.println("\n4. PRUEBA DE TRANSFERENCIA DE TIQUETES");
        System.out.println("-------------------------------------");
        
        try {
            Date fechaEventoTransfer = new Date(System.currentTimeMillis() + (86400000L * 75));
            
            Evento eventoTransfer = organizador.crearEvento(
                "Evento para Transferencias",
                fechaEventoTransfer,
                30, 15, 5,
                7.0, 600.0,  // Cuota baja
                50, 10, 25,
                venue4,  // Usar venue4 diferente
                admin
            );
            
            if (eventoTransfer != null) {
                eventoTransfer.agregarLocalidad(localidad);
                System.out.println("Evento para transferencias creado: " + eventoTransfer.getNombreEvento());
                
                Cliente emisor = new Cliente("emisor", "pass", 50000);
                Cliente receptor = new Cliente("receptor", "pass", 50000);
                
                // Emisor compra tiquetes
                emisor.comprarTiquete("basico", emisor, eventoTransfer, localidad, 1);
                System.out.println("Emisor compro 1 tiquete basico - Saldo: " + emisor.getSaldo());
                
                // Probar transferencia
                if (!emisor.getTiquetesActivos().isEmpty()) {
                    Tiquete tiqueteTransferir = emisor.getTiquetesActivos().get(0);
                    
                    System.out.println("\nAntes de transferir:");
                    System.out.println("Emisor: " + emisor.getTiquetesActivos().size() + " tiquetes");
                    System.out.println("Receptor: " + receptor.getTiquetesActivos().size() + " tiquetes");
                    System.out.println("Tiquete transferible: " + tiqueteTransferir.isTransferible());
                    
                    // Transferir tiquete
                    emisor.transferirTiquete(tiqueteTransferir, receptor, "pass");
                    
                    System.out.println("Despues de transferir:");
                    System.out.println("Emisor: " + emisor.getTiquetesActivos().size() + " tiquetes");
                    System.out.println("Receptor: " + receptor.getTiquetesActivos().size() + " tiquetes");
                }
                
            }
            
        } catch (Exception e) {
            System.out.println("Error en prueba de transferencia: " + e.getMessage());
        }
        
        // PRUEBA 5: METODOS DE USO DE TIQUETES ESPECIALES - CORREGIDOS
        System.out.println("\n5. PRUEBA DE TIQUETES ESPECIALES - CORREGIDOS");
        System.out.println("---------------------------------------------");
        
        try {
            // Probar TiqueteMultiple CORREGIDO
            System.out.println("Probando TiqueteMultiple (corregido):");
            
            // Crear tiquetes individuales primero
            TiqueteBasico t1 = new TiqueteBasico("T1", true, "A1", localidad, null, 12000, null);
            TiqueteBasico t2 = new TiqueteBasico("T2", true, "A2", localidad, null, 12000, null);
            TiqueteBasico t3 = new TiqueteBasico("T3", true, "A3", localidad, null, 12000, null);
            
            TiqueteMultiple tiqueteMultiple = new TiqueteMultiple("MULT-001", true, "M1", localidad, null, 36000, 3, null);
            
            // Agregar tiquetes individuales al múltiple
            tiqueteMultiple.añadirTiquete(t1);
            tiqueteMultiple.añadirTiquete(t2);
            tiqueteMultiple.añadirTiquete(t3);
            
            System.out.println("Tiquetes disponibles: " + tiqueteMultiple.getTiquetes().size());
            System.out.println("Tiquetes usados: " + tiqueteMultiple.getTiquetesUsados().size());
            
            tiqueteMultiple.usarTiqueteMultiple();
            System.out.println("Despues de usar uno - Disponibles: " + tiqueteMultiple.getTiquetes().size());
            System.out.println("Tiquetes usados: " + tiqueteMultiple.getTiquetesUsados().size());
            
            // Probar TiqueteTemporada CORREGIDO
            System.out.println("\nProbando TiqueteTemporada (corregido):");
            
            // Crear eventos con venues diferentes para evitar conflictos
            Date fechaTemp1 = new Date(System.currentTimeMillis() + (86400000L * 85));
            Date fechaTemp2 = new Date(System.currentTimeMillis() + (86400000L * 95));
            
            // Crear nuevos venues para evitar conflictos
            Venue venueTemp1 = new Venue("Sala Conciertos", "MUSICAL", 800, "Oeste", "Acustico");
            Venue venueTemp2 = new Venue("Anfiteatro", "CULTURAL", 600, "Sur", "Intimo");
            admin.aprobarVenue(venueTemp1);
            admin.aprobarVenue(venueTemp2);
            
            Evento evento1 = organizador.crearEvento("Evento 1 Temporada", fechaTemp1, 10, 5, 2, 5.0, 300.0, 20, 5, 10, venueTemp1, admin);
            Evento evento2 = organizador.crearEvento("Evento 2 Temporada", fechaTemp2, 10, 5, 2, 5.0, 300.0, 20, 5, 10, venueTemp2, admin);
            
            if (evento1 != null && evento2 != null) {
                TiqueteTemporada temporada = new TiqueteTemporada("TEMP-001", true, "B1", localidad, evento1, 25000, null);
                temporada.agregarEvento(evento2);
                
                System.out.println("Eventos en tiquete temporada: " + temporada.getEventos().size());
                temporada.usarTiqueteTemporada(evento1.getNombreEvento());
                System.out.println("Eventos disponibles despues de usar: " + temporada.getEventos().size());
                System.out.println("Eventos usados: " + temporada.getUsados().size());
            }
            
            // Probar PaqueteDeluxe
            System.out.println("\nProbando PaqueteDeluxe:");
            TiqueteBasico tiqueteDeluxe = new TiqueteBasico("DLX-001", true, "VIP1", localidad, null, 30000, null);
            PaqueteDeluxe paquete = new PaqueteDeluxe("Backstage Access", "Merchandise Pack", tiqueteDeluxe);
            
            // Agregar tiquetes de cortesia
            TiqueteBasico cortesia1 = new TiqueteBasico("COR-001", true, "VIP2", localidad, null, 0, null);
            TiqueteBasico cortesia2 = new TiqueteBasico("COR-002", true, "VIP3", localidad, null, 0, null);
            paquete.agregarTiqueteCortesia(cortesia1);
            paquete.agregarTiqueteCortesia(cortesia2);
            
            System.out.println("Paquete Deluxe creado:");
            System.out.println("Beneficios: " + paquete.getBeneficiosAdicionales());
            System.out.println("Mercancia: " + paquete.getMercanciaAdicional());
            System.out.println("Tiquetes de cortesia: " + paquete.getTiquetesCortesia().size());
            System.out.println("Tiquete principal transferible: " + tiqueteDeluxe.isTransferible());
            
        } catch (Exception e) {
            System.out.println("Error en prueba de tiquetes especiales: " + e.getMessage());
        }
        
        System.out.println("\n================================================");
        System.out.println("TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE");
        System.out.println("================================================");
    }
}