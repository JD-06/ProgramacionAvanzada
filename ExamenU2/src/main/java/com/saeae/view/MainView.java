package com.saeae.view;

import com.saeae.controller.DatosbaseController;
import com.saeae.controller.EvaluacionController;
import com.saeae.controller.InstrumentoController;
import com.saeae.controller.ReporteController;
import com.saeae.model.AlumnoCotejo;
import com.saeae.model.Evaluacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Vista principal MVC — Dashboard del sistema SAE-AE.
 *
 * Recibe 4 controladores especializados por inyeccion de dependencias:
 *   EvaluacionController  → CRUD JSON
 *   DatosbaseController   → lectura de Datosbase.xlsx
 *   ReporteController     → generacion de Excel
 *   InstrumentoController → logica de negocio pura
 *
 * Layout:
 *   North  — Panel de control con selectores y botones
 *   Center — JTabbedPane con los 3 instrumentos
 *   South  — Barra de estado
 */
public class MainView extends JFrame {

    // -- Controladores inyectados --
    private final EvaluacionController  ctrlEval;
    private final DatosbaseController   ctrlDatos;
    private final ReporteController     ctrlReporte;
    private final InstrumentoController ctrlInstrumento;

    // -- Panel de control --
    private JComboBox<String> cbMateria;
    private JComboBox<String> cbProfesor;
    private JComboBox<String> cbGrupo;
    private JButton btnCargar;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnCarpeta;
    private JButton btnArchivos;
    private JLabel  lblSemaforo;

    // -- Paneles de instrumentos --
    private ProductoIntegradorPanel panelProducto;
    private RubricaPanel            panelRubrica;
    private ListaCotejoPanel        panelCotejo;
    private JTabbedPane             tabbedPane;

    // -- Barra de estado --
    private JLabel lblStatusBar;

    public MainView(EvaluacionController  ctrlEval,
                    DatosbaseController   ctrlDatos,
                    ReporteController     ctrlReporte,
                    InstrumentoController ctrlInstrumento) {
        this.ctrlEval        = ctrlEval;
        this.ctrlDatos       = ctrlDatos;
        this.ctrlReporte     = ctrlReporte;
        this.ctrlInstrumento = ctrlInstrumento;

        // Registrar esta vista en los controladores que la necesitan
        ctrlEval.setView(this);
        ctrlDatos.setView(this);
        ctrlReporte.setView(this);

        initUI();
        poblarCombos();
    }

    // =========================================================================
    // Construccion de la UI
    // =========================================================================

