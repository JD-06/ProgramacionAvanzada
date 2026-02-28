import re

from PA_a2251330007_practica2_python.model.categoria import Categoria
from PA_a2251330007_practica2_python.model.insumo import Insumo
from PA_a2251330007_practica2_python.model.obra import Obra
from PA_a2251330007_practica2_python.model.storage_type import StorageType
from PA_a2251330007_practica2_python.repository.config_repository import ConfigRepository
from PA_a2251330007_practica2_python.repository.data_repository import DataRepository


class AppService:
    def __init__(self) -> None:
        self.config_repository = ConfigRepository()
        self.data_repository = DataRepository()
        self.storage_type = self.config_repository.get_storage_type()
        self.categorias: list[Categoria] = []
        self.insumos: list[Insumo] = []
        self.obras: list[Obra] = []
        self.reload()

    def get_storage_type(self) -> StorageType:
        return self.storage_type

    def set_storage_type(self, storage_type: StorageType) -> None:
        self.storage_type = storage_type
        self.config_repository.set_storage_type(storage_type)
        self._save_all()

    def reload(self) -> None:
        self.categorias = self.data_repository.load_categorias(self.storage_type)
        self.insumos = self.data_repository.load_insumos(self.storage_type)
        self.obras = self.data_repository.load_obras(self.storage_type)

    def get_categorias(self) -> list[Categoria]:
        return list(self.categorias)

    def get_insumos(self) -> list[Insumo]:
        return list(self.insumos)

    def get_obras(self) -> list[Obra]:
        return list(self.obras)

    def add_categoria(self, categoria: Categoria) -> bool:
        if self._find_categoria(categoria.id) is not None:
            return False
        self.categorias.append(categoria)
        self._save_categorias()
        return True

    def update_categoria(self, categoria: Categoria) -> bool:
        current = self._find_categoria(categoria.id)
        if current is None:
            return False
        current.nombre = categoria.nombre
        self._save_categorias()
        return True

    def delete_categoria(self, categoria_id: str) -> bool:
        for insumo in self.insumos:
            if insumo.id_categoria == categoria_id:
                return False
        original_len = len(self.categorias)
        self.categorias = [c for c in self.categorias if c.id != categoria_id]
        removed = len(self.categorias) != original_len
        if removed:
            self._save_categorias()
        return removed

    def add_insumo(self, insumo: Insumo) -> bool:
        if self._find_insumo(insumo.id) is not None or self._find_categoria(insumo.id_categoria) is None:
            return False
        self.insumos.append(insumo)
        self._save_insumos()
        return True

    def update_insumo(self, insumo: Insumo) -> bool:
        current = self._find_insumo(insumo.id)
        if current is None or self._find_categoria(insumo.id_categoria) is None:
            return False
        current.nombre = insumo.nombre
        current.id_categoria = insumo.id_categoria
        self._save_insumos()
        return True

    def delete_insumo(self, insumo_id: str) -> bool:
        original_len = len(self.insumos)
        self.insumos = [i for i in self.insumos if i.id != insumo_id]
        removed = len(self.insumos) != original_len
        if removed:
            self._save_insumos()
        return removed

    def generate_next_obra_id(self) -> str:
        max_value = 0
        for obra in self.obras:
            numeric = re.sub(r"\D", "", obra.id)
            if numeric:
                max_value = max(max_value, int(numeric))
        return f"OBR{max_value + 1:03d}"

    def add_obra(self, obra: Obra) -> bool:
        if self._find_obra(obra.id) is not None:
            return False
        self.obras.append(obra)
        self._save_obras()
        return True

    def update_obra(self, obra: Obra) -> bool:
        current = self._find_obra(obra.id)
        if current is None:
            return False
        current.nombre = obra.nombre
        current.descripcion = obra.descripcion
        self._save_obras()
        return True

    def delete_obra(self, obra_id: str) -> bool:
        original_len = len(self.obras)
        self.obras = [o for o in self.obras if o.id != obra_id]
        removed = len(self.obras) != original_len
        if removed:
            self._save_obras()
        return removed

    def categoria_nombre(self, categoria_id: str) -> str:
        categoria = self._find_categoria(categoria_id)
        return "" if categoria is None else categoria.nombre

    def _find_categoria(self, categoria_id: str) -> Categoria | None:
        return next((categoria for categoria in self.categorias if categoria.id == categoria_id), None)

    def _find_insumo(self, insumo_id: str) -> Insumo | None:
        return next((insumo for insumo in self.insumos if insumo.id == insumo_id), None)

    def _find_obra(self, obra_id: str) -> Obra | None:
        return next((obra for obra in self.obras if obra.id == obra_id), None)

    def _save_categorias(self) -> None:
        self.data_repository.save_categorias(self.storage_type, self.categorias)

    def _save_insumos(self) -> None:
        self.data_repository.save_insumos(self.storage_type, self.insumos)

    def _save_obras(self) -> None:
        self.data_repository.save_obras(self.storage_type, self.obras)

    def _save_all(self) -> None:
        self._save_categorias()
        self._save_insumos()
        self._save_obras()
