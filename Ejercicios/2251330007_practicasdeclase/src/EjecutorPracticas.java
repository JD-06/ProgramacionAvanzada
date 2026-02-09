import controlador.*;


public class EjecutorPracticas {
    

    public static void main(String[] args) {
        
        try {
         
            new CpracticaTarea02();
            
            System.out.println("Sistema iniciado correctamente");
            
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema:");
            e.printStackTrace();
        }
    }
}
