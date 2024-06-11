package com.kcastillo.LiterAlura.operations;

import com.kcastillo.LiterAlura.models.Autor;
import com.kcastillo.LiterAlura.models.Libro;
import com.kcastillo.LiterAlura.models.DatosLibroRecord;
import com.kcastillo.LiterAlura.models.DatosLibros;
import com.kcastillo.LiterAlura.repository.AutorRepository;
import com.kcastillo.LiterAlura.repository.LibroRepository;
import com.kcastillo.LiterAlura.service.ConsumoAPI;
import com.kcastillo.LiterAlura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void top10MoreDownloadedBooks() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversorJsonClass.obtenerDatos(json, DatosLibros.class);

        List<Libro> top10 = datos.resultados().stream()
                .map(dlr -> new Libro(dlr))
                .limit(10)
                .toList();

        System.out.println("-------------------------------");
        System.out.println("Libros más descargados: ");

        top10.forEach(libro -> System.out.println("No. descargas: " + libro.getNumeroDescargas() +
                ",Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor().getNombre() ));
    }

    public void getBookByTitle() {

        System.out.println("Ingrese el nombre del libro: ");
        var title = scanner.nextLine();

        //Finding if the book exists in DB
        Optional<Libro> findingBook = myBooks.stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
        if (findingBook.isPresent()) {
            System.out.println("-------------------------------");
            System.out.println("El libro ya se encuentra en la base de datos: ");
            System.out.println("Libro en DB: " + findingBook.get());
        } else {

            //Replace spaces in "+"
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + title.replace(" ", "+"));
            var datos = conversorJsonClass.obtenerDatos(json, DatosLibros.class);
//        System.out.println(datos);

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
                if (scanner.nextLine().equalsIgnoreCase("s")) {
//                myBooks.add(myBook);

                    //Finding if author of the finding book exists in DB
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
                    System.out.println("-------------------------------");
                    System.out.println("Libro registrado con éxito");
                    //Update lists
                    myBooks = bookRepository.findAll();
                    myAuthors = autorRepository.findAll();
                }

            } else {
                System.out.println("-------------------------------");
                System.out.println("Libro no encontrado");
            }
        }
    }


    public void printAllRegisteredBooks(){
        myBooks.forEach(System.out::println);
    }

    public void printBooksByLanguage() {
        //DB is needed to make this method
        System.out.println("-------------------------------");
        System.out.println("Idiomas de libros registrados: ");
        List<String> languages = myBooks.stream().map(Libro::getIdiomas).distinct().toList();
        languages.forEach(System.out::println);
        System.out.println("Selecciona un idioma: ");
        var language = scanner.nextLine();

        List<Libro> bookList = bookRepository.findByIdioma(language);
        if (bookList.isEmpty()) {
            System.out.println("-------------------------------");
            System.out.println("Ningún libro encontrado con el lenguaje -" + language + "-");
        }else {
            System.out.println("-------------------------------");
            System.out.println("Total de libros encontrados: " + bookList.size());
            System.out.println("Lista de libros con el idioma -" + language + "-:");
            bookList.forEach(System.out::println);
        }

    }

    public void getAllRegisteredAuthors() {
        //Using lists
//        myBooks.forEach(libro -> System.out.println(libro.getAutor() + " - " + libro.getTitulo()));

        //Using DB
        myAuthors.forEach(System.out::println);
    }

    public void findAuthorsAliveByYear() {
        System.out.println("Ingrese el año: [yyyy]");
        String year = scanner.nextLine();
        List<Autor> myAuthors = autorRepository.FindAuthorsAliveInAYear(year);
        if (myAuthors.isEmpty()) {
            System.out.println("-------------------------------");
            System.out.println("Ningún autor de los libros registrados estuvo vivo durante el año " + year);
        } else {
            System.out.println("-------------------------------");
            System.out.println("Lista de autores vivos registrados en el año " + year + ", total = ["+myAuthors.size()+"]");
            myAuthors.forEach(System.out::println);
        }
    }

    public void statisticsAboutAuthors() {
        IntStream birthyearAuthor = myAuthors.stream().mapToInt(autor -> Integer.valueOf(autor.getFechaDeNacimiento()));
        IntSummaryStatistics stats = birthyearAuthor.summaryStatistics();
        System.out.println("-------------------------------");

        System.out.println("Estadísticas sobre el año de nacimiento de los autores registrados");
        System.out.println("total: " + stats.getCount());
        System.out.println("max: " + stats.getMax());
        System.out.println("min: " + stats.getMin());
        System.out.println("promedio: " + stats.getAverage());

    }

    public void getAuthorByName() {
        System.out.println("Ingrese el nombre del autor a buscar en la base de datos: ");
        String myAuthor = scanner.nextLine();

        Optional<Autor> findingAuthor = autorRepository.findFirstByNombreContainingIgnoreCase(myAuthor);
        if (findingAuthor.isPresent()) {
            System.out.println("-------------------------------");
            System.out.println("Autor " + myAuthor + " encontrado: ");
            System.out.println(findingAuthor.get());

        } else {
            System.out.println("-------------------------------");
            System.out.println("Author " + myAuthor + " no encontrado");
        }
    }


}
