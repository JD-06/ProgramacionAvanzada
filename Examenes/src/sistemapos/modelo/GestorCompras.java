package sistemapos.modelo;

import sistemapos.persistencia.ArchivoAuxiliarCSV;
import java.util.ArrayList;

public class GestorCompras {

    private ArrayList<RegistroCompra> comprasSocios;
    private ArrayList<RegistroCompra> comprasNoClientes;
    private String rutaSocios;
    private String rutaNoClientes;

    public GestorCompras() {
        this(ArchivoAuxiliarCSV.RUTA_COMPRAS_SOCIOS, ArchivoAuxiliarCSV.RUTA_COMPRAS_NO_CLIENTES);
    }

    public GestorCompras(String rutaSocios, String rutaNoClientes) {
        this.rutaSocios = rutaSocios;
        this.rutaNoClientes = rutaNoClientes;
        comprasSocios = ArchivoAuxiliarCSV.importarCompras(rutaSocios);
        comprasNoClientes = ArchivoAuxiliarCSV.importarCompras(rutaNoClientes);
    }

    public void registrarCompraSocio(RegistroCompra compra) {
        comprasSocios.add(compra);
        ArchivoAuxiliarCSV.exportarCompras(comprasSocios, rutaSocios);
    }

    public void registrarCompraNoCliente(RegistroCompra compra) {
        comprasNoClientes.add(compra);
        ArchivoAuxiliarCSV.exportarCompras(comprasNoClientes, rutaNoClientes);
    }

    public ArrayList<RegistroCompra> getComprasSocios() {
        return comprasSocios;
    }

    public ArrayList<RegistroCompra> getComprasNoClientes() {
        return comprasNoClientes;
    }
}
