package es.acaex.cursospringmedio2024.services.libros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.LibroCreate;
import es.acaex.cursospringmedio2024.dto.LibroDetail;
import es.acaex.cursospringmedio2024.models.Libro;
import es.acaex.cursospringmedio2024.models.mappers.LibrosMapper;
import es.acaex.cursospringmedio2024.repositories.LibrosRepository;

@Service
public class SaveLibroService {
    
    @Autowired
    LibrosRepository librosRepository;
    @Autowired
    LibrosMapper librosMapper;

    public Libro execute(LibroCreate libroCreate){
        return librosRepository.save(Libro.builder().nombre(libroCreate.getNombre()).build());
    }

    public ResponseEntity<LibroDetail> response(LibroCreate libroCreate) {
        return ResponseEntity.created(null).body(librosMapper.libroToLibroDetail(execute(libroCreate)));
    }
}
