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
     * Excepciones que se pueden lanzar
     * 
     * throw new PrestamoNoGestionableException("El Socio tiene un préstamo
     * retrasado");
     * throw new PrestamoNoGestionableException("El Libro está prestado");
     * throw new PrestamoNoGestionableException("El Socio ha superado el límite de
     * libros prestados");
     * 
     */

    public Prestamo execute(Socio socio, Libro libro) {
        return execute(socio, libro, LocalDate.now());
    }

    public Prestamo execute(Socio socio, Libro libro, LocalDate localDate) {
        if (socio.haSuperadoElLimiteDePrestamo()) {
            throw new PrestamoNoGestionableException("El Socio ha superado el límite de libros prestados");
        } else if (socio.tienePrestamoVencido()) {
            throw new PrestamoNoGestionableException("El Socio tiene un préstamo retrasado");
        } else if (libro.estaEnPrestamo()) {
            throw new PrestamoNoGestionableException("El Libro está prestado");
        } else {
            int prestamoDias = 0;
            DayOfWeek day = localDate.getDayOfWeek();
            String perfil = socio.getPerfil();
            boolean diaCorrecto = true;
            String mensaje = "";
            switch (perfil) {
                case "visitante":
                    prestamoDias = 7;
                    if (day.name() == "SUNDAY" || day.name() == "SUNDAY"){
                        diaCorrecto = false;
                        mensaje = "Los visitantes no pueden alquilar en fin de semana";
                    }
                    break;
                case "alumno":
                    prestamoDias = 15;
                    if (day.name() == "SUNDAY" || day.name() == "SUNDAY") {
                        diaCorrecto = false;
                        mensaje = "Los estudiantes no pueden alquilar en fin de semana";
                    }
                    break;
                case "profesor":
                    prestamoDias = 30;
                    break;
                default:
                    prestamoDias = 0;
                    break;
            }
            if (diaCorrecto) {
                return Prestamo.builder()
                        .libro(libro)
                        .socio(socio)
                        .expiraEn(localDate.plusDays(prestamoDias))
                        .build();
            } else {
                throw new PrestamoNoGestionableException(mensaje);
            }
        }
    }
}
