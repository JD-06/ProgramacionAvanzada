package ejemplos.jbutton;

import java.util.ArrayList;
import java.util.List;

public class ModeloBoton {
    private int contador;
    private List<String> historial;

    public ModeloBoton() {
        this.contador = 0;
        this.historial = new ArrayList<>();
    }

    public void incrementar() {
        contador++;
        historial.add("Click #" + contador);
    }

    public void decrementar() {
        if (contador > 0) {
            contador--;
            historial.add("Decrementado a " + contador);
        }
    }

    public void resetear() {
        contador = 0;
        historial.clear();
        historial.add("Contador reseteado");
    }

    public int getContador() {
        return contador;
    }

    public String getEstadoTexto() {
        return "Contador: " + contador;
    }

    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }

    public String getMensajeMotivacional() {
        if (contador == 0) {
            return "¡Haz click para comenzar!";
        } else if (contador < 5) {
            return "¡Buen comienzo!";
        } else if (contador < 10) {
            return "¡Vas muy bien!";
        } else {
            return "¡Eres un maestro del click!";
        }
    }
}