    private void initUI() {
        setTitle("SAE-AE  |  Sistema Automatizado de Evaluacion de Atributos de Egreso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setMinimumSize(new Dimension(950, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(construirPanelControl(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirBarraEstado(),  BorderLayout.SOUTH);
    }

    private JPanel construirPanelControl() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        panel.setBackground(new Color(0, 32, 96));
        panel.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));

        JLabel logo = new JLabel("  SAE-AE");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        panel.add(logo);
        panel.add(sep());

        // Selectores (poblados desde DatosbaseController)
        panel.add(lbl("Materia:"));
        cbMateria = new JComboBox<>();  estilo(cbMateria, 200);
        panel.add(cbMateria);

        panel.add(lbl("Profesor:"));
        cbProfesor = new JComboBox<>(); estilo(cbProfesor, 145);
        panel.add(cbProfesor);

        panel.add(lbl("Grupo:"));
        cbGrupo = new JComboBox<>();    estilo(cbGrupo, 60);
        panel.add(cbGrupo);

        panel.add(sep());

        // Botones CRUD
        btnCargar  = btn("Cargar Datos",       new Color(70, 130, 180));
        btnNuevo   = btn("Nuevo Registro",     new Color(46, 139, 87));
        btnGuardar = btn("Guardar/Actualizar", new Color(30, 100, 220));
        btnEliminar= btn("Eliminar",           new Color(190, 40, 40));
        panel.add(btnCargar); panel.add(btnNuevo);
        panel.add(btnGuardar); panel.add(btnEliminar);

        panel.add(sep());

        // Configuracion de archivos
        btnCarpeta  = btn("Carpeta...",        new Color(80, 80, 95));
        btnArchivos = btn("Archivos Excel...", new Color(80, 80, 95));
        panel.add(btnCarpeta);
        panel.add(btnArchivos);

        panel.add(sep());

        // Semaforo
        lblSemaforo = new JLabel("  \u25CF");
        lblSemaforo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSemaforo.setForeground(Color.RED);
        lblSemaforo.setToolTipText("Rojo: Sin iniciar");
        panel.add(lblSemaforo);

        // Al cambiar la materia: repoblar profesores, grupos y atributos en cascada
        cbMateria.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                String materia = (String) cbMateria.getSelectedItem();
                llenarCombo(cbProfesor, ctrlDatos.getProfesoresPorMateria(materia));
                // Tras repoblar profesores, actualizar grupos para el nuevo profesor seleccionado
                String profesor = (String) cbProfesor.getSelectedItem();
                llenarCombo(cbGrupo, ctrlDatos.getGruposPorMateriaYProfesor(materia, profesor));
                // Actualizar atributos de egreso en el panel Producto Integrador
                panelProducto.setAtributos(ctrlDatos.getAtributos(materia));
            }
        });

        // Al cambiar el profesor: repoblar grupos disponibles para esa materia+profesor
        cbProfesor.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                String materia  = (String) cbMateria.getSelectedItem();
                String profesor = (String) cbProfesor.getSelectedItem();
                llenarCombo(cbGrupo, ctrlDatos.getGruposPorMateriaYProfesor(materia, profesor));
            }
        });

        // Al cambiar el grupo: cargar alumnos automaticamente en la Lista de Cotejo
        cbGrupo.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                String materia = (String) cbMateria.getSelectedItem();
                String grupo   = (String) cbGrupo.getSelectedItem();
                List<AlumnoCotejo> alumnos = ctrlDatos.getAlumnos(materia, grupo);
                if (!alumnos.isEmpty()) panelCotejo.cargarAlumnos(alumnos);
            }
        });

        // Eventos — cada boton delega al controlador correspondiente
        btnCargar.addActionListener(e   -> onCargar());
        btnNuevo.addActionListener(e    -> onNuevo());
        btnGuardar.addActionListener(e  -> onGuardar());
        btnEliminar.addActionListener(e -> onEliminar());
        btnCarpeta.addActionListener(e  -> onSeleccionarCarpeta());
        btnArchivos.addActionListener(e -> onConfigurarArchivos());

        return panel;
    }

    private JPanel construirPanelCentral() {
        panelProducto = new ProductoIntegradorPanel();
        panelRubrica  = new RubricaPanel();
        panelCotejo   = new ListaCotejoPanel();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.addTab(" 1. Producto Integrador ", panelProducto);
        tabbedPane.addTab(" 2. Rubrica (Atributos) ", panelRubrica);
        tabbedPane.addTab(" 3. Lista de Cotejo ",     panelCotejo);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(tabbedPane, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel construirBarraEstado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        panel.setBackground(new Color(218, 218, 228));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(180, 180, 195)));
        lblStatusBar = new JLabel("Listo — Seleccione Materia, Profesor y Grupo para comenzar.");
        lblStatusBar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panel.add(lblStatusBar);
        return panel;
    }

    // =========================================================================
    // Carga de combos desde DatosbaseController
    // =========================================================================

    private void poblarCombos() {
        llenarCombo(cbMateria, ctrlDatos.getMaterias());
        llenarCombo(cbGrupo,   ctrlDatos.getGrupos());

        // Profesores filtrados por la materia inicial
        String materiaInicial  = (String) cbMateria.getSelectedItem();
        llenarCombo(cbProfesor, ctrlDatos.getProfesoresPorMateria(materiaInicial));

        // Grupos filtrados por la materia + profesor iniciales
        String profesorInicial = (String) cbProfesor.getSelectedItem();
        llenarCombo(cbGrupo, ctrlDatos.getGruposPorMateriaYProfesor(materiaInicial, profesorInicial));

        // Atributos de egreso para la materia inicial
        panelProducto.setAtributos(ctrlDatos.getAtributos(materiaInicial));

        if (!ctrlDatos.isDisponible()) {
            setStatus("AVISO: Datosbase.xlsx no encontrado. Use 'Archivos Excel...' para configurar.");
        }
    }

    private void llenarCombo(JComboBox<String> combo, List<String> items) {
        combo.removeAllItems();
        for (String item : items) combo.addItem(item);
    }

    // =========================================================================
    // Handlers — cada uno usa el controlador especializado correspondiente
    // =========================================================================

    /** Carga datos desde Datosbase.xlsx (alumnos, atributo) y desde JSON (evaluacion existente). */
    private void onCargar() {
        String materia  = (String) cbMateria.getSelectedItem();
        String profesor = (String) cbProfesor.getSelectedItem();
        String grupo    = (String) cbGrupo.getSelectedItem();
        if (materia == null || grupo == null) return;

        // 1. Cargar alumnos desde Datosbase (nombres, calificaciones en 0)
        List<AlumnoCotejo> alumnosDB = ctrlDatos.getAlumnos(materia, grupo);
        if (!alumnosDB.isEmpty()) panelCotejo.cargarAlumnos(alumnosDB);

        // 2. Buscar registro previo en JSON
        Optional<Evaluacion> opt = ctrlEval.cargar(materia, profesor, grupo);
        if (opt.isPresent()) {
            Evaluacion eval = opt.get();

            // Restaurar rutas guardadas para este profesor/grupo
            if (eval.getCarpetaGuardado() != null && !eval.getCarpetaGuardado().isBlank()) {
                ctrlReporte.setCarpetaGuardado(eval.getCarpetaGuardado());
            }
            if (eval.getRutaPlantilla() != null && !eval.getRutaPlantilla().isBlank()) {
                ctrlReporte.setRutaPlantilla(eval.getRutaPlantilla());
            }

            panelProducto.cargarDatos(eval);
            panelRubrica.cargarDatos(eval);

            // Solo sobreescribir cotejo si el JSON tiene alumnos con calificaciones guardadas;
            // si la lista esta vacia, conservar los alumnos ya cargados de Datosbase.
            boolean jsonTieneAlumnos = eval.getListaCotejo() != null
                    && eval.getListaCotejo().getAlumnos() != null
                    && !eval.getListaCotejo().getAlumnos().isEmpty();
            if (jsonTieneAlumnos) {
                panelCotejo.cargarDatos(eval);
            }

            actualizarSemaforo(ctrlInstrumento.calcularEstatus(eval));
            String carpetaInfo = (eval.getCarpetaGuardado() != null && !eval.getCarpetaGuardado().isBlank())
                    ? "  |  Carpeta: " + eval.getCarpetaGuardado() : "";
            setStatus("Cargado: " + eval.getId() + carpetaInfo);
            JOptionPane.showMessageDialog(this,
                    "Registro cargado:\n" + eval.getId(),
                    "Carga exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            actualizarSemaforo("ROJO");
            String msg = alumnosDB.isEmpty()
                    ? "Sin registro previo y sin alumnos en Datosbase para esta combinacion."
                    : "Sin registro previo. Alumnos cargados de Datosbase: " + alumnosDB.size();
            setStatus(msg);
            JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Limpia todos los paneles para un registro nuevo. */
    private void onNuevo() {
        panelProducto.limpiar();
        panelRubrica.limpiar();
        panelCotejo.limpiar();
        actualizarSemaforo("ROJO");
        setStatus("Nuevo registro — complete los campos y presione Guardar/Actualizar.");
    }

    /** Guarda en JSON (EvaluacionController) y genera Excel (ReporteController). */
    private void onGuardar() {
        String materia  = (String) cbMateria.getSelectedItem();
        String profesor = (String) cbProfesor.getSelectedItem();
        String grupo    = (String) cbGrupo.getSelectedItem();
        if (materia == null || grupo == null) return;

        // Construir la evaluacion desde la vista
        Evaluacion eval = new Evaluacion();
        eval.setAsignatura(materia);
        eval.setProfesor(profesor);
        eval.setGrupo(grupo);
        eval.setId(ctrlEval.generarId(materia, profesor, grupo));
        eval.setDatosInstrumento(panelProducto.getDatos());
        eval.setCriteriosRubrica(panelRubrica.getCriteriosRubrica());
        eval.setListaCotejo(panelCotejo.getListaCotejo());

        // Persistir rutas de archivos asociadas a este profesor/grupo
        eval.setCarpetaGuardado(ctrlReporte.getCarpetaGuardado());
        eval.setRutaPlantilla(ctrlReporte.getRutaPlantilla());

        // InstrumentoController: calcular estatus antes de guardar
        String estatus = ctrlInstrumento.calcularEstatus(eval);
        eval.setEstatus(estatus);

        // EvaluacionController: persistir en JSON
        boolean guardado = ctrlEval.guardar(eval);
        if (!guardado) return;

        // Releer el JSON recien guardado para asegurar que el Excel
        // refleje exactamente lo que quedo persistido
        Evaluacion evalGuardada = ctrlEval.cargar(materia, profesor, grupo).orElse(eval);

        // ReporteController: generar Excel desde el JSON
        String rutaExcel = ctrlReporte.generarExcel(evalGuardada);
        if (rutaExcel != null) {
            actualizarSemaforo(estatus);
            String nombre = ctrlReporte.nombreArchivo(eval);
            setStatus("Guardado: " + eval.getId() + "  |  Excel: " + nombre);
            JOptionPane.showMessageDialog(this,
                    "Evaluacion guardada.\n\nArchivo Excel generado:\n" + nombre,
                    "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Elimina el registro del JSON usando EvaluacionController. */
    private void onEliminar() {
        String materia  = (String) cbMateria.getSelectedItem();
        String profesor = (String) cbProfesor.getSelectedItem();
        String grupo    = (String) cbGrupo.getSelectedItem();
        if (materia == null || grupo == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Eliminar registro de:\n" + materia + " / " + profesor + " / " + grupo + "\n\n¿Continuar?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = ctrlEval.eliminar(materia, profesor, grupo);
            if (ok) {
                panelProducto.limpiar();
                panelRubrica.limpiar();
                panelCotejo.limpiar();
                actualizarSemaforo("ROJO");
                setStatus("Registro eliminado: " + materia + " / " + grupo);
                JOptionPane.showMessageDialog(this,
                        "Registro eliminado del JSON.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontro el registro.", "Sin datos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /** Selecciona carpeta de destino para los Excel — delega a ReporteController. */
    private void onSeleccionarCarpeta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar carpeta para guardar reportes");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setApproveButtonText("Seleccionar");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String carpeta = chooser.getSelectedFile().getAbsolutePath();
            ctrlReporte.setCarpetaGuardado(carpeta);
            setStatus("Carpeta de reportes: " + carpeta);
            JOptionPane.showMessageDialog(this,
                    "Los Excel se guardaran en:\n" + carpeta,
                    "Carpeta configurada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Configura rutas de Datosbase.xlsx y examen.xlsx — delega a sus controladores. */
    private void onConfigurarArchivos() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5); g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Datosbase.xlsx (referencia — solo lectura):"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
        JTextField txtDB = new JTextField(ctrlDatos.getRutaDatosbase(), 38);
        panel.add(txtDB, g);
        g.gridx = 2; g.fill = GridBagConstraints.NONE; g.weightx = 0;
        JButton btnDB = new JButton("...");
        btnDB.addActionListener(e -> elegirXlsx(txtDB));
        panel.add(btnDB, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("examen.xlsx (plantilla de evaluacion):"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
        JTextField txtPl = new JTextField(ctrlReporte.getRutaPlantilla(), 38);
        panel.add(txtPl, g);
        g.gridx = 2; g.fill = GridBagConstraints.NONE; g.weightx = 0;
        JButton btnPl = new JButton("...");
        btnPl.addActionListener(e -> elegirXlsx(txtPl));
        panel.add(btnPl, g);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Configurar archivos Excel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String rdb = txtDB.getText().trim();
            String rpl = txtPl.getText().trim();
            if (!rdb.isEmpty()) {
                ctrlDatos.setRutaDatosbase(rdb);   // DatosbaseController
                poblarCombos();
                setStatus("Datosbase recargada: " + rdb);
            }
            if (!rpl.isEmpty()) {
                ctrlReporte.setRutaPlantilla(rpl); // ReporteController
                setStatus("Plantilla configurada: " + rpl);
            }
        }
    }

    // =========================================================================
    // Metodos publicos (llamados por los controladores en callbacks de error)
    // =========================================================================

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================================
    // Helpers privados de UI
    // =========================================================================

    private void actualizarSemaforo(String estatus) {
        switch (estatus) {
            case "VERDE":
                lblSemaforo.setForeground(new Color(0, 200, 80));
                lblSemaforo.setToolTipText("Verde: Todos los instrumentos completos");
                break;
            case "AMARILLO":
                lblSemaforo.setForeground(new Color(255, 210, 0));
                lblSemaforo.setToolTipText("Amarillo: Datos incompletos");
                break;
            default:
                lblSemaforo.setForeground(new Color(220, 50, 50));
                lblSemaforo.setToolTipText("Rojo: Sin iniciar");
        }
    }

    private void setStatus(String texto) { lblStatusBar.setText(texto); }

    private void elegirXlsx(JTextField destino) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            destino.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    private JLabel lbl(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(Color.WHITE);
        return l;
    }

    private JButton btn(String t, Color c) {
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.BOLD, 11));
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(4, 9, 4, 9));
        return b;
    }

    private void estilo(JComboBox<String> c, int w) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        c.setPreferredSize(new Dimension(w, 24));
    }

    private JSeparator sep() {
        JSeparator s = new JSeparator(JSeparator.VERTICAL);
        s.setPreferredSize(new Dimension(2, 24));
        s.setForeground(new Color(80, 100, 140));
        return s;
    }
}
