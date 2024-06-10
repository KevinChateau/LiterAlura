package com.kcastillo.LiterAlura;

import com.kcastillo.LiterAlura.principal.Principal;
import com.kcastillo.LiterAlura.repository.AutorRepository;
import com.kcastillo.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired //Para que haga la inyección de una dependencia
	private LibroRepository bookRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(bookRepository,autorRepository);
		principal.showMenu();
	}
}
