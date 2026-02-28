from pathlib import Path
import xml.etree.ElementTree as ET

from PA_a2251330007_practica2_python.model.categoria import Categoria
from PA_a2251330007_practica2_python.model.insumo import Insumo
from PA_a2251330007_practica2_python.model.obra import Obra
from PA_a2251330007_practica2_python.model.storage_type import StorageType


class DataRepository:
    def __init__(self) -> None:
        self.data_dir = Path.cwd() / "data"
        self.data_dir.mkdir(parents=True, exist_ok=True)

    def load_categorias(self, storage_type: StorageType) -> list[Categoria]:
        return self._load_categorias_xml() if storage_type == StorageType.XML else self._load_categorias_txt()

    def save_categorias(self, storage_type: StorageType, categorias: list[Categoria]) -> None:
        if storage_type == StorageType.XML:
            self._save_categorias_xml(categorias)
        else:
            self._save_categorias_txt(categorias)

    def load_insumos(self, storage_type: StorageType) -> list[Insumo]:
        return self._load_insumos_xml() if storage_type == StorageType.XML else self._load_insumos_txt()

    def save_insumos(self, storage_type: StorageType, insumos: list[Insumo]) -> None:
        if storage_type == StorageType.XML:
            self._save_insumos_xml(insumos)
        else:
            self._save_insumos_txt(insumos)

    def load_obras(self, storage_type: StorageType) -> list[Obra]:
        return self._load_obras_xml() if storage_type == StorageType.XML else self._load_obras_txt()

    def save_obras(self, storage_type: StorageType, obras: list[Obra]) -> None:
        if storage_type == StorageType.XML:
            self._save_obras_xml(obras)
        else:
            self._save_obras_txt(obras)

    def _load_categorias_txt(self) -> list[Categoria]:
        categorias: list[Categoria] = []
        for line in self._read_lines(self.data_dir / "categorias.txt"):
            parts = line.split(",", 1)
            if len(parts) == 2:
                categorias.append(Categoria(parts[0].strip(), parts[1].strip()))
        return categorias

    def _save_categorias_txt(self, categorias: list[Categoria]) -> None:
        lines = [f"{categoria.id},{categoria.nombre}" for categoria in categorias]
        self._write_lines(self.data_dir / "categorias.txt", lines)

    def _load_insumos_txt(self) -> list[Insumo]:
        insumos: list[Insumo] = []
        for line in self._read_lines(self.data_dir / "insumos.txt"):
            parts = line.split(",", 2)
            if len(parts) == 3:
                insumos.append(Insumo(parts[0].strip(), parts[1].strip(), parts[2].strip()))
        return insumos

    def _save_insumos_txt(self, insumos: list[Insumo]) -> None:
        lines = [f"{insumo.id},{insumo.nombre},{insumo.id_categoria}" for insumo in insumos]
        self._write_lines(self.data_dir / "insumos.txt", lines)

    def _load_obras_txt(self) -> list[Obra]:
        obras: list[Obra] = []
        for line in self._read_lines(self.data_dir / "obras.txt"):
            parts = line.split(",", 2)
            if len(parts) == 3:
                obras.append(Obra(parts[0].strip(), parts[1].strip(), parts[2].strip()))
        return obras

    def _save_obras_txt(self, obras: list[Obra]) -> None:
        lines = [f"{obra.id},{obra.nombre},{obra.descripcion}" for obra in obras]
        self._write_lines(self.data_dir / "obras.txt", lines)

    def _load_categorias_xml(self) -> list[Categoria]:
        root = self._load_xml_root(self.data_dir / "categorias.xml")
        if root is None:
            return []
        categorias: list[Categoria] = []
        for element in root.findall("categoria"):
            categorias.append(Categoria(self._child_text(element, "id"), self._child_text(element, "nombre")))
        return categorias

    def _save_categorias_xml(self, categorias: list[Categoria]) -> None:
        root = ET.Element("categorias")
        for categoria in categorias:
            item = ET.SubElement(root, "categoria")
            ET.SubElement(item, "id").text = categoria.id or ""
            ET.SubElement(item, "nombre").text = categoria.nombre or ""
        self._write_xml(root, self.data_dir / "categorias.xml")

    def _load_insumos_xml(self) -> list[Insumo]:
        root = self._load_xml_root(self.data_dir / "insumos.xml")
        if root is None:
            return []
        insumos: list[Insumo] = []
        for element in root.findall("insumo"):
            insumos.append(
                Insumo(
                    self._child_text(element, "id"),
                    self._child_text(element, "nombre"),
                    self._child_text(element, "idCategoria"),
                )
            )
        return insumos

    def _save_insumos_xml(self, insumos: list[Insumo]) -> None:
        root = ET.Element("insumos")
        for insumo in insumos:
            item = ET.SubElement(root, "insumo")
            ET.SubElement(item, "id").text = insumo.id or ""
            ET.SubElement(item, "nombre").text = insumo.nombre or ""
            ET.SubElement(item, "idCategoria").text = insumo.id_categoria or ""
        self._write_xml(root, self.data_dir / "insumos.xml")

    def _load_obras_xml(self) -> list[Obra]:
        root = self._load_xml_root(self.data_dir / "obras.xml")
        if root is None:
            return []
        obras: list[Obra] = []
        for element in root.findall("obra"):
            obras.append(
                Obra(
                    self._child_text(element, "id"),
                    self._child_text(element, "nombre"),
                    self._child_text(element, "descripcion"),
                )
            )
        return obras

    def _save_obras_xml(self, obras: list[Obra]) -> None:
        root = ET.Element("obras")
        for obra in obras:
            item = ET.SubElement(root, "obra")
            ET.SubElement(item, "id").text = obra.id or ""
            ET.SubElement(item, "nombre").text = obra.nombre or ""
            ET.SubElement(item, "descripcion").text = obra.descripcion or ""
        self._write_xml(root, self.data_dir / "obras.xml")

    @staticmethod
    def _read_lines(path: Path) -> list[str]:
        if not path.exists():
            return []
        return path.read_text(encoding="utf-8").splitlines()

    @staticmethod
    def _write_lines(path: Path, lines: list[str]) -> None:
        path.write_text("\n".join(lines), encoding="utf-8")

    @staticmethod
    def _load_xml_root(path: Path) -> ET.Element | None:
        if not path.exists():
            return None
        return ET.parse(path).getroot()

    @staticmethod
    def _write_xml(root: ET.Element, path: Path) -> None:
        tree = ET.ElementTree(root)
        ET.indent(tree, space="    ", level=0)
        tree.write(path, encoding="utf-8", xml_declaration=True)

    @staticmethod
    def _child_text(parent: ET.Element, tag: str) -> str:
        child = parent.find(tag)
        return (child.text or "") if child is not None else ""
