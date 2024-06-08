package com.kcastillo.LiterAlura.operations;

import com.kcastillo.LiterAlura.models.DatosLibro;
import com.kcastillo.LiterAlura.models.DatosLibroRecord;
import com.kcastillo.LiterAlura.models.DatosLibros;
import com.kcastillo.LiterAlura.service.ConsumoAPI;
import com.kcastillo.LiterAlura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Operations {

    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI;
    private ConvierteDatos conversorJsonClass;
    private Scanner scanner;
    private List<DatosLibro> myBooks;


    public Operations() {
        consumoAPI = new ConsumoAPI();
        conversorJsonClass = new ConvierteDatos();
        scanner = new Scanner(System.in);
        myBooks = new ArrayList<>();
    }

    public List<DatosLibroRecord> getAllBooksAPI() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversorJsonClass.obtenerDatos(json, DatosLibros.class);

        return datos.resultados();
    }

    public void getBookByTitle() {

        System.out.println("Ingrese el nombre del libro: ");
        var title = scanner.nextLine();
        //Replace spaces in "+"
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + title.replace(" ","+"));
        var datos = conversorJsonClass.obtenerDatos(json, DatosLibros.class);
        System.out.println(datos);
//        System.out.println(datos.resultados().get(0).autor());
//        System.out.println(datos.resultados().get(0).autor().get(0));

        //To find the first book that contains user title
        Optional<DatosLibroRecord> bookRecord = datos.resultados().stream()
                .filter(datosLibroRecord -> datosLibroRecord.titulo().toLowerCase().contains(title.toLowerCase()))
                .findFirst();

        if (bookRecord.isPresent()) {
            System.out.println("-------------------------------");
            System.out.println("Libro encontrado: ");
            DatosLibro myBook = new DatosLibro(bookRecord.get());
            System.out.println(myBook);
            System.out.println(myBook.getAutor()); //******
            System.out.println("¿Desea registrar este libro [s/n]?");
            if(scanner.nextLine().equalsIgnoreCase("s")){
                myBooks.add(myBook);
                System.out.println("Libro registrado con éxito");
            }

        } else {
            System.out.println("-------------------------------");
            System.out.println("Libro no encontrado");
        }
    }

    public void printAllRegistredBooks(){
        myBooks.forEach(System.out::println);
    }

    public void printBooksByLanguage() {
        //DB is needed to make this method
    }
}
