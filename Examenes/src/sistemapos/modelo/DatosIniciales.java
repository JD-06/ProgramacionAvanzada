package sistemapos.modelo;

import java.io.*;

/**
 * Carga datos de muestra en productos.csv si el archivo esta vacio.
 *
 * 11 categorias con minimo 6 productos cada una:
 *  1. Abarrotes (Despensa)        - Unitario
 *  2. Bebidas                     - Unitario / Por Volumen
 *  3. Lacteos y Huevo             - Unitario
 *  4. Frutas y Verduras           - Por Peso (kg)
 *  5. Carnes y Pescados           - Por Peso (kg)
 *  6. Salchichoneria              - Por Peso (kg)
 *  7. Panaderia y Tortilleria     - Unitario
 *  8. Limpieza del Hogar          - Unitario
 *  9. Cuidado Personal            - Unitario
 * 10. Snacks y Dulceria           - Unitario
 * 11. Mascotas                    - Unitario / Por Peso
 *
 * Formato CSV (12 campos):
 * id,codigo,nombre,descripcion,categoria,precioCompra,precioVenta,
 * stock,stockMinimo,estado,tipo,imagenRuta
 *
 * Uso: DatosIniciales.cargar() desde Main.java o el menu.
 */
public class DatosIniciales {

    public static void cargar() {
        File archivo = new File("productos.csv");
        if (archivo.exists() && archivo.length() > 0) {
            System.out.println("productos.csv ya tiene datos. No se sobreescribe.");
            return;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            // Formato: id,codigo,nombre,descripcion,categoria,
            //          precioCompra,precioVenta,stock,stockMin,estado,tipo,imagen

            // ---- 1. ABARROTES (Despensa) - Unitario ----
            pw.println("1,ABR001,Arroz 1kg,Arroz grano largo,Abarrotes (Despensa),12.0,18.5,100,10,Activo,Unitario,");
            pw.println("2,ABR002,Frijol Negro 1kg,Frijol negro de alta calidad,Abarrotes (Despensa),18.0,25.0,80,10,Activo,Unitario,");
            pw.println("3,ABR003,Aceite Vegetal 1L,Aceite para cocinar,Abarrotes (Despensa),22.0,35.0,60,8,Activo,Unitario,");
            pw.println("4,ABR004,Pasta Spaghetti 200g,Pasta de trigo,Abarrotes (Despensa),8.0,12.0,90,10,Activo,Unitario,");
            pw.println("5,ABR005,Sardinas en Lata,Sardinas en salsa de tomate,Abarrotes (Despensa),14.0,22.0,70,8,Activo,Unitario,");
            pw.println("6,ABR006,Sal de Mesa 1kg,Sal fina yodada,Abarrotes (Despensa),6.0,10.0,120,15,Activo,Unitario,");
            pw.println("7,ABR007,Azucar Blanca 1kg,Azucar refinada,Abarrotes (Despensa),14.0,22.0,100,12,Activo,Unitario,");

            // ---- 2. BEBIDAS - Unitario / Por Volumen ----
            pw.println("8,BEB001,Agua Purificada 1.5L,Agua sin gas,Bebidas,6.0,12.0,150,20,Activo,Unitario,");
            pw.println("9,BEB002,Refresco Cola 2L,Refresco carbonatado,Bebidas,15.0,25.0,80,10,Activo,Unitario,");
            pw.println("10,BEB003,Jugo de Naranja 1L,Jugo natural sin azucar anadida,Bebidas,18.0,28.0,60,8,Activo,Unitario,");
            pw.println("11,BEB004,Cafe Soluble 200g,Cafe instantaneo,Bebidas,35.0,55.0,40,5,Activo,Unitario,");
            pw.println("12,BEB005,Te Verde (25 sobres),Infusion de te verde,Bebidas,22.0,38.0,50,6,Activo,Unitario,");
            pw.println("13,BEB006,Cerveza Lata 355ml,Cerveza clara,Bebidas,14.0,22.0,200,25,Activo,Unitario,");
            pw.println("14,BEB007,Bebida Energetica 250ml,Bebida con cafeina,Bebidas,18.0,28.0,90,10,Activo,Unitario,");

            // ---- 3. LACTEOS Y HUEVO - Unitario ----
            pw.println("15,LAC001,Leche Entera 1L,Leche pasteurizada,Lacteos y Huevo,14.0,22.0,100,15,Activo,Unitario,");
            pw.println("16,LAC002,Huevo 30 piezas,Huevo blanco fresco,Lacteos y Huevo,55.0,85.0,60,8,Activo,Unitario,");
            pw.println("17,LAC003,Yogurt Natural 1kg,Yogurt sin azucar,Lacteos y Huevo,28.0,42.0,40,5,Activo,Unitario,");
            pw.println("18,LAC004,Mantequilla 90g,Mantequilla sin sal,Lacteos y Huevo,22.0,35.0,50,6,Activo,Unitario,");
            pw.println("19,LAC005,Crema Acida 200g,Crema para cocinar,Lacteos y Huevo,18.0,28.0,55,7,Activo,Unitario,");
            pw.println("20,LAC006,Gelatina Familiar,Postre de gelatina,Lacteos y Huevo,10.0,18.0,70,10,Activo,Unitario,");

            // ---- 4. FRUTAS Y VERDURAS - Por Peso (kg) ----
            pw.println("21,FRV001,Manzana,Manzana roja fresca,Frutas y Verduras,22.0,35.0,50,5,Activo,Por Peso (kg),");
            pw.println("22,FRV002,Platano,Platano tabasco,Frutas y Verduras,10.0,18.0,60,6,Activo,Por Peso (kg),");
            pw.println("23,FRV003,Tomate,Tomate bola fresco,Frutas y Verduras,15.0,25.0,40,4,Activo,Por Peso (kg),");
            pw.println("24,FRV004,Zanahoria,Zanahoria fresca,Frutas y Verduras,12.0,20.0,45,5,Activo,Por Peso (kg),");
            pw.println("25,FRV005,Papa,Papa blanca,Frutas y Verduras,14.0,22.0,80,8,Activo,Por Peso (kg),");
            pw.println("26,FRV006,Naranja,Naranja valenciana,Frutas y Verduras,16.0,28.0,70,7,Activo,Por Peso (kg),");
            pw.println("27,FRV007,Chayote,Chayote verde fresco,Frutas y Verduras,8.0,15.0,35,4,Activo,Por Peso (kg),");

            // ---- 5. CARNES Y PESCADOS - Por Peso (kg) ----
            pw.println("28,CAR001,Pechuga de Pollo,Pechuga sin hueso,Carnes y Pescados,60.0,90.0,30,3,Activo,Por Peso (kg),");
            pw.println("29,CAR002,Carne Molida de Res,Carne molida fresca,Carnes y Pescados,80.0,120.0,25,3,Activo,Por Peso (kg),");
            pw.println("30,CAR003,Costilla de Cerdo,Costilla ahumada,Carnes y Pescados,70.0,110.0,20,2,Activo,Por Peso (kg),");
            pw.println("31,CAR004,Filete de Tilapia,Tilapia sin espinas,Carnes y Pescados,50.0,80.0,15,2,Activo,Por Peso (kg),");
            pw.println("32,CAR005,Camaron Mediano,Camaron fresco pelado,Carnes y Pescados,120.0,180.0,10,1,Activo,Por Peso (kg),");
            pw.println("33,CAR006,Salmon en Rebanada,Salmon atlantico,Carnes y Pescados,150.0,220.0,8,1,Activo,Por Peso (kg),");

            // ---- 6. SALCHICHONERIA - Por Peso (kg) ----
            pw.println("34,SAL001,Jamon de Pavo,Jamon bajo en grasa,Salchichoneria,55.0,85.0,20,2,Activo,Por Peso (kg),");
            pw.println("35,SAL002,Salchicha Viena,Salchicha de pavo,Salchichoneria,48.0,75.0,25,3,Activo,Por Peso (kg),");
            pw.println("36,SAL003,Tocino Ahumado,Tocino en rebanadas,Salchichoneria,85.0,130.0,15,2,Activo,Por Peso (kg),");
            pw.println("37,SAL004,Queso Manchego,Queso manchego rebanado,Salchichoneria,120.0,180.0,12,2,Activo,Por Peso (kg),");
            pw.println("38,SAL005,Chorizo Rojo,Chorizo estilo espanol,Salchichoneria,78.0,120.0,18,2,Activo,Por Peso (kg),");
            pw.println("39,SAL006,Mortadela,Mortadela con aceitunas,Salchichoneria,42.0,65.0,22,3,Activo,Por Peso (kg),");

            // ---- 7. PANADERIA Y TORTILLERIA - Unitario ----
            pw.println("40,PAN001,Pan de Caja Blanco,Pan de caja grande,Panaderia y Tortilleria,24.0,38.0,30,4,Activo,Unitario,");
            pw.println("41,PAN002,Tortilla de Maiz 1kg,Tortilla fresca de maiz,Panaderia y Tortilleria,14.0,22.0,60,8,Activo,Unitario,");
            pw.println("42,PAN003,Bolillo (pieza),Pan frances fresco,Panaderia y Tortilleria,2.0,4.0,100,20,Activo,Unitario,");
            pw.println("43,PAN004,Concha de Pan Dulce,Pan dulce tipo concha,Panaderia y Tortilleria,3.0,6.0,80,15,Activo,Unitario,");
            pw.println("44,PAN005,Pan Integral 680g,Pan integral de avena,Panaderia y Tortilleria,28.0,42.0,25,4,Activo,Unitario,");
            pw.println("45,PAN006,Tortilla de Harina,Tortilla de harina grande,Panaderia y Tortilleria,18.0,28.0,45,6,Activo,Unitario,");

            // ---- 8. LIMPIEZA DEL HOGAR - Unitario ----
            pw.println("46,LIM001,Detergente en Polvo 1kg,Detergente para ropa,Limpieza del Hogar,35.0,55.0,40,5,Activo,Unitario,");
            pw.println("47,LIM002,Suavizante Ropa 800ml,Suavizante lavanda,Limpieza del Hogar,30.0,48.0,35,4,Activo,Unitario,");
            pw.println("48,LIM003,Desinfectante 1L,Desinfectante multiusos,Limpieza del Hogar,24.0,38.0,50,6,Activo,Unitario,");
            pw.println("49,LIM004,Papel Higienico 4 rollos,Papel doble hoja,Limpieza del Hogar,28.0,42.0,80,10,Activo,Unitario,");
            pw.println("50,LIM005,Esponja para Trastes,Esponja con fibra,Limpieza del Hogar,10.0,18.0,100,15,Activo,Unitario,");
            pw.println("51,LIM006,Blanqueador 1L,Blanqueador con cloro,Limpieza del Hogar,16.0,28.0,60,8,Activo,Unitario,");

            // ---- 9. CUIDADO PERSONAL - Unitario ----
            pw.println("52,CUI001,Shampoo Neutro 400ml,Shampoo para todo tipo de cabello,Cuidado Personal,35.0,55.0,40,5,Activo,Unitario,");
            pw.println("53,CUI002,Jabon de Tocador,Jabon antibacterial,Cuidado Personal,10.0,18.0,80,10,Activo,Unitario,");
            pw.println("54,CUI003,Pasta Dental 75ml,Pasta con fluor,Cuidado Personal,20.0,32.0,60,8,Activo,Unitario,");
            pw.println("55,CUI004,Desodorante Roll-on,Desodorante 24 horas,Cuidado Personal,28.0,45.0,50,6,Activo,Unitario,");
            pw.println("56,CUI005,Crema Corporal 250g,Crema hidratante,Cuidado Personal,40.0,65.0,30,4,Activo,Unitario,");
            pw.println("57,CUI006,Papel Facial 100pzs,Kleenex suave,Cuidado Personal,20.0,32.0,45,5,Activo,Unitario,");

            // ---- 10. SNACKS Y DULCERIA - Unitario ----
            pw.println("58,SNA001,Papas Sabor Limon,Papas fritas limon,Snacks y Dulceria,10.0,18.0,90,12,Activo,Unitario,");
            pw.println("59,SNA002,Galletas Marias,Galletas clasicas,Snacks y Dulceria,12.0,22.0,70,10,Activo,Unitario,");
            pw.println("60,SNA003,Chocolate de Leche,Barra de chocolate,Snacks y Dulceria,16.0,28.0,60,8,Activo,Unitario,");
            pw.println("61,SNA004,Dulces Surtidos 100g,Dulces mexicanos,Snacks y Dulceria,8.0,15.0,100,15,Activo,Unitario,");
            pw.println("62,SNA005,Cacahuates Saladitos,Cacahuates con sal,Snacks y Dulceria,6.0,12.0,80,10,Activo,Unitario,");
            pw.println("63,SNA006,Palomitas Microondas,Palomitas con mantequilla,Snacks y Dulceria,12.0,20.0,55,7,Activo,Unitario,");

            // ---- 11. MASCOTAS - Unitario / Por Peso ----
            pw.println("64,MAS001,Alimento Perro Premium 1kg,Croquetas adulto raza mediana,Mascotas,28.0,45.0,20,3,Activo,Por Peso (kg),");
            pw.println("65,MAS002,Alimento Gato Adulto 1kg,Croquetas para gato adulto,Mascotas,30.0,48.0,18,2,Activo,Por Peso (kg),");
            pw.println("66,MAS003,Croquetas Cachorro 1kg,Alimento cachorro raza pequeña,Mascotas,32.0,52.0,15,2,Activo,Por Peso (kg),");
            pw.println("67,MAS004,Arena para Gatos 5kg,Arena aglomerante,Mascotas,40.0,65.0,25,3,Activo,Unitario,");
            pw.println("68,MAS005,Hueso de Nylon Mediano,Juguete para perros,Mascotas,22.0,35.0,30,4,Activo,Unitario,");
            pw.println("69,MAS006,Snack Dental Perro,Premio dental para perros,Mascotas,18.0,28.0,40,5,Activo,Unitario,");

        } catch (IOException e) {
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
        }
        System.out.println("Datos iniciales cargados: 11 categorias, 69 productos.");
    }
}
