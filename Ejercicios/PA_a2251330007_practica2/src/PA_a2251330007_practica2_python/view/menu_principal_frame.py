import tkinter as tk

from PA_a2251330007_practica2_python.model.storage_type import StorageType


class MenuPrincipalFrame(tk.Tk):
    def __init__(self) -> None:
        super().__init__()
        self.title("PA_a2251330007_practica2_python")
        self.geometry("1050x700")

        self.storage_var = tk.StringVar(value=StorageType.TXT.value)

        self.menu_bar = tk.Menu(self)
        self.config(menu=self.menu_bar)

        self.menu_operacion = tk.Menu(self.menu_bar, tearoff=0)
        self.menu_operacion.add_command(label="Categorias")
        self.menu_operacion.add_command(label="Insumos")
        self.menu_operacion.add_command(label="Obras")

        self.menu_configuracion = tk.Menu(self.menu_bar, tearoff=0)
        self.menu_configuracion.add_radiobutton(label="TXT", variable=self.storage_var, value=StorageType.TXT.value)
        self.menu_configuracion.add_radiobutton(label="XML", variable=self.storage_var, value=StorageType.XML.value)

        self.menu_salir = tk.Menu(self.menu_bar, tearoff=0)
        self.menu_salir.add_command(label="Cerrar")

        self.menu_bar.add_cascade(label="Operacion", menu=self.menu_operacion)
        self.menu_bar.add_cascade(label="Configuracion", menu=self.menu_configuracion)
        self.menu_bar.add_cascade(label="Salir", menu=self.menu_salir)

    def set_menu_commands(
        self,
        categorias_cb,
        insumos_cb,
        obras_cb,
        txt_cb,
        xml_cb,
        salir_cb,
    ) -> None:
        self.menu_operacion.entryconfig("Categorias", command=categorias_cb)
        self.menu_operacion.entryconfig("Insumos", command=insumos_cb)
        self.menu_operacion.entryconfig("Obras", command=obras_cb)
        self.menu_configuracion.entryconfig("TXT", command=txt_cb)
        self.menu_configuracion.entryconfig("XML", command=xml_cb)
        self.menu_salir.entryconfig("Cerrar", command=salir_cb)

    def set_main_actions_enabled(self, enabled: bool) -> None:
        state = tk.NORMAL if enabled else tk.DISABLED
        self.menu_operacion.entryconfig("Categorias", state=state)
        self.menu_operacion.entryconfig("Insumos", state=state)
        self.menu_operacion.entryconfig("Obras", state=state)
        self.menu_configuracion.entryconfig("TXT", state=state)
        self.menu_configuracion.entryconfig("XML", state=state)

    def apply_storage_type(self, storage_type: StorageType) -> None:
        self.storage_var.set(storage_type.value)
