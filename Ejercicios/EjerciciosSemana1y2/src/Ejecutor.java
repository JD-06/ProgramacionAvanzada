import controlador.*;


public class Ejecutor {
    
 
    private static Object controlador;
    
 
    public static void main(String[] args) {
        
        try {
            
            controlador = new Cejercicioclase01_b();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema:");
            e.printStackTrace();
        }
    }
}
