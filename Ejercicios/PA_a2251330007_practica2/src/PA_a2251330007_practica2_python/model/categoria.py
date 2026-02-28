from dataclasses import dataclass


@dataclass
class Categoria:
    id: str
    nombre: str

    def __str__(self) -> str:
        return self.nombre
