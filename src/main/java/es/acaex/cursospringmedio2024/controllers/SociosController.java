package es.acaex.cursospringmedio2024.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import es.acaex.cursospringmedio2024.api.SociosApiDelegate;
import es.acaex.cursospringmedio2024.dto.SocioCreate;
import es.acaex.cursospringmedio2024.dto.SocioDetail;
import es.acaex.cursospringmedio2024.dto.SocioSummary;
import es.acaex.cursospringmedio2024.services.socios.FindAllSociosService;
import es.acaex.cursospringmedio2024.services.socios.SaveSocioService;

@Controller
public class SociosController implements SociosApiDelegate{
    
    @Autowired
    FindAllSociosService findAllSociosService;
    @Autowired
    SaveSocioService saveSocioService;

    @Override
    public ResponseEntity<List<SocioSummary>> listarSocios() {
        return findAllSociosService.response();
    }

    @Override
    public ResponseEntity<SocioDetail> crear(SocioCreate socioCreate) {
        return saveSocioService.response(socioCreate);
    }
    
}
