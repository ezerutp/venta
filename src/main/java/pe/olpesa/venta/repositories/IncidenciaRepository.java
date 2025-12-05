package pe.olpesa.venta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Incidencia;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.utils.EnumEstadoIncidencia;
import pe.olpesa.venta.utils.EnumPrioridad;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    
    List<Incidencia> findByEstado(EnumEstadoIncidencia estado);
    
    List<Incidencia> findByCliente(Cliente cliente);
    
    List<Incidencia> findByAsignadoA(Usuario usuario);
    
    List<Incidencia> findByPrioridad(EnumPrioridad prioridad);
    
    List<Incidencia> findByEstadoNot(EnumEstadoIncidencia estado);
    
    List<Incidencia> findByEstadoIn(List<EnumEstadoIncidencia> estados);
    
    @Query("SELECT i FROM Incidencia i LEFT JOIN FETCH i.cliente LEFT JOIN FETCH i.producto LEFT JOIN FETCH i.asignadoA LEFT JOIN FETCH i.creadoPor WHERE i.asignadoA = :usuario AND i.estado IN :estados")
    List<Incidencia> findByAsignadoAAndEstadoInWithRelations(@Param("usuario") Usuario usuario, @Param("estados") List<EnumEstadoIncidencia> estados);
    
    @Query("SELECT i FROM Incidencia i LEFT JOIN FETCH i.cliente LEFT JOIN FETCH i.producto LEFT JOIN FETCH i.asignadoA LEFT JOIN FETCH i.creadoPor WHERE i.creadoPor = :usuario AND i.estado IN :estados")
    List<Incidencia> findByCreadoPorAndEstadoInWithRelations(@Param("usuario") Usuario usuario, @Param("estados") List<EnumEstadoIncidencia> estados);
    
    List<Incidencia> findByAsignadoAAndEstadoIn(Usuario usuario, List<EnumEstadoIncidencia> estados);
    
    List<Incidencia> findByCreadoPorAndEstadoIn(Usuario usuario, List<EnumEstadoIncidencia> estados);
    
    long countByEstado(EnumEstadoIncidencia estado);
    
    long countByAsignadoAAndEstadoIn(Usuario usuario, List<EnumEstadoIncidencia> estados);
}
