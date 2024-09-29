package es.acaex.cursospringmedio2024.models.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import es.acaex.cursospringmedio2024.dto.LibroDetail;
import es.acaex.cursospringmedio2024.dto.LibroSummary;
import es.acaex.cursospringmedio2024.models.Libro;

@Mapper(componentModel = "spring")
public interface LibrosMapper {
    
    LibroSummary libroToLibroSummary(Libro libro);
    List<LibroSummary> libroListToLibroSummaryList(List<Libro> libros);

    LibroDetail libroToLibroDetail(Libro libro);
}
