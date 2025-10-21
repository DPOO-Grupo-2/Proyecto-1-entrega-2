package compra;

import java.util.HashSet;
import java.util.Random;

import evento.Localidad;
import evento.Evento;
import tiquetes.Tiquete;
import tiquetes.TiqueteBasico;
import tiquetes.TiqueteTemporada;
import tiquetes.TiqueteMultiple;
import usuarios.Usuario;

public class VendedorTiquetes {
    
    private static HashSet<String> codigos = new HashSet<String>(); 
    
    public VendedorTiquetes() {
    }

    private String generarCodigoUnico() {
        int numero;
        String codigo;
        do {
            numero = (int) (Math.random() * 10e7);
            codigo = String.format("%07d", numero); 
        } while (codigos.contains(codigo));
        
        codigos.add(codigo);
        return codigo;
    }

    public Tiquete venderTiquete(String tipoTiquete, double precioBase, double cargoPorcentual, double cuotaAdicional, Usuario usuario, Evento evento,
                                 Localidad localidad1, int numeroTiquetes) {

        String codigo = generarCodigoUnico(); 
        boolean enumerada = localidad1.isTieneNumeracion();
        String silla1 = null;

        if (enumerada) {
            Random random = new Random();
            char letra = (char) ('A' + random.nextInt(10));
            int numeroSilla = random.nextInt(20) + 1;
            silla1 = letra + String.valueOf(numeroSilla);
        }

        double costoEmision = 0;
        if (evento.getAdministrador() != null && 
            (evento.getOrganizador() == null || !evento.getOrganizador().equals(usuario))) {
            costoEmision = evento.getAdministrador().getCostoFijoEmision();
        }

        if (tipoTiquete.equalsIgnoreCase("basico")) {
            double precio;
            if (evento.getOrganizador() != null && evento.getOrganizador().equals(usuario)) {
                precio = 0;
            } else {
                
                double precioLocalidad;
                try {
                    precioLocalidad = localidad1.getPrecioBasicoConDescuento();
                } catch (Exception e) {
                    precioLocalidad = localidad1.getPrecioBasico() * (1 - localidad1.getDescuento() / 100.0);
                }
                precio = precioLocalidad * (1 + cargoPorcentual / 100.0);
                precio += cuotaAdicional + costoEmision;
            }
            return new TiqueteBasico(codigo, true, silla1, localidad1, evento, precio, usuario);

        } else if (tipoTiquete.equalsIgnoreCase("temporada")) {
            double precio;
            if (evento.getOrganizador() != null && evento.getOrganizador().equals(usuario)) {
                precio = 0;
            } else {
                
                double precioLocalidad;
                try {
                    precioLocalidad = localidad1.getPrecioTemporadaConDescuento();
                } catch (Exception e) {
                    precioLocalidad = localidad1.getPrecioTemporada() * (1 - localidad1.getDescuento() / 100.0);
                }
                precio = precioLocalidad * (1 + cargoPorcentual / 100.0);
                precio += cuotaAdicional + costoEmision;
            }
            return new TiqueteTemporada(codigo, true, silla1, localidad1, evento, precio, usuario);

        } else if (tipoTiquete.equalsIgnoreCase("multiple")) {
            double precio;
            if (evento.getOrganizador() != null && evento.getOrganizador().equals(usuario)) {
                precio = 0;
            } else {
            	
                double precioLocalidad;
                try {
                    precioLocalidad = localidad1.getPrecioMultipleConDescuento();
                } catch (Exception e) {
                    precioLocalidad = localidad1.getPrecioMultiple() * (1 - localidad1.getDescuento() / 100.0);
                }
                precio = precioLocalidad * (1 + cargoPorcentual / 100.0);
                precio += cuotaAdicional + costoEmision;
            }
            double precioMultiple = precio * numeroTiquetes;

            TiqueteMultiple tiqueteMultiple = new TiqueteMultiple(
                codigo, true, silla1, localidad1, evento, precioMultiple, numeroTiquetes, usuario);

            for (int i = 0; i < numeroTiquetes; i++) {
                String codigoIndividual = generarCodigoUnico(); 
                TiqueteBasico tiqueteIndividual = new TiqueteBasico(
                    codigoIndividual, true, silla1, localidad1, evento, precio, usuario);
                tiqueteMultiple.añadirTiquete(tiqueteIndividual);
            }

            return tiqueteMultiple;
        }

        return null;
    }
}