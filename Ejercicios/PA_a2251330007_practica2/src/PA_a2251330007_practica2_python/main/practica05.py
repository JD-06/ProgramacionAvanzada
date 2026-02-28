import tkinter as tk

from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.view.practica03_c import Practica03C


class Practica05(tk.Tk):
    def __init__(self) -> None:
        super().__init__()
        self.service = AppService()

        self.title("Parte2 - Practica05")
        self.geometry("900x620")

        menu_bar = tk.Menu(self)
        self.config(menu=menu_bar)

        self.menu_configuracion = tk.Menu(menu_bar, tearoff=0)
        self.menu_configuracion.add_command(label="Categorias", command=self.abrir_categorias)

        self.menu_salir = tk.Menu(menu_bar, tearoff=0)
        self.menu_salir.add_command(label="Cerrar", command=self.destroy)

        menu_bar.add_cascade(label="Configuracion", menu=self.menu_configuracion)
        menu_bar.add_cascade(label="Salir", menu=self.menu_salir)

    def abrir_categorias(self) -> None:
        child = Practica03C(self.service, self)
        self.menu_configuracion.entryconfig("Categorias", state=tk.DISABLED)
        child.set_on_close(lambda: self.menu_configuracion.entryconfig("Categorias", state=tk.NORMAL))


def main() -> None:
    Practica05().mainloop()


if __name__ == "__main__":
    main()
