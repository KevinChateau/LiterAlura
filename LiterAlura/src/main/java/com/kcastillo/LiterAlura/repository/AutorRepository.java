package com.kcastillo.LiterAlura.repository;

import com.kcastillo.LiterAlura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query(value = "SELECT * FROM autores a WHERE a.fecha_de_nacimiento<:year AND a.fecha_de_fallecimient > :year", nativeQuery = true)
    List<Autor> FindAuthorsAliveInAYear(String year);

    Optional<Autor> findFirstByNombreContainingIgnoreCase(String nombre);

}
