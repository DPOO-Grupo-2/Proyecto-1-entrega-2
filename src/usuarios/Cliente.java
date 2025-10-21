package usuarios;

import java.util.ArrayList;

import evento.Evento;
import evento.Localidad;
import tiquetes.Tiquete;

public class Cliente extends Usuario {

    private ArrayList<Tiquete> tiquetesComprados;
    private ArrayList<String> historialTransacciones;

    public Cliente(String login, String password, double saldo) {
        super(login, password, saldo);
        this.tiquetesComprados = new ArrayList<>();
        this.historialTransacciones = new ArrayList<>();
    }

    public ArrayList<Tiquete> getTiquetesComprados() {
        return tiquetesComprados;
    }

    public ArrayList<String> getHistorialTransacciones() {
        return historialTransacciones;
    }

    public void comprarTiquete(String tipoTiquete, Usuario usuario, Evento evento, Localidad localidad, int numeroTiquetes) {
        if (evento == null || localidad == null || tipoTiquete == null) {
            System.err.println("Error: datos nulos.");
            return;
        }
        
        if (numeroTiquetes <= 0) {
            System.err.println("Error: cantidad inválida.");
            return;
        }
        
        if (evento.getCancelado()) {
            System.err.println("Error: el evento está cancelado.");
            return;
        }
        
        int maxPorTransaccion = evento.getMaxTiquetesPorTransaccion();
        if (maxPorTransaccion > 0 && numeroTiquetes > maxPorTransaccion) {
            System.err.println("Error: supera el máximo de " + maxPorTransaccion + " tiquetes por transacción.");
            return;
        }
        
        Tiquete tiquete = evento.venderTiquete(tipoTiquete, this, evento, localidad, numeroTiquetes);
        
        if (tiquete != null) {
            
            double precio = tiquete.getPrecio();
            if (this.getSaldo() < precio) {
                System.err.println("Error: saldo insuficiente. Necesita $" + precio + " pero tiene $" + this.getSaldo());
                return;
            }
            
            this.actualizarSaldo(-precio);
            
            this.tiquetesComprados.add(tiquete);
            this.agregarTiquete(tiquete);
            
            this.historialTransacciones.add("Compra de tiquete " + tiquete.getId() + " por valor de $" + precio);
            
        } else {
            System.err.println("Error: no se pudo generar el tiquete.");
        }
    }

    public void transferirTiquete(Tiquete tiquete, Usuario receptor, String password) {
        if (tiquete == null || receptor == null) {
            System.err.println("Error: datos nulos.");
            return;
        }
        
        super.transferirTiquete(tiquete, receptor, password);
        
        if (this.tiquetesComprados.contains(tiquete)) {
            this.tiquetesComprados.remove(tiquete);
        }
        
        if (receptor instanceof Cliente) {
            ((Cliente) receptor).getTiquetesComprados().add(tiquete);
        }
        
        this.historialTransacciones.add("Transferencia de tiquete " + tiquete.getId() + " a " + receptor.getLogin());
    }

    public void cancelarTiquete(String codigoTiquete, String nombreEvento) {
        if (codigoTiquete == null || nombreEvento == null) {
            System.err.println("Error: datos nulos.");
            return;
        }
        Tiquete tiqueteEncontrado = null;
        for (Tiquete tiquete : this.tiquetesComprados) {
            if (tiquete.getId().equals(codigoTiquete)) {
                tiqueteEncontrado = tiquete;
                break;
            }
        }
        
        if (tiqueteEncontrado == null) {
            for (Tiquete tiquete : this.getTiquetesActivos()) {
                if (tiquete.getId().equals(codigoTiquete)) {
                    tiqueteEncontrado = tiquete;
                    break;
                }
            }
        }

        if (tiqueteEncontrado == null) {
            System.err.println("Error: tiquete no encontrado.");
            return;
        }

        Tiquete tiquete = tiqueteEncontrado;
        ArrayList<Evento> eventos = tiquete.getEventos();
        
        if (eventos == null || eventos.isEmpty()) {
            System.err.println("Error: el tiquete no tiene eventos asociados.");
            return;
        }
        
        Evento eventoEncontrado = null;
        for (Evento evento : eventos) {
            if (nombreEvento.equals(evento.getNombreEvento())) {
                eventoEncontrado = evento;
                break;
            }
        }

        if (eventoEncontrado == null) {
            System.err.println("Error: evento no encontrado en el tiquete.");
            return;
        }

        Evento evento = eventoEncontrado;
        
        if (evento.getAdministrador() != null && evento.getAdministrador().reembolso(this, tiquete)) {
            this.tiquetesComprados.remove(tiquete);
            this.removerTiquete(tiquete);
            
            this.historialTransacciones.add("Cancelación de tiquete aprobada: " + codigoTiquete);
            System.out.println("Cancelación exitosa. Tiquete " + codigoTiquete + " cancelado.");
        } else {
            System.err.println("Error: la cancelación no fue aprobada por el administrador.");
        }
    }
}