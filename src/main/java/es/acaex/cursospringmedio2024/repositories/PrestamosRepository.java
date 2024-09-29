package es.acaex.cursospringmedio2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.acaex.cursospringmedio2024.models.Prestamo;

public interface PrestamosRepository extends JpaRepository<Prestamo, Long> {
    
}