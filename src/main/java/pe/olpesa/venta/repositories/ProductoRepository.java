package pe.olpesa.venta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar producto por SKU
    Producto findBySku(String sku);
    
    // Buscar producto por código de barras
    Producto findByCodigoBarras(String codigoBarras);
    
    // Buscar productos por nombre (conteniendo texto, ignorando mayúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar productos por estado
    List<Producto> findByEstado(boolean estado);
    
    // Buscar productos con stock menor o igual al especificado
    List<Producto> findByStockActualLessThanEqual(Double stock);
    
    // Buscar productos con stock mayor al especificado
    List<Producto> findByStockActualGreaterThan(Double stock);
    
    // Buscar productos afectos o no afectos a impuesto
    List<Producto> findByAfectoImpuesto(boolean afectoImpuesto);
    
    // Buscar productos por rango de precios
    List<Producto> findByPrecioUnitarioBetween(Double precioMinimo, Double precioMaximo);
    
}
