package es.acaex.cursospringmedio2024.services.socios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.SocioSummary;
import es.acaex.cursospringmedio2024.models.Socio;
import es.acaex.cursospringmedio2024.models.mappers.SociosMapper;
import es.acaex.cursospringmedio2024.repositories.SociosRepository;

@Service
public class FindAllSociosService {
    
    @Autowired
    SociosRepository sociosRepository;
    @Autowired
    SociosMapper sociosMapper;

    public List<Socio> execute(){
        return sociosRepository.findAll();
    }

    public ResponseEntity<List<SocioSummary>> response() {
        return ResponseEntity.ok().body(sociosMapper.socioListToSocioSummaryList(execute()));
    }

}
