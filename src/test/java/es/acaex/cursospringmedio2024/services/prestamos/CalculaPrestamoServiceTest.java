package es.acaex.cursospringmedio2024.services.prestamos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import es.acaex.cursospringmedio2024.exceptions.PrestamoNoGestionableException;
import es.acaex.cursospringmedio2024.models.Libro;
import es.acaex.cursospringmedio2024.models.Prestamo;
import es.acaex.cursospringmedio2024.models.Socio;

import static org.mockito.Mockito.when;

import java.time.*;

public class CalculaPrestamoServiceTest {

    CalculaPrestamoService calculaPrestamoService;

    @Mock
    Socio socio;
    @Mock
    Libro libro;

    LocalDate fechaDePrestamo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        calculaPrestamoService = new CalculaPrestamoService();
    }

    @Test
    void unVisitanteTiene7DiasDePrestamo() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(socio.getPerfil()).thenReturn("visitante");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-09-09"));
    }

    @Test
    void unEstudianteTiene15DiasDePrestamo() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(socio.getPerfil()).thenReturn("alumno");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-09-17"));
    }

    @Test
    void unProfesorTiene30DiasDePrestamo() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(socio.getPerfil()).thenReturn("profesor");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-10-02"));
    }

    @Test
    void noSePuedePrestarSiElSocioHaSuperadoElLimiteDePrestamos() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(socio.haSuperadoElLimiteDePrestamo()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        });

        assertThat(ex.getMessage(), containsString("prestados"));
    }

    @Test
    void noSePuedePrestarSiElSocioTieneUnPrestamoRetrasado() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(socio.tienePrestamoVencido()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        });

        assertThat(ex.getMessage(), containsString("retrasado"));
    }

    @Test
    void noSePuedePrestarSiElLibroYaEstaEnUnPrestamoActivo() {
        fechaDePrestamo = LocalDate.parse("2024-09-02");
        when(libro.estaEnPrestamo()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        });

        assertThat(ex.getMessage(), containsString("prestado"));
    }

    @Test
    void noSePuedePrestarAAlumnoSiEsFinDeSemana() {
        fechaDePrestamo = LocalDate.parse("2024-10-12");

        when(socio.getPerfil()).thenReturn("alumno");

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        });

        assertThat(ex.getMessage(), containsString("Los alumnos no pueden alquilar en fin de semana"));
    }

    @Test
    void noSePuedePrestarAVisitanteSiEsFinDeSemana() {
        fechaDePrestamo = LocalDate.parse("2024-10-12");

        when(socio.getPerfil()).thenReturn("visitante");

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        });

        assertThat(ex.getMessage(), containsString("Los visitantes no pueden alquilar en fin de semana"));
    }

    @Test
    void siSePuedePrestarAProfesorSiEsFinDeSemana() {
        fechaDePrestamo = LocalDate.parse("2024-10-12");

        when(socio.getPerfil()).thenReturn("profesor");
        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        assertThat(prestamo.getExpiraEn().toString(), is("2024-11-11"));
    }

}
