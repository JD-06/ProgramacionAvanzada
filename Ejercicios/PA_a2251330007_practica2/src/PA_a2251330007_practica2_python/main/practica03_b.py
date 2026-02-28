import tkinter as tk

from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.controller.categoria_controller import CategoriaController
from PA_a2251330007_practica2_python.model.storage_type import StorageType
from PA_a2251330007_practica2_python.view.categoria_internal_frame import CategoriaInternalFrame


def main() -> None:
    service = AppService()
    service.set_storage_type(StorageType.TXT)

    root = tk.Tk()
    root.withdraw()
    child = CategoriaInternalFrame(root)
    child.title("Parte1 - Practica03_b")
    CategoriaController(child, service)
    child.set_on_close(root.destroy)
    root.mainloop()


if __name__ == "__main__":
    main()
