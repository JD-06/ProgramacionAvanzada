package com.saeae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal del sistema SAE-AE.
 * Agrupa todos los datos de una evaluacion para una terna Asignatura/Profesor/Grupo.
 * Se serializa/deserializa a JSON con Jackson.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Evaluacion {

    private String id;          // Clave unica: Asignatura_Profesor_Grupo
    private String asignatura;
    private String profesor;
    private String grupo;
    private String estatus;     // ROJO | AMARILLO | VERDE

    @JsonProperty("carpeta_guardado")
    private String carpetaGuardado;   // Carpeta de destino para los Excel generados

    @JsonProperty("ruta_plantilla")
    private String rutaPlantilla;     // Ruta de examen.xlsx (plantilla)

    @JsonProperty("datos_instrumento")
    private DatosInstrumento datosInstrumento;  // Encabezado + 6 criterios PI

    @JsonProperty("criterios_rubrica")
    private List<CriterioRubrica> criteriosRubrica; // 7 criterios con 4 niveles (RubricaProducto)

    @JsonProperty("lista_cotejo")
    private ListaCotejo listaCotejo;                // Alumnos con calificaciones por escala

    public Evaluacion() {
        this.criteriosRubrica = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            this.criteriosRubrica.add(new CriterioRubrica());
        }
        this.listaCotejo = new ListaCotejo();
    }

    // -------------------------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------------------------

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }

    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public DatosInstrumento getDatosInstrumento() { return datosInstrumento; }
    public void setDatosInstrumento(DatosInstrumento datosInstrumento) { this.datosInstrumento = datosInstrumento; }

    public List<CriterioRubrica> getCriteriosRubrica() { return criteriosRubrica; }
    public void setCriteriosRubrica(List<CriterioRubrica> criteriosRubrica) { this.criteriosRubrica = criteriosRubrica; }

    public ListaCotejo getListaCotejo() { return listaCotejo; }
    public void setListaCotejo(ListaCotejo listaCotejo) { this.listaCotejo = listaCotejo; }

    public String getCarpetaGuardado() { return carpetaGuardado; }
    public void setCarpetaGuardado(String carpetaGuardado) { this.carpetaGuardado = carpetaGuardado; }

    public String getRutaPlantilla() { return rutaPlantilla; }
    public void setRutaPlantilla(String rutaPlantilla) { this.rutaPlantilla = rutaPlantilla; }
}
