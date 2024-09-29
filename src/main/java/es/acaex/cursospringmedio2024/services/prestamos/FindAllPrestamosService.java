package es.acaex.cursospringmedio2024.services.prestamos;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.PrestamoSummary;
import es.acaex.cursospringmedio2024.models.Prestamo;
import es.acaex.cursospringmedio2024.models.mappers.PrestamosMapper;
import es.acaex.cursospringmedio2024.repositories.PrestamosRepository;

@Service
public class FindAllPrestamosService {
    
    @Autowired
    PrestamosRepository prestamosRepository;
    @Autowired
    PrestamosMapper prestamosMapper;

    public List<Prestamo> execute(){
        return prestamosRepository.findAll();
    }

    public ResponseEntity<List<PrestamoSummary>> response() {
        return ResponseEntity.ok().body(prestamosMapper.prestamoListToPrestamoSummaryList(execute()));
    }
}
