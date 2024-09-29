package es.acaex.cursospringmedio2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.acaex.cursospringmedio2024.models.Libro;

public interface LibrosRepository extends JpaRepository<Libro, Long> {
    
}
