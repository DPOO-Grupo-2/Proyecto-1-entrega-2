package usuarios;

import java.util.HashMap;
import java.util.Map;

public class Administrador extends usuario {
    
    private double costoFijoEmision;
    private Map<String, Double> porcentajeServicioPorTipo;

    public Administrador(String login, String password, double saldo) {
        super(login, password, saldo);
        this.costoFijoEmision = 5000;
        this.porcentajeServicioPorTipo = new HashMap<>();

        porcentajeServicioPorTipo.put("MUSICAL", 0.10);
        porcentajeServicioPorTipo.put("CULTURAL", 0.08);
        porcentajeServicioPorTipo.put("DEPORTIVO", 0.12);
        porcentajeServicioPorTipo.put("RELIGIOSO", 0.05);
    }
    

    public double getCostoFijoEmision() {
        return costoFijoEmision;
    }
    
    public void setCostoFijoEmision(double costo) {
        this.costoFijoEmision = costo;
        System.out.println("Costo de emisión: $" + costo);
    }
    
}
