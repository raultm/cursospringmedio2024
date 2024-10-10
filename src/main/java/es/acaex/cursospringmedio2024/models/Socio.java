package es.acaex.cursospringmedio2024.models;


import java.util.*;
import java.time.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "socios")	
@Data  
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String perfil;

    @OneToMany(mappedBy = "socio")
    private List<Prestamo> prestamos = new ArrayList<>();

    public boolean haSuperadoElLimiteDePrestamo(){
        return getPrestamos().stream()
                .filter(prestamo -> prestamo.getDevueltoEn() == null)
                .count() >= 3;
    }

    public boolean tienePrestamoVencido(){
        return getPrestamos().stream()
                .anyMatch(prestamo ->   prestamo.getDevueltoEn() == null && 
                                        prestamo.getExpiraEn().isBefore(LocalDate.now())
                );                   
    }   
}
