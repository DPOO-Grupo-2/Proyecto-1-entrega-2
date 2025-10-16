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
            
            this.historialTransacciones.add("Compra de tiquete realizada por un valor de $" + tiquete.getPrecio());
            
        } else {
            System.err.println("Error: no se pudo generar el tiquete.");
        }
    }

    public void transferirTiquete(Usuario emisor, Usuario receptor, Tiquete tiquete) {
        if (emisor == null || receptor == null || tiquete == null) {
            System.err.println("Error: datos nulos.");
            return;
        }
        
        if (!emisor.getTiquetesActivos().contains(tiquete)) {
            System.err.println("Error: el emisor no posee este tiquete.");
            return;
        }
        
        if (!tiquete.isTransferible()) {
            System.err.println("Error: este tiquete no es transferible.");
            return;
        }
        
        if (tiquete.isUsado()) {
            System.err.println("Error: no se puede transferir un tiquete usado.");
            return;
        }
        
        if (emisor.getLogin().equals(receptor.getLogin())) {
            System.err.println("Error: no puedes transferirte a ti mismo.");
            return;
        }
        
        emisor.getTiquetesActivos().remove(tiquete);
        receptor.getTiquetesActivos().add(tiquete);
        tiquete.setUsuario(receptor);
        
        if (emisor instanceof Cliente) {
            ((Cliente) emisor).getTiquetesComprados().remove(tiquete);
        }
        
        if (receptor instanceof Cliente) {
            ((Cliente) receptor).getTiquetesComprados().add(tiquete);
        }
    }

    public void cancelarTiquete(String codigoTiquete, String nombreEvento) {
        if (codigoTiquete == null || nombreEvento == null) {
            System.err.println("Error: datos nulos.");
            return;
        }
        
        ArrayList<Tiquete> tiquetesUsuario = this.tiquetesComprados;
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < tiquetesUsuario.size()) {
            Tiquete tiquete = tiquetesUsuario.get(i);
            if (tiquete.getId().equals(codigoTiquete)) {
                encontrado = true;
            } else {
                i++;
            }
        }

        if (!encontrado) {
            System.err.println("Error: tiquete no encontrado.");
            return;
        }

        Tiquete tiquete = tiquetesUsuario.get(i);
        ArrayList<Evento> eventos = tiquete.getEventos();
        
        if (eventos == null || eventos.isEmpty()) {
            System.err.println("Error: el tiquete no tiene eventos asociados.");
            return;
        }
        
        boolean encontrado2 = false;
        int j = 0;

        while (!encontrado2 && j < eventos.size()) {
            Evento evento = eventos.get(j);
            if (nombreEvento.equals(evento.getNombreEvento())) {
                encontrado2 = true;
            } else {
                j++;
            }
        }

        if (!encontrado2) {
            System.err.println("Error: evento no encontrado en el tiquete.");
            return;
        }

        Evento evento = eventos.get(j);
        
        if (evento.getAdministrador() != null && evento.getAdministrador().Reembolso(this, tiquete)) {

            tiquetesUsuario.remove(i);
            this.removerTiquete(tiquete);
            evento.getTiquetesVendidos().remove(tiquete);

            this.historialTransacciones.add("Cancelación de tiquete aprobada: " + codigoTiquete);
        } else {
            System.err.println("Error: la cancelación no fue aprobado por el administrador.");
        }
    }

    
}