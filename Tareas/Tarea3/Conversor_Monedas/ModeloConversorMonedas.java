package ejemplos.conversormonedas;

import java.util.HashMap;
import java.util.Map;

public class ModeloConversorMonedas {
    private Map<String, Double> tasasCambio;
    private double cantidad;
    private String monedaOrigen;
    private String monedaDestino;

    public ModeloConversorMonedas() {
        inicializarTasasCambio();
        this.cantidad = 0.0;
        this.monedaOrigen = "USD";
        this.monedaDestino = "EUR";
    }

    private void inicializarTasasCambio() {
        tasasCambio = new HashMap<>();

        tasasCambio.put("USD", 1.0);
        tasasCambio.put("EUR", 0.92);
        tasasCambio.put("GBP", 0.79);
        tasasCambio.put("JPY", 149.50);
        tasasCambio.put("MXN", 17.20);
        tasasCambio.put("CAD", 1.36);
        tasasCambio.put("AUD", 1.53);
        tasasCambio.put("CHF", 0.88);
        tasasCambio.put("CNY", 7.24);
        tasasCambio.put("BRL", 4.97);
    }

    public String[] getMonedasDisponibles() {
        return tasasCambio.keySet().toArray(new String[0]);
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setMonedaOrigen(String moneda) {
        this.monedaOrigen = moneda;
    }

    public void setMonedaDestino(String moneda) {
        this.monedaDestino = moneda;
    }

    public double convertir() {
        if (monedaOrigen.equals(monedaDestino)) {
            return cantidad;
        }

        double enUSD = cantidad / tasasCambio.get(monedaOrigen);
        double resultado = enUSD * tasasCambio.get(monedaDestino);

        return Math.round(resultado * 100.0) / 100.0;
    }

    public String obtenerNombreMoneda(String codigo) {
        Map<String, String> nombres = new HashMap<>();
        nombres.put("USD", "Dólar Estadounidense");
        nombres.put("EUR", "Euro");
        nombres.put("GBP", "Libra Esterlina");
        nombres.put("JPY", "Yen Japonés");
        nombres.put("MXN", "Peso Mexicano");
        nombres.put("CAD", "Dólar Canadiense");
        nombres.put("AUD", "Dólar Australiano");
        nombres.put("CHF", "Franco Suizo");
        nombres.put("CNY", "Yuan Chino");
        nombres.put("BRL", "Real Brasileño");

        return nombres.getOrDefault(codigo, codigo);
    }

    public double getTasaCambio(String origen, String destino) {
        if (origen.equals(destino)) {
            return 1.0;
        }
        double enUSD = 1.0 / tasasCambio.get(origen);
        return Math.round((enUSD * tasasCambio.get(destino)) * 10000.0) / 10000.0;
    }
}
