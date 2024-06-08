package com.kcastillo.LiterAlura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Double numeroDescargas;

    public Libro() {
    }

    public Libro(DatosLibroRecord datosLibroRecord) {
        this.titulo = datosLibroRecord.titulo();
        this.autor = new Autor(datosLibroRecord.autor().get(0)); //Get only one author
        this.idiomas = datosLibroRecord.idiomas().get(0); //Get first language
        this.numeroDescargas = datosLibroRecord.numeroDescargas();
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
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
