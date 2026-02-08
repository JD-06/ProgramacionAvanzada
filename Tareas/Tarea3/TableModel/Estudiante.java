package ejemplos.tablemodel;

public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String carrera;
    private double promedio;

    public Estudiante(int id, String nombre, String apellido, int edad, String carrera, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    @Override
    public String toString() {
        return "Estudiante{id=" + id + ", nombre=" + nombre + " " + apellido + ", carrera=" + carrera + "}";
    }
}
