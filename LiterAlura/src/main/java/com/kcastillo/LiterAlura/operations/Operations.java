package com.kcastillo.LiterAlura.operations;

import com.kcastillo.LiterAlura.models.Autor;
import com.kcastillo.LiterAlura.models.Libro;
import com.kcastillo.LiterAlura.models.DatosLibroRecord;
import com.kcastillo.LiterAlura.models.DatosLibros;
import com.kcastillo.LiterAlura.repository.AutorRepository;
import com.kcastillo.LiterAlura.repository.LibroRepository;
import com.kcastillo.LiterAlura.service.ConsumoAPI;
import com.kcastillo.LiterAlura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Operations {

    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI;
    private ConvierteDatos conversorJsonClass;
    private Scanner scanner;
    private LibroRepository bookRepository;
    private AutorRepository autorRepository;
    private List<Libro> myBooks = new ArrayList<>();;
    private List<Autor> myAuthors = new ArrayList<>();;

    public Operations(LibroRepository bookRepository, AutorRepository autorRepository) {
        consumoAPI = new ConsumoAPI();
        conversorJsonClass = new ConvierteDatos();
        scanner = new Scanner(System.in);
        this.bookRepository = bookRepository;
        this.autorRepository = autorRepository;
        myBooks = bookRepository.findAll();
        myAuthors = autorRepository.findAll();
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
//        System.out.println(datos);
//        System.out.println(datos.resultados().get(0).autor());
//        System.out.println(datos.resultados().get(0).autor().get(0));

        //To find the first book that contains user title
        Optional<DatosLibroRecord> bookRecord = datos.resultados().stream()
                .filter(datosLibroRecord -> datosLibroRecord.titulo().toLowerCase().contains(title.toLowerCase()))
                .findFirst();

        if (bookRecord.isPresent()) {
            System.out.println("-------------------------------");
            System.out.println("Libro encontrado: ");
            Libro myBook = new Libro(bookRecord.get());

            System.out.println(myBook);
            System.out.println(myBook.getAutor()); //******
            System.out.println("¿Desea registrar este libro [s/n]?");
            if(scanner.nextLine().equalsIgnoreCase("s")){
//                myBooks.add(myBook);

                //Finding if author of the finding book exist in DB
                Optional<Autor> myAutor = myAuthors.stream()
                        .filter(autor -> autor.getNombre().toLowerCase().contains(myBook.getAutor().getNombre().toLowerCase()))
                        .findFirst();
                if (myAutor.isPresent()) {
                    System.out.println("Este autor ya existe en la base de datos");
                    myBook.setAutor(myAutor.get()); //Set existent author
                } else {
                    System.out.println("El autor no existe en la base de datos");
                    autorRepository.save(myBook.getAutor()); //Save new author
                }

                bookRepository.save(myBook);
                System.out.println("Libro registrado con éxito");
            }

        } else {
            System.out.println("-------------------------------");
            System.out.println("Libro no encontrado");
        }
    }


    public void printAllRegisteredBooks(){
        myBooks.forEach(System.out::println);
    }

    public void printBooksByLanguage() {
        //DB is needed to make this method
        //Execute with queries
    }

    public void getAllRegisteredAuthors() {
        //Using lists
//        myBooks.forEach(libro -> System.out.println(libro.getAutor() + " - " + libro.getTitulo()));

        //Using DB
        myAuthors.forEach(System.out::println);
    }
}
