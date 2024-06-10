package com.kcastillo.LiterAlura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
//    @OneToOne(mappedBy="libros",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private Double numeroDescargas;

    public Libro() {
    }

    public Libro(DatosLibroRecord datosLibroRecord) {
        this.titulo = datosLibroRecord.titulo();
        //Falta verificar que el autor no sea null
        this.autor = new Autor(datosLibroRecord.autor().get(0)); //Get only one author
        this.idioma = datosLibroRecord.idiomas().get(0); //Get first language
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
        return idioma;
    }

    public void setIdiomas(String idiomas) {
        this.idioma = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor.getNombre() +
                ", idiomas='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}
