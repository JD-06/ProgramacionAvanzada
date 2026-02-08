package ejemplos.calculadoragrid;

public class ModeloCalculadoraGrid {
    private String display;
    private double operando1;
    private double operando2;
    private String operacion;
    private boolean iniciarNuevoNumero;

    public ModeloCalculadoraGrid() {
        this.display = "0";
        this.operando1 = 0;
        this.operando2 = 0;
        this.operacion = "";
        this.iniciarNuevoNumero = true;
    }

    public void agregarDigito(String digito) {
        if (iniciarNuevoNumero) {
            display = digito;
            iniciarNuevoNumero = false;
        } else {
            if (display.equals("0")) {
                display = digito;
            } else {
                display += digito;
            }
        }
    }

    public void agregarPunto() {
        if (iniciarNuevoNumero) {
            display = "0.";
            iniciarNuevoNumero = false;
        } else if (!display.contains(".")) {
            display += ".";
        }
    }

    public void setOperacion(String op) {
        if (!operacion.isEmpty()) {
            calcular();
        }
        operando1 = Double.parseDouble(display);
        operacion = op;
        iniciarNuevoNumero = true;
    }

    public void calcular() {
        if (operacion.isEmpty()) {
            return;
        }

        operando2 = Double.parseDouble(display);

        switch (operacion) {
            case "+":
                operando1 = operando1 + operando2;
                break;
            case "-":
                operando1 = operando1 - operando2;
                break;
            case "×":
                operando1 = operando1 * operando2;
                break;
            case "÷":
                if (operando2 != 0) {
                    operando1 = operando1 / operando2;
                } else {
                    display = "Error";
                    limpiar();
                    return;
                }
                break;
            case "%":
                operando1 = operando1 % operando2;
                break;
        }

        display = formatearResultado(operando1);
        operacion = "";
        iniciarNuevoNumero = true;
    }

    private String formatearResultado(double valor) {
        if (valor == (long) valor) {
            return String.format("%d", (long) valor);
        } else {
            return String.format("%.8f", valor).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    public void limpiar() {
        display = "0";
        operando1 = 0;
        operando2 = 0;
        operacion = "";
        iniciarNuevoNumero = true;
    }

    public void borrar() {
        if (display.length() > 1) {
            display = display.substring(0, display.length() - 1);
        } else {
            display = "0";
            iniciarNuevoNumero = true;
        }
    }

    public void cambiarSigno() {
        double valor = Double.parseDouble(display);
        valor = -valor;
        display = formatearResultado(valor);
    }

    public String getDisplay() {
        return display;
    }
}
