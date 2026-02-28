from PA_a2251330007_practica2_python.controller.categoria_controller import CategoriaController
from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.view.categoria_internal_frame import CategoriaInternalFrame


class Practica03C(CategoriaInternalFrame):
    def __init__(self, service: AppService, master=None) -> None:
        super().__init__(master)
        CategoriaController(self, service)
