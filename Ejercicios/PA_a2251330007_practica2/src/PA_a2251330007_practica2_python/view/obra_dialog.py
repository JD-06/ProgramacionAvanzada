import tkinter as tk
from tkinter import messagebox


class ObraDialog(tk.Toplevel):
    def __init__(self, owner: tk.Misc, title: str, obra_id: str, nombre: str, descripcion: str) -> None:
        super().__init__(owner)
        self.title(title)
        self.geometry("450x290")
        self.transient(owner)
        self.grab_set()

        self._accepted = False
        self._id = obra_id

        tk.Label(self, text="Obra").grid(row=0, column=0, padx=8, pady=8, sticky="w")
        self.campo_nombre = tk.Entry(self)
        self.campo_nombre.insert(0, nombre or "")
        self.campo_nombre.grid(row=0, column=1, padx=8, pady=8, sticky="ew")

        tk.Label(self, text="Descripcion").grid(row=1, column=0, padx=8, pady=8, sticky="nw")
        self.campo_descripcion = tk.Text(self, height=6)
        self.campo_descripcion.insert("1.0", descripcion or "")
        self.campo_descripcion.grid(row=1, column=1, padx=8, pady=8, sticky="nsew")

        tk.Button(self, text="Guardar", command=self._on_save).grid(row=2, column=0, padx=8, pady=10, sticky="ew")
        tk.Button(self, text="Cancelar", command=self.destroy).grid(row=2, column=1, padx=8, pady=10, sticky="ew")

        self.columnconfigure(1, weight=1)
        self.rowconfigure(1, weight=1)

    def show(self) -> None:
        self.wait_window(self)

    def _on_save(self) -> None:
        if not self.get_nombre_value() or not self.get_descripcion_value():
            messagebox.showwarning("Validacion", "Completa todos los campos", parent=self)
            return
        self._accepted = True
        self.destroy()

    def is_accepted(self) -> bool:
        return self._accepted

    def get_id_value(self) -> str:
        return self._id

    def get_nombre_value(self) -> str:
        return self.campo_nombre.get().strip()

    def get_descripcion_value(self) -> str:
        return self.campo_descripcion.get("1.0", tk.END).strip()
