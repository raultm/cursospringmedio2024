package es.acaex.cursospringmedio2024.services.prestamos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import es.acaex.cursospringmedio2024.dto.PrestamoCreate;
import es.acaex.cursospringmedio2024.dto.PrestamoDetail;
import es.acaex.cursospringmedio2024.models.Prestamo;
import es.acaex.cursospringmedio2024.models.mappers.PrestamosMapper;
import es.acaex.cursospringmedio2024.repositories.LibrosRepository;
import es.acaex.cursospringmedio2024.repositories.PrestamosRepository;
import es.acaex.cursospringmedio2024.repositories.SociosRepository;

@Service
public class SavePrestamoService {
    
    @Autowired
    PrestamosRepository prestamosRepository;
    @Autowired
    SociosRepository sociosRepository;
    @Autowired
    LibrosRepository librosRepository;
    @Autowired
    CalculaPrestamoService calculaPrestamoService;
    @Autowired
    PrestamosMapper prestamosMapper;

    public Prestamo execute(PrestamoCreate prestamoCreate){
        Prestamo prestamoCalculado = calculaPrestamoService.execute(
            sociosRepository.findById(prestamoCreate.getSocioId()).orElseThrow(),
            librosRepository.findById(prestamoCreate.getLibroId()).orElseThrow()
        );
        return prestamosRepository.save(prestamoCalculado);
    }

    public ResponseEntity<PrestamoDetail> response(PrestamoCreate prestamoCreate) {
        return ResponseEntity.created(null).body(prestamosMapper.prestamoToPrestamoDetail(execute(prestamoCreate)));
    }
}
