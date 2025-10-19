package pe.olpesa.venta.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.cliente LEFT JOIN FETCH v.producto LEFT JOIN FETCH v.usuario ORDER BY v.fechaEmitido DESC")
    List<Venta> findAllWithDetails();
    
    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.cliente LEFT JOIN FETCH v.producto LEFT JOIN FETCH v.usuario ORDER BY v.fechaEmitido DESC")
    List<Venta> findTopVentasWithDetails(Pageable pageable);
}
