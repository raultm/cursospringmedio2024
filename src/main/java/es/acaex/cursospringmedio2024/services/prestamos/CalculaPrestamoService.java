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

    

    private final static Map<String, Long> diasPorPerfilHorarioHabitual = Map.of(
        "profesor", 30L,
        "estudiante", 15L,
        "visitante", 7L
    );

    private final static Map<String, Long> diasPorPerfilHorarioNoHabitual = Map.of(
        "profesor", 15L,
        "estudiante", 7L,
        "visitante", 3L
    );

    private final static Map<String, Long> diasPorPerfilVacaciones = Map.of(
        "profesor", 60L
    );

    private final static Map<String, Map<String, Long>> diasPorSituacion = Map.of(
        "horarionohabitual", diasPorPerfilHorarioNoHabitual,
        "vacaciones", diasPorPerfilVacaciones,
        "horariohabitual", diasPorPerfilHorarioHabitual
    );

    public Prestamo execute(Socio socio, Libro libro) {
        return execute(socio, libro, LocalDate.now());
    }

    public Prestamo execute(Socio socio, Libro libro, LocalDate localDate) {
        return execute(socio, libro, localDate, LocalTime.now());
    }

    public Prestamo execute(Socio socio, Libro libro, LocalDate localDate, LocalTime localTime) {
        validaQuePrestamoEsGestionable(socio, libro, localDate, localTime);

        return Prestamo.builder()
                .libro(libro)
                .socio(socio)
                .expiraEn(localDate.plusDays(diasPorSituacion.get(getNombreSituacion(localDate, localTime)).get(socio.getPerfil())))
                .build();
    }

    private String getNombreSituacion(LocalDate localDate, LocalTime localTime) {
        if(    localDate.getMonth().equals(Month.JULY)
            || localDate.getMonth().equals(Month.AUGUST)){
                return "vacaciones";
            }
        
        if(    localTime.isBefore(LocalTime.of(9, 0))
            || localTime.isAfter(LocalTime.of(20,0))){
            return "horarionohabitual";
        }
        return "horariohabitual";
    }

    private void validaQuePrestamoEsGestionable(Socio socio, Libro libro, LocalDate localDate, LocalTime localTime) {
        if(libro.estaEnPrestamo()){
            throw new PrestamoNoGestionableException("El Libro está prestado");
        }

        if(socio.haSuperadoElLimiteDePrestamo()){
            throw new PrestamoNoGestionableException("El Socio ha superado el límite de libros prestados");
        }

        if(socio.tienePrestamoVencido()){
            throw new PrestamoNoGestionableException("El Socio tiene un préstamo retrasado");
        }

        if(noEsPerfilProfesor(socio) && esFinDeSemana(localDate)){
            throw new PrestamoNoGestionableException("El pefil del Socio no puede sacar préstamos en fin de semana");
        }

        if(getNombreSituacion(localDate, localTime).equals("vacaciones") && !socio.getPerfil().equals("profesor")){
            throw new PrestamoNoGestionableException("El pefil del Socio no puede sacar préstamos en vacaciones");
        }
    }

    private boolean noEsPerfilProfesor(Socio socio) {
        return !socio.getPerfil().equals("profesor");
    }

    private boolean esFinDeSemana(LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }
}
