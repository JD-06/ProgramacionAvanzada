from tkinter import messagebox

from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.model.categoria import Categoria
from PA_a2251330007_practica2_python.view.categoria_dialog import CategoriaDialog
from PA_a2251330007_practica2_python.view.categoria_internal_frame import CategoriaInternalFrame


class CategoriaController:
    def __init__(self, view: CategoriaInternalFrame, service: AppService) -> None:
        self.view = view
        self.service = service

        self.view.boton_agregar.configure(command=self.add_categoria)
        self.view.boton_modificar.configure(command=self.edit_categoria)
        self.view.boton_eliminar.configure(command=self.delete_categoria)
        self.view.boton_cerrar.configure(command=self.view.dispose)

        self.refresh()

    def refresh(self) -> None:
        self.view.set_categorias(self.service.get_categorias())

    def add_categoria(self) -> None:
        dialog = CategoriaDialog(self.view, "Agregar Categoria", "", "", True)
        dialog.show()
        if not dialog.is_accepted():
            return
        ok = self.service.add_categoria(Categoria(dialog.get_id_value(), dialog.get_nombre_value()))
        if not ok:
            messagebox.showwarning("Aviso", "Ya existe el id de la categoria", parent=self.view)
            return
        self.refresh()

    def edit_categoria(self) -> None:
        categoria_id = self.view.get_selected_id()
        if categoria_id is None:
            messagebox.showwarning("Aviso", "Selecciona una categoria", parent=self.view)
            return
        dialog = CategoriaDialog(
            self.view,
            "Modificar Categoria",
            categoria_id,
            self.view.get_selected_nombre() or "",
            False,
        )
        dialog.show()
        if not dialog.is_accepted():
            return
        self.service.update_categoria(Categoria(categoria_id, dialog.get_nombre_value()))
        self.refresh()

    def delete_categoria(self) -> None:
        categoria_id = self.view.get_selected_id()
        if categoria_id is None:
            messagebox.showwarning("Aviso", "Selecciona una categoria", parent=self.view)
            return
        confirm = messagebox.askyesno("Confirmar", f"Eliminar categoria {categoria_id}?", parent=self.view)
        if not confirm:
            return
        ok = self.service.delete_categoria(categoria_id)
        if not ok:
            messagebox.showwarning("Aviso", "No se puede eliminar una categoria con insumos asociados", parent=self.view)
            return
        self.refresh()
