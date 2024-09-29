package es.acaex.cursospringmedio2024.models.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import es.acaex.cursospringmedio2024.dto.PrestamoDetail;
import es.acaex.cursospringmedio2024.dto.PrestamoSummary;
import es.acaex.cursospringmedio2024.models.Prestamo;

@Mapper(componentModel = "spring")
public interface PrestamosMapper {
    

    PrestamoSummary prestamoToPrestamoSummary(Prestamo prestamo);
    List<PrestamoSummary> prestamoListToPrestamoSummaryList(List<Prestamo> prestamos);

    PrestamoDetail prestamoToPrestamoDetail(Prestamo prestamo);
}
