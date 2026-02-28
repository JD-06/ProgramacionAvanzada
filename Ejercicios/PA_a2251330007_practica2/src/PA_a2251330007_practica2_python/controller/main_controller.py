from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.controller.categoria_controller import CategoriaController
from PA_a2251330007_practica2_python.controller.insumo_controller import InsumoController
from PA_a2251330007_practica2_python.controller.obra_controller import ObraController
from PA_a2251330007_practica2_python.model.storage_type import StorageType
from PA_a2251330007_practica2_python.view.categoria_internal_frame import CategoriaInternalFrame
from PA_a2251330007_practica2_python.view.insumo_internal_frame import InsumoInternalFrame
from PA_a2251330007_practica2_python.view.menu_principal_frame import MenuPrincipalFrame
from PA_a2251330007_practica2_python.view.obra_internal_frame import ObraInternalFrame


class MainController:
    def __init__(self, view: MenuPrincipalFrame, service: AppService) -> None:
        self.view = view
        self.service = service

        self.view.apply_storage_type(self.service.get_storage_type())
        self.view.set_menu_commands(
            self.open_categorias,
            self.open_insumos,
            self.open_obras,
            lambda: self.change_storage(StorageType.TXT),
            lambda: self.change_storage(StorageType.XML),
            self.view.destroy,
        )

    def change_storage(self, storage_type: StorageType) -> None:
        self.service.set_storage_type(storage_type)
        self.view.apply_storage_type(storage_type)

    def open_categorias(self) -> None:
        frame = CategoriaInternalFrame(self.view)
        CategoriaController(frame, self.service)
        self._open_frame(frame)

    def open_insumos(self) -> None:
        frame = InsumoInternalFrame(self.view)
        InsumoController(frame, self.service)
        self._open_frame(frame)

    def open_obras(self) -> None:
        frame = ObraInternalFrame(self.view)
        ObraController(frame, self.service)
        self._open_frame(frame)

    def _open_frame(self, frame) -> None:
        self.view.set_main_actions_enabled(False)
        frame.set_on_close(lambda: self.view.set_main_actions_enabled(True))
        frame.focus_force()
