@echo off
chcp 65001 > nul
echo Iniciando Sistema POS...

set LIBS=lib\gson-2.10.1.jar;lib\opencsv-5.7.1.jar;lib\commons-lang3-3.14.0.jar;lib\commons-text-1.11.0.jar;lib\commons-beanutils-1.9.4.jar;lib\commons-collections4-4.4.jar;lib\commons-logging-1.3.3.jar

java -cp out;%LIBS% com.tienda.App
