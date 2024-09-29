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
        // if(socio.tienePrestamoVencido()){
        //     throw new PrestamoNoGestionableException("El Socio tiene un préstamo retrasado");
        // }

        // if(libro.estaEnPrestamo()){
        //     throw new PrestamoNoGestionableException("El Libro está prestado");
        // }

        // if(!socio.haSuperadoElLimiteDePrestamo()){
        //     prestamoDias = 7;
        //     if(socio.getPerfil().toLowerCase() == "estudiante"){
        //         prestamoDias = 15;
        //     }else if (socio.getPerfil().toLowerCase() == "profesor"){
        //         prestamoDias = 30;
        //     }
        // }else{
        //     throw new PrestamoNoGestionableException("El Socio ha superado el límite de libros prestados");
        // }
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
    void unVisitanteTiene7DiasDePrestamo(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(socio.getPerfil()).thenReturn("visitante");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-09-08"));
    }

    @Test
    void unEstudianteTiene15DiasDePrestamo(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(socio.getPerfil()).thenReturn("estudiante");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-09-16"));
    }

    @Test
    void unProfesorTiene30DiasDePrestamo(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(socio.getPerfil()).thenReturn("profesor");

        Prestamo prestamo = calculaPrestamoService.execute(socio, libro, fechaDePrestamo);

        assertThat(prestamo.getExpiraEn().toString(), is("2024-10-01"));
    }

    @Test
    void noSePuedePrestarSiElSocioHaSuperadoElLimiteDePrestamos(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(socio.haSuperadoElLimiteDePrestamo()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        }); 

        assertThat(ex.getMessage(), containsString("prestados"));
    }

    @Test
    void noSePuedePrestarSiElSocioTieneUnPrestamoRetrasado(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(socio.tienePrestamoVencido()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        }); 

        assertThat(ex.getMessage(), containsString("retrasado"));
    }

    @Test
    void noSePuedePrestarSiElLibroYaEstaEnUnPrestamoActivo(){
        fechaDePrestamo = LocalDate.parse("2024-09-01");
        when(libro.estaEnPrestamo()).thenReturn(true);

        Exception ex = assertThrows(PrestamoNoGestionableException.class, () -> {
            calculaPrestamoService.execute(socio, libro, fechaDePrestamo);
        }); 

        assertThat(ex.getMessage(), containsString("prestado"));
    }
}
