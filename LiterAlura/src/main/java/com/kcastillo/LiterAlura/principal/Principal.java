package com.kcastillo.LiterAlura.principal;

import java.util.Scanner;

public class Principal {

    private Scanner teclado;

    public Principal() {
        teclado = new Scanner(System.in);
    }

    public void showMenu() {
        int exit = 0;

        do {
            System.out.println("***** Bienvenido al MENU principal de la API Gutendex *****");
            System.out.println("""
                    Ingrese la operación que desea realizar:
                                        
                    1) Buscar libro por título
                    2) Listar libros registrados
                    3) Listar autores registrados
                    4) Listar autores vivos en un determinado año
                    5) Listar libros por idioma
                    6) Salir
                                        
                    Opción:
                    """);
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6: exit = 1;
                    break;
            }


        } while (exit == 0);

    }
}
