package com.kcastillo.LiterAlura.repository;

import com.kcastillo.LiterAlura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {

}
