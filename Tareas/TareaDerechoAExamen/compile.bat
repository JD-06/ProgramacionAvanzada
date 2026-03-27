@echo off
chcp 65001 > nul
echo ═══════════════════════════════════════════
echo   Sistema POS — Compilando...
echo ═══════════════════════════════════════════

set LIBS=lib\gson-2.10.1.jar;lib\opencsv-5.7.1.jar;lib\commons-lang3-3.14.0.jar;lib\commons-text-1.11.0.jar;lib\commons-beanutils-1.9.4.jar;lib\commons-collections4-4.4.jar;lib\commons-logging-1.3.3.jar

if not exist out mkdir out

javac -encoding UTF-8 -cp %LIBS% -d out ^
  src\com\tienda\App.java ^
  src\com\tienda\model\*.java ^
  src\com\tienda\util\*.java ^
  src\com\tienda\view\*.java ^
  src\com\tienda\controller\*.java

if %ERRORLEVEL% == 0 (
    echo.
    echo ✔  Compilación exitosa.
) else (
    echo.
    echo ✘  Errores en la compilación.
)
pause
