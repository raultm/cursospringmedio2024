package es.acaex.cursospringmedio2024.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import es.acaex.cursospringmedio2024.api.LibrosApiDelegate;
import es.acaex.cursospringmedio2024.dto.LibroCreate;
import es.acaex.cursospringmedio2024.dto.LibroDetail;
import es.acaex.cursospringmedio2024.dto.LibroSummary;
import es.acaex.cursospringmedio2024.services.libros.FindAllLibrosService;
import es.acaex.cursospringmedio2024.services.libros.SaveLibroService;

@Component
public class LibrosController implements LibrosApiDelegate{

    @Autowired
    FindAllLibrosService findAllLibrosService;
    @Autowired
    SaveLibroService saveLibroService;

    @Override
    public ResponseEntity<LibroDetail> crearLibro(LibroCreate libroCreate) {
        return saveLibroService.response(libroCreate);
    }

    @Override
    public ResponseEntity<List<LibroSummary>> listarLibros() {
        return findAllLibrosService.response();
    }
    
}
