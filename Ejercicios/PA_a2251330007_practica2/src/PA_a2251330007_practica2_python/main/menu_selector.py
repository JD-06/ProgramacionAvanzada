import subprocess
import sys
import tkinter as tk
from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parents[2]

def _run_module(module_name: str) -> None:
    subprocess.Popen([sys.executable, "-m", module_name], cwd=str(PROJECT_ROOT))


class MenuSelector(tk.Tk):
    def __init__(self) -> None:
        super().__init__()
        self.title("Selector de Programas - Practica 2")
        self.geometry("400x500")

        tk.Label(self, text="Menu de Ejecucion", font=("Arial", 18, "bold")).pack(pady=14)

        buttons = [
            ("Ejecutar App (Principal)", "PA_a2251330007_practica2_python.main.app"),
            ("Ejecutar Practica 03_b", "PA_a2251330007_practica2_python.main.practica03_b"),
            ("Ejecutar Practica 05", "PA_a2251330007_practica2_python.main.practica05"),
            ("Ejecutar Practica Final", "PA_a2251330007_practica2_python.main.practica_final"),
            ("Ejecutar Practica Final 2", "PA_a2251330007_practica2_python.main.practica_final2"),
        ]

        for text, module in buttons:
            tk.Button(self, text=text, width=34, command=lambda m=module: _run_module(m)).pack(pady=6)

        tk.Button(self, text="Salir", width=34, command=self.destroy).pack(pady=10)


def main() -> None:
    MenuSelector().mainloop()


if __name__ == "__main__":
    main()
