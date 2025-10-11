package pe.olpesa.venta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
}
