from tkinter import messagebox

from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.model.insumo import Insumo
from PA_a2251330007_practica2_python.view.insumo_dialog import InsumoDialog
from PA_a2251330007_practica2_python.view.insumo_internal_frame import InsumoInternalFrame


class InsumoController:
    def __init__(self, view: InsumoInternalFrame, service: AppService) -> None:
        self.view = view
        self.service = service

        self.view.boton_agregar.configure(command=self.add_insumo)
        self.view.boton_modificar.configure(command=self.edit_insumo)
        self.view.boton_eliminar.configure(command=self.delete_insumo)
        self.view.boton_cerrar.configure(command=self.view.dispose)

        self.refresh()

    def refresh(self) -> None:
        names = {categoria.id: categoria.nombre for categoria in self.service.get_categorias()}
        self.view.set_insumos(self.service.get_insumos(), names)

    def add_insumo(self) -> None:
        categorias = self.service.get_categorias()
        if not categorias:
            messagebox.showwarning("Aviso", "Primero registra categorias", parent=self.view)
            return

        dialog = InsumoDialog(self.view, "Agregar Insumo", "", "", None, categorias, True)
        dialog.show()
        if not dialog.is_accepted():
            return

        ok = self.service.add_insumo(Insumo(dialog.get_id_value(), dialog.get_nombre_value(), dialog.get_categoria_id() or ""))
        if not ok:
            messagebox.showwarning("Aviso", "Id duplicado o categoria invalida", parent=self.view)
            return
        self.refresh()

    def edit_insumo(self) -> None:
        insumo_id = self.view.get_selected_id()
        if insumo_id is None:
            messagebox.showwarning("Aviso", "Selecciona un insumo", parent=self.view)
            return

        dialog = InsumoDialog(
            self.view,
            "Modificar Insumo",
            insumo_id,
            self.view.get_selected_nombre() or "",
            self.view.get_selected_id_categoria(),
            self.service.get_categorias(),
            False,
        )
        dialog.show()
        if not dialog.is_accepted():
            return

        self.service.update_insumo(Insumo(insumo_id, dialog.get_nombre_value(), dialog.get_categoria_id() or ""))
        self.refresh()

    def delete_insumo(self) -> None:
        insumo_id = self.view.get_selected_id()
        if insumo_id is None:
            messagebox.showwarning("Aviso", "Selecciona un insumo", parent=self.view)
            return

        confirm = messagebox.askyesno("Confirmar", f"Eliminar insumo {insumo_id}?", parent=self.view)
        if not confirm:
            return

        self.service.delete_insumo(insumo_id)
        self.refresh()
