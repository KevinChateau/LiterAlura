package com.kcastillo.LiterAlura.repository;

import com.kcastillo.LiterAlura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
