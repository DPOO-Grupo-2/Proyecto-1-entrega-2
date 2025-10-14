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
    private static HashSet<String> codigos = new HashSet<>();

    public static Tiquete venderTiquete(String tipoTiquete, double precioBase, double cargoPorcentual, double cuotaAdicional, Usuario usuario, Evento evento,
        Localidad localidad1,
        int numeroTiquetes
    ) {

        int numero = (int) (Math.random() * 10e7);
        String codigo = "" + numero;

        while (codigos.contains(codigo)) {
            numero = (int) (Math.random() * 10e7);
            codigo = "" + numero;
        }

        while (codigo.length() < 7)
            codigo = "0" + codigo;

        codigos.add(codigo); 
       
        
        


        
        //falta implementar funcion de que si el organizador es el que compra el tiquete el precio es 0

        
        	
        	boolean enumerada = localidad1.getEnumeracion();
        String silla1 = null;

        if (enumerada) {
            Random random = new Random();
            char letra = (char) ('A' + random.nextInt(10));
            int numeroSilla = random.nextInt(20) + 1;       
            silla1 = letra + String.valueOf(numeroSilla);
        }

        if (tipoTiquete.equalsIgnoreCase("basico")) {
            double precio = localidad1.getPrecioBasico() * (1 + evento.getCargoPorcentual());
            precio += evento.getCuotaAdicional();
           
            return new TiqueteBasico(codigo, true, silla1, localidad1, evento, precio);

        } else if (tipoTiquete.equalsIgnoreCase("temporada")) {
            double precio = localidad1.getPrecioTemporada() * (1 + evento.getCargoPorcentual());
            precio += evento.getCuotaAdicional();
            return new TiqueteTemporada(codigo, true, silla1, localidad1, evento, precio);

        } else if (tipoTiquete.equalsIgnoreCase("multiple")) {
            double precio = localidad1.getPrecioMultiple() * (1 + evento.getCargoPorcentual());
            precio += evento.getCuotaAdicional();
            double precioMultiple = precio * numeroTiquetes;

            TiqueteMultiple tiquetes = new TiqueteMultiple(
                codigo, true, silla1, localidad1, evento, precioMultiple, numeroTiquetes
            );

            for (int i = 0; i < tiquetes.getNumeroDeTiquetes(); i++) {
                TiqueteBasico tiquete2 = new TiqueteBasico(
                    codigo, true, silla1, localidad1, evento, precio
                );
                tiquetes.añadirTiquete( tiquete2);
            }

            return tiquetes;
        }

        return null;
    }
}
    

