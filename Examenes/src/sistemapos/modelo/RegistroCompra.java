package sistemapos.modelo;

public class RegistroCompra {

    private String fechaHora;
    private String ticket;
    private String cliente;
    private String cajero;
    private double total;

    public RegistroCompra(String fechaHora, String ticket, String cliente, String cajero, double total) {
        this.fechaHora = fechaHora;
        this.ticket = ticket;
        this.cliente = cliente;
        this.cajero = cajero;
        this.total = total;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getTicket() {
        return ticket;
    }

    public String getCliente() {
        return cliente;
    }

    public String getCajero() {
        return cajero;
    }

    public double getTotal() {
        return total;
    }

    public String toCsv() {
        return fechaHora.replace(",", ";") + "," +
               ticket.replace(",", ";") + "," +
               cliente.replace(",", ";") + "," +
               cajero.replace(",", ";") + "," +
               total;
    }
}
