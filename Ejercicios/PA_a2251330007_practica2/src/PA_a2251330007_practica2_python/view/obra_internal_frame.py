import tkinter as tk
from tkinter import ttk

from PA_a2251330007_practica2_python.model.obra import Obra


class ObraInternalFrame(tk.Toplevel):
    def __init__(self, master: tk.Misc | None = None) -> None:
        super().__init__(master)
        self.title("Obras")
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

        self.tabla = ttk.Treeview(self, columns=("id", "obra", "descripcion"), show="headings", selectmode="browse")
        self.tabla.heading("id", text="Id")
        self.tabla.heading("obra", text="Obra")
        self.tabla.heading("descripcion", text="Descripcion")
        self.tabla.column("id", width=100)
        self.tabla.column("obra", width=220)
        self.tabla.column("descripcion", width=380)
        self.tabla.pack(fill=tk.BOTH, expand=True, padx=8, pady=(0, 8))

        self.protocol("WM_DELETE_WINDOW", self.dispose)

    def set_on_close(self, callback) -> None:
        self._on_close = callback

    def dispose(self) -> None:
        if self._on_close is not None:
            self._on_close()
            self._on_close = None
        self.destroy()

    def set_obras(self, obras: list[Obra]) -> None:
        for item in self.tabla.get_children():
            self.tabla.delete(item)
        for obra in obras:
            self.tabla.insert("", tk.END, values=(obra.id, obra.nombre, obra.descripcion))

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

    def get_selected_descripcion(self) -> str | None:
        selected = self.tabla.selection()
        if not selected:
            return None
        return str(self.tabla.item(selected[0], "values")[2])
