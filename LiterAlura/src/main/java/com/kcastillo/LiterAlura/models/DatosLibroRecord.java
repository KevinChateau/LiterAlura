package com.kcastillo.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //To ignore properties not mapping
public record DatosLibroRecord(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutorRecord> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDescargas
        ) {
}
