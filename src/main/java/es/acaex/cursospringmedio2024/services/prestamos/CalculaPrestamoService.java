package es.acaex.cursospringmedio2024.services.prestamos;

import java.util.*;
import java.time.*;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.exceptions.PrestamoNoGestionableException;
import es.acaex.cursospringmedio2024.models.Libro;
import es.acaex.cursospringmedio2024.models.Prestamo;
import es.acaex.cursospringmedio2024.models.Socio;

@Service
public class CalculaPrestamoService {
    

    /**
     *  Excepciones que se pueden lanzar
     * 
     *      throw new PrestamoNoGestionableException("El Socio tiene un préstamo retrasado");
     *      throw new PrestamoNoGestionableException("El Libro está prestado");
     *      throw new PrestamoNoGestionableException("El Socio ha superado el límite de libros prestados");
     *  
     */

    public Prestamo execute(Socio socio, Libro libro) {
        return execute(socio, libro, LocalDate.now());
    }

    public Prestamo execute(Socio socio, Libro libro, LocalDate localDate){
        int prestamoDias = 7;
        
        return Prestamo.builder()
                .libro(libro)
                .socio(socio)
                .expiraEn(localDate.plusDays(prestamoDias))
                .build();
    }
}
