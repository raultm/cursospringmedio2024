package es.acaex.cursospringmedio2024.controllers;

            import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AppController {
    
    @Value("${app.env}")
    private String appEnv;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    public Map<String, String> info() {
        return new HashMap<String, String>() {{
            put("Entorno", appEnv);
            put("Version", appVersion);
        }};
    }

    @GetMapping(value = "openapi.yml", produces = "application/yaml;charset=UTF-8")
    public ResponseEntity<String> openapiYaml() {
        return ResponseEntity.ok(getOpenApiYaml());
    }

    private String getOpenApiYaml(){
        String contenido = "no Content";
        ClassPathResource resource = new ClassPathResource("openapi.yml");
        
        try {
            InputStream inputStream = resource.getInputStream();
            contenido = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n")
            );
        } catch (IOException e) {
            contenido = "IOException";
        }
        return contenido;
    }

}