package es.acaex.cursospringmedio2024.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import es.acaex.cursospringmedio2024.api.PrestamosApiDelegate;
import es.acaex.cursospringmedio2024.dto.PrestamoCreate;
import es.acaex.cursospringmedio2024.dto.PrestamoDetail;
import es.acaex.cursospringmedio2024.dto.PrestamoSummary;
import es.acaex.cursospringmedio2024.services.prestamos.DevolverPrestamoService;
import es.acaex.cursospringmedio2024.services.prestamos.FindAllPrestamosService;
import es.acaex.cursospringmedio2024.services.prestamos.SavePrestamoService;

@Component
public class PrestamosController implements PrestamosApiDelegate{

    @Autowired
    SavePrestamoService savePrestamoService;
    @Autowired
    FindAllPrestamosService findAllPrestamosService;
    @Autowired
    DevolverPrestamoService devolverPrestamoService;

    @Override
    public ResponseEntity<PrestamoDetail> crearPrestamo(PrestamoCreate prestamoCreate) {
        return savePrestamoService.response(prestamoCreate);
    }

    @Override
    public ResponseEntity<List<PrestamoSummary>> listarPrestamos() {
        return findAllPrestamosService.response();
    }

    @Override
    public ResponseEntity<PrestamoDetail> devolverPrestamo(Long id) {
        return devolverPrestamoService.response(id);
    }
    
}
