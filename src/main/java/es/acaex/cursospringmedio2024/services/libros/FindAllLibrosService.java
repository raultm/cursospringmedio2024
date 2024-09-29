package es.acaex.cursospringmedio2024.services.libros;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.LibroSummary;
import es.acaex.cursospringmedio2024.models.Libro;
import es.acaex.cursospringmedio2024.models.mappers.LibrosMapper;
import es.acaex.cursospringmedio2024.repositories.LibrosRepository;

@Service
public class FindAllLibrosService {
    
    @Autowired
    LibrosRepository librosRepository;
    @Autowired
    LibrosMapper librosMapper;

    public List<Libro> execute(){
        return librosRepository.findAll();
    }

    public ResponseEntity<List<LibroSummary>> response() {
        return ResponseEntity.ok().body(librosMapper.libroListToLibroSummaryList(execute()));
    }
}
