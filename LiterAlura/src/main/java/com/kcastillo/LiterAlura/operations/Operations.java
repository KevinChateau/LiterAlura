package com.kcastillo.LiterAlura.operations;

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
    private List<Libro> myBooks;

    public Operations(LibroRepository bookRepository, AutorRepository autorRepository) {
        consumoAPI = new ConsumoAPI();
        conversorJsonClass = new ConvierteDatos();
        scanner = new Scanner(System.in);
        myBooks = new ArrayList<>();
        this.bookRepository = bookRepository;
        this.autorRepository = autorRepository;
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

            /*if(myBook.getAutor() != null){
                Autor autorGuardado = autorRepository.save(myBook.getAutor());
                myBook.setAutor(autorGuardado);
//                myBook.getAutor().setLibro(myBook,false); //Asegura la relación bidireccional sin recursión
            }*/

            System.out.println(myBook);
//            myBook.getAutor().setLibro(myBook); // ******
            System.out.println(myBook.getAutor()); //******
            System.out.println("¿Desea registrar este libro [s/n]?");
            if(scanner.nextLine().equalsIgnoreCase("s")){
//                Autor myAuthor = myBook.getAutor();
                myBooks.add(myBook);
//                saveLibroWithAutor(myBook);
                autorRepository.save(myBook.getAutor());
                bookRepository.save(myBook);
                System.out.println("Libro registrado con éxito");
            }

        } else {
            System.out.println("-------------------------------");
            System.out.println("Libro no encontrado");
        }
    }

    /*@Transactional
    public void saveLibroWithAutor(Libro libro) {
        if (libro.getAutor() != null) {
            // Guardar y obtener una referencia gestionada del autor
            Autor savedAutor = autorRepository.save(libro.getAutor());
            libro.setAutor(savedAutor);
        }
        bookRepository.save(libro);
    }*/


    public void printAllRegisteredBooks(){
        myBooks.forEach(System.out::println);
    }

    public void printBooksByLanguage() {
        //DB is needed to make this method
    }

    public void getAllRegisteredAuthors() {
        //Using lists
        myBooks.forEach(libro -> System.out.println(libro.getAutor() + " - " + libro.getTitulo()));
    }
}
