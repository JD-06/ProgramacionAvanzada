package Proyecto2;

public class ModeloConversor {

    private final double TASA_CAMBIO = 20.50; 

    public double convertirDolarAPeso(double dolares) {
        return dolares * TASA_CAMBIO;
    }
}