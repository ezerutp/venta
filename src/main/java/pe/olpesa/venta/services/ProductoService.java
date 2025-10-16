package pe.olpesa.venta.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.repositories.ProductoRepository;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    // Listar todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Buscar producto por SKU
    public Producto obtenerProductoPorSku(String sku) {
        return productoRepository.findBySku(sku);
    }

    // Buscar producto por código de barras
    public Producto obtenerProductoPorCodigoBarras(String codigoBarras) {
        return productoRepository.findByCodigoBarras(codigoBarras);
    }

    // Buscar productos por nombre (conteniendo texto, ignorando mayúsculas)
    public List<Producto> obtenerProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar productos activos
    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByEstado(true);
    }

    // Buscar productos inactivos
    public List<Producto> obtenerProductosInactivos() {
        return productoRepository.findByEstado(false);
    }

    // Buscar productos con stock bajo
    public List<Producto> obtenerProductosConStockBajo(Double stockMinimo) {
        return productoRepository.findByStockActualLessThanEqual(stockMinimo);
    }

    // Buscar productos con stock disponible
    public List<Producto> obtenerProductosConStock() {
        return productoRepository.findByStockActualGreaterThan(0.0);
    }

    // Buscar productos sin stock
    public List<Producto> obtenerProductosSinStock() {
        return productoRepository.findByStockActualLessThanEqual(0.0);
    }

    // Buscar productos afectos a impuesto
    public List<Producto> obtenerProductosAfectosImpuesto() {
        return productoRepository.findByAfectoImpuesto(true);
    }

    // Buscar productos no afectos a impuesto
    public List<Producto> obtenerProductosNoAfectosImpuesto() {
        return productoRepository.findByAfectoImpuesto(false);
    }

    // Buscar productos por rango de precios
    public List<Producto> obtenerProductosPorRangoPrecios(Double precioMinimo, Double precioMaximo) {
        return productoRepository.findByPrecioUnitarioBetween(precioMinimo, precioMaximo);
    }

    // Crear o actualizar producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar producto por ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Activar/Desactivar producto
    public Producto cambiarEstadoProducto(Long id, boolean estado) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setEstado(estado);
            return productoRepository.save(producto);
        }
        return null;
    }

    // Actualizar stock de producto
    public Producto actualizarStock(Long id, Double nuevoStock) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setStockActual(nuevoStock);
            return productoRepository.save(producto);
        }
        return null;
    }

    // Reducir stock de producto (para ventas)
    public Producto reducirStock(Long id, Double cantidad) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null && producto.getStockActual() >= cantidad) {
            producto.setStockActual(producto.getStockActual() - cantidad);
            return productoRepository.save(producto);
        }
        return null;
    }

    // Aumentar stock de producto (para ingresos)
    public Producto aumentarStock(Long id, Double cantidad) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setStockActual(producto.getStockActual() + cantidad);
            return productoRepository.save(producto);
        }
        return null;
    }

    // Contar productos activos
    public int contarProductosActivos() {
        return (int) listarProductos().stream()
                .filter(producto -> producto.isEstado())
                .count();
    }

    // Contar productos inactivos
    public int contarProductosInactivos() {
        return (int) listarProductos().stream()
                .filter(producto -> producto.isEstado() == false)
                .count();
    }
}
