package controller;

import model.StorageType;
import view.CategoriaInternalFrame;
import view.InsumoInternalFrame;
import view.MenuPrincipalFrame;
import view.ObraInternalFrame;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class MainController {
    private final MenuPrincipalFrame view;
    private final AppService service;

    public MainController(MenuPrincipalFrame view, AppService service) {
        this.view = view;
        this.service = service;

        this.view.applyStorageType(this.service.getStorageType());

        this.view.miCategorias.addActionListener(e -> openCategorias());
        this.view.miInsumos.addActionListener(e -> openInsumos());
        this.view.miObras.addActionListener(e -> openObras());
        this.view.miTxt.addActionListener(e -> changeStorage(StorageType.TXT));
        this.view.miXml.addActionListener(e -> changeStorage(StorageType.XML));
        this.view.miSalir.addActionListener(e -> this.view.dispose());
    }

    private void changeStorage(StorageType storageType) {
        this.service.setStorageType(storageType);
        this.view.applyStorageType(storageType);
    }

    private void openCategorias() {
        CategoriaInternalFrame frame = new CategoriaInternalFrame();
        new CategoriaController(frame, this.service);
        openFrame(frame);
    }

    private void openInsumos() {
        InsumoInternalFrame frame = new InsumoInternalFrame();
        new InsumoController(frame, this.service);
        openFrame(frame);
    }

    private void openObras() {
        ObraInternalFrame frame = new ObraInternalFrame();
        new ObraController(frame, this.service);
        openFrame(frame);
    }

    private void openFrame(JInternalFrame frame) {
        this.view.desktopPane.add(frame);
        this.view.setMainActionsEnabled(false);
        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                view.setMainActionsEnabled(true);
            }
        });
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (Exception ignored) {
        }
    }
}
