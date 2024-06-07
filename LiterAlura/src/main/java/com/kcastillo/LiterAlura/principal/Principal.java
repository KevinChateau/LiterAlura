package com.kcastillo.LiterAlura.principal;

import com.kcastillo.LiterAlura.models.DatosLibros;
import com.kcastillo.LiterAlura.operations.Operations;
import com.kcastillo.LiterAlura.service.ConsumoAPI;
import com.kcastillo.LiterAlura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {

    private Scanner teclado;
    private Operations operations;


    public Principal() {
        teclado = new Scanner(System.in);
        operations = new Operations();
    }

    public void showMenu() {
        int exit = 0;


        try {


            do {
                System.out.println("***** Bienvenido al MENU principal de la API Gutendex *****");
                System.out.println("""
                        Ingrese la operación que desea realizar:
                                            
                        1) Buscar y registrar libro por título
                        2) Listar libros registrados
                        3) Listar autores registrados
                        4) Listar autores vivos en un determinado año
                        5) Listar libros por idioma
                        6) Salir
                                            
                        Opción:
                        """);

                int opcion = Integer.valueOf(teclado.nextLine());


                switch (opcion) {
                    case 1:
                        operations.getAllBooksAPI().forEach(System.out::println);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        exit = 1;
                        break;
                    default:
                        System.out.println("Opción fuera del menú, intente nuevamente");
                }


            } while (exit == 0);
        } catch (NumberFormatException nfe) {
            System.out.println("Entrada inválida\n" );
            nfe.printStackTrace();
        }
    }
}
