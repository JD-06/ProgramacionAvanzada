from pathlib import Path

from PA_a2251330007_practica2_python.model.storage_type import StorageType


class ConfigRepository:
    def __init__(self) -> None:
        self.data_dir = Path.cwd() / "data"
        self.config_file = self.data_dir / "config.properties"
        self.data_dir.mkdir(parents=True, exist_ok=True)

    def get_storage_type(self) -> StorageType:
        if not self.config_file.exists():
            self.set_storage_type(StorageType.TXT)
            return StorageType.TXT

        storage_value = None
        with self.config_file.open("r", encoding="utf-8") as fh:
            for raw_line in fh:
                line = raw_line.strip()
                if not line or line.startswith("#") or "=" not in line:
                    continue
                key, value = line.split("=", 1)
                if key.strip() == "storage":
                    storage_value = value.strip()
                    break
        return StorageType.from_string(storage_value)

    def set_storage_type(self, storage_type: StorageType) -> None:
        with self.config_file.open("w", encoding="utf-8") as fh:
            fh.write(f"storage={storage_type.value}\n")
