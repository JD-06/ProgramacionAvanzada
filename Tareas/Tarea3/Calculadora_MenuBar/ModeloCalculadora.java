package ejemplos.calculadoramenu;

import java.util.ArrayList;
import java.util.List;

public class ModeloCalculadora {
    private double resultado;
    private double operando1;
    private double operando2;
    private String operacion;
    private List<String> historial;
    private boolean esCientifico;

    public ModeloCalculadora() {
        this.resultado = 0.0;
        this.operando1 = 0.0;
        this.operando2 = 0.0;
        this.operacion = "";
        this.historial = new ArrayList<>();
        this.esCientifico = false;
    }

    public void setOperando1(double operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(double operando2) {
        this.operando2 = operando2;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double calcular() {
        switch (operacion) {
            case "+":
                resultado = operando1 + operando2;
                break;
            case "-":
                resultado = operando1 - operando2;
                break;
            case "*":
            case "×":
                resultado = operando1 * operando2;
                break;
            case "/":
            case "÷":
                if (operando2 != 0) {
                    resultado = operando1 / operando2;
                } else {
                    throw new ArithmeticException("División por cero");
                }
                break;
            case "^":
                resultado = Math.pow(operando1, operando2);
                break;
            default:
                resultado = operando1;
        }

        agregarHistorial();
        return resultado;
    }

    public double calcularSeno(double angulo) {
        resultado = Math.sin(Math.toRadians(angulo));
        historial.add("sin(" + angulo + ") = " + resultado);
        return resultado;
    }

    public double calcularCoseno(double angulo) {
        resultado = Math.cos(Math.toRadians(angulo));
        historial.add("cos(" + angulo + ") = " + resultado);
        return resultado;
    }

    public double calcularTangente(double angulo) {
        resultado = Math.tan(Math.toRadians(angulo));
        historial.add("tan(" + angulo + ") = " + resultado);
        return resultado;
    }

    public double calcularRaizCuadrada(double numero) {
        if (numero >= 0) {
            resultado = Math.sqrt(numero);
            historial.add("√" + numero + " = " + resultado);
            return resultado;
        } else {
            throw new ArithmeticException("Raíz cuadrada de número negativo");
        }
    }

    public double calcularLogaritmo(double numero) {
        if (numero > 0) {
            resultado = Math.log10(numero);
            historial.add("log(" + numero + ") = " + resultado);
            return resultado;
        } else {
            throw new ArithmeticException("Logaritmo de número no positivo");
        }
    }

    private void agregarHistorial() {
        String entrada = String.format("%.2f %s %.2f = %.2f",
            operando1, operacion, operando2, resultado);
        historial.add(entrada);
    }

    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }

    public void limpiarHistorial() {
        historial.clear();
    }

    public void resetear() {
        resultado = 0.0;
        operando1 = 0.0;
        operando2 = 0.0;
        operacion = "";
    }

    public double getResultado() {
        return resultado;
    }

    public void setEsCientifico(boolean esCientifico) {
        this.esCientifico = esCientifico;
    }

    public boolean isEsCientifico() {
        return esCientifico;
    }
}
