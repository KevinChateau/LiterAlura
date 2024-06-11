package com.kcastillo.LiterAlura.repository;

import com.kcastillo.LiterAlura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    //derived query
    List<Libro> findByIdioma(String idioma);
}
