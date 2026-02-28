from PA_a2251330007_practica2_python.controller.app_service import AppService
from PA_a2251330007_practica2_python.controller.main_controller import MainController
from PA_a2251330007_practica2_python.view.menu_principal_frame import MenuPrincipalFrame


def main() -> None:
    service = AppService()
    frame = MenuPrincipalFrame()
    MainController(frame, service)
    frame.mainloop()


if __name__ == "__main__":
    main()
