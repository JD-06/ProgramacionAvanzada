from enum import Enum


class StorageType(Enum):
    TXT = "TXT"
    XML = "XML"

    @staticmethod
    def from_string(value: str | None) -> "StorageType":
        if value is None:
            return StorageType.TXT
        return StorageType.XML if value.strip().upper() == "XML" else StorageType.TXT
