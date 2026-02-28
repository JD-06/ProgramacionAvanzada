from tkinter import messagebox

from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.model.obra import Obra
from PA_a2251330007_practica2_python.view.obra_dialog import ObraDialog
from PA_a2251330007_practica2_python.view.obra_internal_frame import ObraInternalFrame


class ObraController:
    def __init__(self, view: ObraInternalFrame, service: AppService) -> None:
        self.view = view
        self.service = service

        self.view.boton_agregar.configure(command=self.add_obra)
        self.view.boton_modificar.configure(command=self.edit_obra)
        self.view.boton_eliminar.configure(command=self.delete_obra)
        self.view.boton_cerrar.configure(command=self.view.dispose)

        self.refresh()

    def refresh(self) -> None:
        self.view.set_obras(self.service.get_obras())

    def add_obra(self) -> None:
        dialog = ObraDialog(
            self.view,
            "Agregar Obra",
            self.service.generate_next_obra_id(),
            "",
            "",
        )
        dialog.show()
        if not dialog.is_accepted():
            return
        ok = self.service.add_obra(Obra(dialog.get_id_value(), dialog.get_nombre_value(), dialog.get_descripcion_value()))
        if not ok:
            messagebox.showwarning("Aviso", "No se pudo guardar la obra", parent=self.view)
            return
        self.refresh()

    def edit_obra(self) -> None:
        obra_id = self.view.get_selected_id()
        if obra_id is None:
            messagebox.showwarning("Aviso", "Selecciona una obra", parent=self.view)
            return

        dialog = ObraDialog(
            self.view,
            "Modificar Obra",
            obra_id,
            self.view.get_selected_nombre() or "",
            self.view.get_selected_descripcion() or "",
        )
        dialog.show()
        if not dialog.is_accepted():
            return

        self.service.update_obra(Obra(obra_id, dialog.get_nombre_value(), dialog.get_descripcion_value()))
        self.refresh()

    def delete_obra(self) -> None:
        obra_id = self.view.get_selected_id()
        if obra_id is None:
            messagebox.showwarning("Aviso", "Selecciona una obra", parent=self.view)
            return
        confirm = messagebox.askyesno("Confirmar", f"Eliminar obra {obra_id}?", parent=self.view)
        if not confirm:
            return
        self.service.delete_obra(obra_id)
        self.refresh()
