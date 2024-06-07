package com.kcastillo.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class DatosLibro {

    private String titulo;
    private List<DatosAutor> autor;
    private String idiomas;
    private Double numeroDescargas;

    public DatosLibro() {
    }

    public DatosLibro (DatosLibroRecord datosLibroRecord) {
        this.titulo = datosLibroRecord.titulo();
        this.autor = datosLibroRecord.autor();
        this.idiomas = datosLibroRecord.idiomas().get(0); //Get first language
        this.numeroDescargas = datosLibroRecord.numeroDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "DatosLibro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas='" + idiomas + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}
