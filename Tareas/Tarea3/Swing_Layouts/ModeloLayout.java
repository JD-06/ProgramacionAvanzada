package ejemplos.layouts;

public class ModeloLayout {
    private String layoutActual;
    private String descripcion;

    public ModeloLayout() {
        this.layoutActual = "FlowLayout";
        actualizarDescripcion();
    }

    public void setLayoutActual(String layout) {
        this.layoutActual = layout;
        actualizarDescripcion();
    }

    public String getLayoutActual() {
        return layoutActual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    private void actualizarDescripcion() {
        switch (layoutActual) {
            case "FlowLayout":
                descripcion = "FlowLayout: Organiza componentes en flujo de izquierda a derecha";
                break;
            case "BorderLayout":
                descripcion = "BorderLayout: Divide en 5 regiones (North, South, East, West, Center)";
                break;
            case "GridLayout":
                descripcion = "GridLayout: Organiza en una cuadrícula de tamaño fijo";
                break;
            case "BoxLayout":
                descripcion = "BoxLayout: Organiza componentes en una línea vertical u horizontal";
                break;
            case "CardLayout":
                descripcion = "CardLayout: Muestra un componente a la vez como tarjetas";
                break;
            case "GridBagLayout":
                descripcion = "GridBagLayout: Layout más flexible y complejo";
                break;
            default:
                descripcion = "Layout desconocido";
        }
    }

    public String[] getLayoutsDisponibles() {
        return new String[] {
            "FlowLayout",
            "BorderLayout",
            "GridLayout",
            "BoxLayout",
            "CardLayout",
            "GridBagLayout"
        };
    }
}
