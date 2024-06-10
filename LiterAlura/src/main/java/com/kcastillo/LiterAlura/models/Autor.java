package com.kcastillo.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimient;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "libros_id", referencedColumnName = "id")
//    @JoinColumn(name = "libros_id")
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(DatosAutorRecord datosAutorRecord) {
        this.nombre = datosAutorRecord.nombre();
        this.fechaDeNacimiento = datosAutorRecord.fechaDeNacimiento();
        this.fechaDeFallecimient = datosAutorRecord.fechaDeFallecimiento();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimient() {
        return fechaDeFallecimient;
    }

    public void setFechaDeFallecimient(String fechaDeFallecimient) {
        this.fechaDeFallecimient = fechaDeFallecimient;
    }

    public List<Libro> getLibros() {
        return libros;
    }

   /* public void setLibro(Libro libro, boolean actualizarRelacion) {
//        libro.forEach(l-> l.setAutor(this));
        this.libros = libro;
        //Para el caso de un solo libro
        if (actualizarRelacion && libro != null && libro.getAutor() != this) {
            libro.setAutor(this);
        }

    }*/

    public void setLibro(List<Libro> libros) {
//        libro.forEach(l-> l.setAutor(this));
        this.libros = libros;
        //Para el caso de un solo libro
//        if (libro != null && libro.getAutor() != this) {
//            libro.setAutor(this);
//        }

    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeFallecimient='" + fechaDeFallecimient + '\'' +
                ", libro=" + libros +
                '}';
    }
}
