import tkinter as tk
from tkinter import messagebox, ttk

from PA_a2251330007_practica2_python.model.categoria import Categoria


class InsumoDialog(tk.Toplevel):
    def __init__(
        self,
        owner: tk.Misc,
        title: str,
        insumo_id: str,
        nombre: str,
        selected_categoria_id: str | None,
        categorias: list[Categoria],
        editable_id: bool,
    ) -> None:
        super().__init__(owner)
        self.title(title)
        self.geometry("420x240")
        self.transient(owner)
        self.grab_set()

        self._accepted = False
        self._categoria_ids = [categoria.id for categoria in categorias]

        tk.Label(self, text="Id").grid(row=0, column=0, padx=8, pady=8, sticky="w")
        self.campo_id = tk.Entry(self)
        self.campo_id.insert(0, insumo_id or "")
        if not editable_id:
            self.campo_id.configure(state="disabled")
        self.campo_id.grid(row=0, column=1, padx=8, pady=8, sticky="ew")

        tk.Label(self, text="Insumo").grid(row=1, column=0, padx=8, pady=8, sticky="w")
        self.campo_nombre = tk.Entry(self)
        self.campo_nombre.insert(0, nombre or "")
        self.campo_nombre.grid(row=1, column=1, padx=8, pady=8, sticky="ew")

        tk.Label(self, text="Categoria").grid(row=2, column=0, padx=8, pady=8, sticky="w")
        self.combo_categorias = ttk.Combobox(self, state="readonly", values=[categoria.nombre for categoria in categorias])
        if selected_categoria_id in self._categoria_ids:
            self.combo_categorias.current(self._categoria_ids.index(selected_categoria_id))
        elif self._categoria_ids:
            self.combo_categorias.current(0)
        self.combo_categorias.grid(row=2, column=1, padx=8, pady=8, sticky="ew")

        tk.Button(self, text="Guardar", command=self._on_save).grid(row=3, column=0, padx=8, pady=10, sticky="ew")
        tk.Button(self, text="Cancelar", command=self.destroy).grid(row=3, column=1, padx=8, pady=10, sticky="ew")

        self.columnconfigure(1, weight=1)

    def show(self) -> None:
        self.wait_window(self)

    def _on_save(self) -> None:
        if not self.get_id_value() or not self.get_nombre_value() or self.get_categoria_id() is None:
            messagebox.showwarning("Validacion", "Completa todos los campos", parent=self)
            return
        self._accepted = True
        self.destroy()

    def is_accepted(self) -> bool:
        return self._accepted

    def get_id_value(self) -> str:
        return self.campo_id.get().strip()

    def get_nombre_value(self) -> str:
        return self.campo_nombre.get().strip()

    def get_categoria_id(self) -> str | None:
        index = self.combo_categorias.current()
        if index < 0 or index >= len(self._categoria_ids):
            return None
        return self._categoria_ids[index]
