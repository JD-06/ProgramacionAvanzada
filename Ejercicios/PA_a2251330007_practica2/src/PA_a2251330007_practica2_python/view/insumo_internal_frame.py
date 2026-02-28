import tkinter as tk
from tkinter import ttk

from PA_a2251330007_practica2_python.model.insumo import Insumo


class InsumoInternalFrame(tk.Toplevel):
    def __init__(self, master: tk.Misc | None = None) -> None:
        super().__init__(master)
        self.title("Insumos")
        self.geometry("760x430")
        self._on_close = None

        top = tk.Frame(self)
        top.pack(fill=tk.X, padx=8, pady=8)

        self.boton_agregar = tk.Button(top, text="Agregar")
        self.boton_modificar = tk.Button(top, text="Modificar")
        self.boton_eliminar = tk.Button(top, text="Eliminar")
        self.boton_cerrar = tk.Button(top, text="Salir", command=self.dispose)
        for button in (self.boton_agregar, self.boton_modificar, self.boton_eliminar, self.boton_cerrar):
            button.pack(side=tk.LEFT, padx=3)

        self.tabla = ttk.Treeview(
            self,
            columns=("id", "insumo", "categoria", "id_categoria"),
            show="headings",
            selectmode="browse",
        )
        self.tabla.heading("id", text="Id")
        self.tabla.heading("insumo", text="Insumo")
        self.tabla.heading("categoria", text="Categoria")
        self.tabla.heading("id_categoria", text="IdCategoria")
        self.tabla.column("id", width=100)
        self.tabla.column("insumo", width=240)
        self.tabla.column("categoria", width=240)
        self.tabla.column("id_categoria", width=120)
        self.tabla.pack(fill=tk.BOTH, expand=True, padx=8, pady=(0, 8))

        self.protocol("WM_DELETE_WINDOW", self.dispose)

    def set_on_close(self, callback) -> None:
        self._on_close = callback

    def dispose(self) -> None:
        if self._on_close is not None:
            self._on_close()
            self._on_close = None
        self.destroy()

    def set_insumos(self, insumos: list[Insumo], categorias: dict[str, str]) -> None:
        for item in self.tabla.get_children():
            self.tabla.delete(item)
        for insumo in insumos:
            self.tabla.insert(
                "",
                tk.END,
                values=(insumo.id, insumo.nombre, categorias.get(insumo.id_categoria, ""), insumo.id_categoria),
            )

    def get_selected_id(self) -> str | None:
        selected = self.tabla.selection()
        if not selected:
            return None
        return str(self.tabla.item(selected[0], "values")[0])

    def get_selected_nombre(self) -> str | None:
        selected = self.tabla.selection()
        if not selected:
            return None
        return str(self.tabla.item(selected[0], "values")[1])

    def get_selected_id_categoria(self) -> str | None:
        selected = self.tabla.selection()
        if not selected:
            return None
        return str(self.tabla.item(selected[0], "values")[3])
