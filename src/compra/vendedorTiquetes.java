package compra;

import java.util.HashSet;
import java.util.Random;

import evento.localidad;
import evento.evento;
import tiquetes.tiquete;
import tiquetes.tiqueteBasico;
import tiquetes.tiqueteTemporada;
import tiquetes.tiqueteMultiple;
import usuarios.usuario;

public class vendedorTiquetes {
    private static HashSet<String> codigos = new HashSet<>();

    public static tiquete venderTiquete(String tipoTiquete, double precioBase, double cargoPorcentual, double cuotaAdicional, usuario usuario, evento evento,
        localidad localidad1,
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


        double precio = localidad1.getPrecioLocalidad() * (1 + evento.getCargoPorcentual());
        precio += evento.getCuotaAdicional();


        boolean enumerada = localidad1.getEnumeracion();
        String silla1 = null;

        if (enumerada) {
            Random random = new Random();
            char letra = (char) ('A' + random.nextInt(10));
            int numeroSilla = random.nextInt(20) + 1;       
            silla1 = letra + String.valueOf(numeroSilla);
        }

        if (tipoTiquete.equalsIgnoreCase("basico")) {
            return new tiqueteBasico(codigo, true, silla1, localidad1, evento, precio);

        } else if (tipoTiquete.equalsIgnoreCase("temporada")) {
            return new tiqueteTemporada(codigo, true, silla1, localidad1, evento, precio);

        } else if (tipoTiquete.equalsIgnoreCase("multiple")) {
            double precioMultiple = precio * numeroTiquetes;

            tiqueteMultiple tiquetes = new tiqueteMultiple(
                codigo, true, silla1, localidad1, evento, precioMultiple, numeroTiquetes
            );

            for (int i = 0; i < tiquetes.getNumeroDeTiquetes(); i++) {
                tiqueteBasico tiquete2 = new tiqueteBasico(
                    codigo, true, silla1, localidad1, evento, precio
                );
                tiquetes.añadirTiquete(tiquetes, tiquete2);
            }

            return tiquetes;
        }

        return null;
    }
}
    

