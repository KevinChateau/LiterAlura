package com.kcastillo.LiterAlura.operations;

import com.kcastillo.LiterAlura.models.DatosLibro;
import com.kcastillo.LiterAlura.models.DatosLibros;
import com.kcastillo.LiterAlura.service.ConsumoAPI;
import com.kcastillo.LiterAlura.service.ConvierteDatos;

import java.util.List;

public class Operations {

    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI;
    private ConvierteDatos conversorJsonClass;

    public Operations() {
        consumoAPI = new ConsumoAPI();
        conversorJsonClass = new ConvierteDatos();
    }

    public List<DatosLibro> getAllBooksAPI() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversorJsonClass.obtenerDatos(json, DatosLibros.class);

        return datos.resultados();
    }

}
