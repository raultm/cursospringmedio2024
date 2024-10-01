package es.acaex.cursospringmedio2024.services.prestamos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.PrestamoDetail;
import es.acaex.cursospringmedio2024.models.Prestamo;
import es.acaex.cursospringmedio2024.models.mappers.PrestamosMapper;
import es.acaex.cursospringmedio2024.repositories.PrestamosRepository;
import java.time.*;

@Service
public class DevolverPrestamoService {

    @Autowired
    PrestamosMapper prestamosMapper;
    @Autowired
    PrestamosRepository prestamosRepository;

    public ResponseEntity<PrestamoDetail> response(Long id) {
        return ResponseEntity.ok().body(prestamosMapper.prestamoToPrestamoDetail(execute(id)));
    }

    private Prestamo execute(Long id) {
        Prestamo prestamo = prestamosRepository.findById(id).orElseThrow();

        prestamo.setDevueltoEn(LocalDate.now());

        return prestamosRepository.save(prestamo);
    }
    
}
