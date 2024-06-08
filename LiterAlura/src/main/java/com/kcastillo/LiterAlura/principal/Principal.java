package com.kcastillo.LiterAlura.principal;

import com.kcastillo.LiterAlura.operations.Operations;

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
                System.out.println("\n***** Bienvenido al MENU principal de la API Gutendex *****");
                System.out.println("""
                        Ingrese la operación que desea realizar:
                                            
                        1) Buscar y registrar libro por título
                        2) Listar libros registrados
                        3) Listar libros por idioma
                        4) Listar autores registrados
                        5) Listar autores vivos en un determinado año
                        6) Salir
                                            
                        Opción:
                        """);

                int opcion = Integer.valueOf(teclado.nextLine());


                switch (opcion) {
                    case 1:
//                        operations.getAllBooksAPI().forEach(System.out::println);
                        operations.getBookByTitle();
                        break;
                    case 2:
                        operations.printAllRegisteredBooks();
                        break;
                    case 3:
                        operations.printBooksByLanguage();
                        break;
                    case 4:
                        operations.getAllRegisteredAuthors();
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
