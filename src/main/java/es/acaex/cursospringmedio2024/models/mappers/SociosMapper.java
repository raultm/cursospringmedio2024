package es.acaex.cursospringmedio2024.models.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import es.acaex.cursospringmedio2024.dto.SocioDetail;
import es.acaex.cursospringmedio2024.dto.SocioSummary;
import es.acaex.cursospringmedio2024.models.Socio;

@Mapper(componentModel = "spring")
public interface SociosMapper {
    
    SocioSummary socioToSocioSummary(Socio socio);
    List<SocioSummary> socioListToSocioSummaryList(List<Socio> socios);

    SocioDetail socioToSocioDetail(Socio socio);

}
