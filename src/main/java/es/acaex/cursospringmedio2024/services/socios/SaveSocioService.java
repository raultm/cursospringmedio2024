package es.acaex.cursospringmedio2024.services.socios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.acaex.cursospringmedio2024.dto.SocioCreate;
import es.acaex.cursospringmedio2024.dto.SocioDetail;
import es.acaex.cursospringmedio2024.models.Socio;
import es.acaex.cursospringmedio2024.models.mappers.SociosMapper;
import es.acaex.cursospringmedio2024.repositories.SociosRepository;

@Service
public class SaveSocioService {

    @Autowired
    SociosRepository sociosRepository;
    @Autowired
    SociosMapper sociosMapper;

    public Socio execute(SocioCreate socioCreate){
        return sociosRepository.save(Socio.builder()
            .nombre(socioCreate.getNombre())
            .perfil(socioCreate.getPerfil())
            .build());
    }

    public ResponseEntity<SocioDetail> response(SocioCreate socioCreate) {
        return ResponseEntity.created(null).body(sociosMapper.socioToSocioDetail(execute(socioCreate)));
    }
}
