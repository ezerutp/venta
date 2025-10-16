package pe.olpesa.venta.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoRestController {
    private final ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        // Verificar que el SKU no esté en uso
        Producto productoExistentePorSku = productoService.obtenerProductoPorSku(producto.getSku());
        if (productoExistentePorSku != null) {
            return ResponseEntity.badRequest().body("Ya existe un producto con ese SKU");
        }

        // Verificar que el código de barras no esté en uso
        Producto productoExistentePorCodigoBarras = productoService.obtenerProductoPorCodigoBarras(producto.getCodigoBarras());
        if (productoExistentePorCodigoBarras != null) {
            return ResponseEntity.badRequest().body("Ya existe un producto con ese código de barras");
        }
        
        // Establecer estado activo por defecto
        producto.setEstado(true);
        
        Producto productoGuardado = productoService.guardarProducto(producto);
        return ResponseEntity.ok(productoGuardado);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    // Activar un producto
    @PutMapping("/activar/{id}")
    public ResponseEntity<String> activarProducto(@PathVariable Long id) {
        Producto producto = productoService.cambiarEstadoProducto(id, true);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Producto activado correctamente");
    }

    // Desactivar un producto
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarProducto(@PathVariable Long id) {
        Producto producto = productoService.cambiarEstadoProducto(id, false);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Producto desactivado correctamente");
    }
    
    // Obtener datos de un producto para edición
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoParaEdicion(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto productoDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        Producto productoExistente = productoService.obtenerProductoPorId(id);
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar que el SKU no esté en uso por otro producto
        Producto productoConMismoSku = productoService.obtenerProductoPorSku(productoDto.getSku());
        if (productoConMismoSku != null && !productoConMismoSku.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un producto con ese SKU");
        }

        // Verificar que el código de barras no esté en uso por otro producto
        Producto productoConMismoCodigoBarras = productoService.obtenerProductoPorCodigoBarras(productoDto.getCodigoBarras());
        if (productoConMismoCodigoBarras != null && !productoConMismoCodigoBarras.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un producto con ese código de barras");
        }
        
        // Actualizar los campos
        productoExistente.setSku(productoDto.getSku());
        productoExistente.setCodigoBarras(productoDto.getCodigoBarras());
        productoExistente.setNombre(productoDto.getNombre());
        productoExistente.setDescripcion(productoDto.getDescripcion());
        productoExistente.setPrecioUnitario(productoDto.getPrecioUnitario());
        productoExistente.setAfectoImpuesto(productoDto.isAfectoImpuesto());
        productoExistente.setStockActual(productoDto.getStockActual());
        productoExistente.setEstado(productoDto.isEstado());
        
        Producto productoGuardado = productoService.guardarProducto(productoExistente);
        return ResponseEntity.ok(productoGuardado);
    }

    // Obtener productos activos
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> obtenerProductosActivos() {
        List<Producto> productos = productoService.obtenerProductosActivos();
        return ResponseEntity.ok(productos);
    }

    // Obtener productos inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<List<Producto>> obtenerProductosInactivos() {
        List<Producto> productos = productoService.obtenerProductosInactivos();
        return ResponseEntity.ok(productos);
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductosPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.obtenerProductosPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<Producto> obtenerProductoPorSku(@PathVariable String sku) {
        Producto producto = productoService.obtenerProductoPorSku(sku);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener producto por código de barras
    @GetMapping("/codigo-barras/{codigoBarras}")
    public ResponseEntity<Producto> obtenerProductoPorCodigoBarras(@PathVariable String codigoBarras) {
        Producto producto = productoService.obtenerProductoPorCodigoBarras(codigoBarras);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener productos con stock bajo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerProductosConStockBajo(@RequestParam Double stockMinimo) {
        List<Producto> productos = productoService.obtenerProductosConStockBajo(stockMinimo);
        return ResponseEntity.ok(productos);
    }

    // Obtener productos con stock disponible
    @GetMapping("/con-stock")
    public ResponseEntity<List<Producto>> obtenerProductosConStock() {
        List<Producto> productos = productoService.obtenerProductosConStock();
        return ResponseEntity.ok(productos);
    }

    // Obtener productos sin stock
    @GetMapping("/sin-stock")
    public ResponseEntity<List<Producto>> obtenerProductosSinStock() {
        List<Producto> productos = productoService.obtenerProductosSinStock();
        return ResponseEntity.ok(productos);
    }

    // Obtener productos afectos a impuesto
    @GetMapping("/afectos-impuesto")
    public ResponseEntity<List<Producto>> obtenerProductosAfectosImpuesto() {
        List<Producto> productos = productoService.obtenerProductosAfectosImpuesto();
        return ResponseEntity.ok(productos);
    }

    // Obtener productos no afectos a impuesto
    @GetMapping("/no-afectos-impuesto")
    public ResponseEntity<List<Producto>> obtenerProductosNoAfectosImpuesto() {
        List<Producto> productos = productoService.obtenerProductosNoAfectosImpuesto();
        return ResponseEntity.ok(productos);
    }

    // Obtener productos por rango de precios
    @GetMapping("/rango-precios")
    public ResponseEntity<List<Producto>> obtenerProductosPorRangoPrecios(
            @RequestParam Double precioMinimo, 
            @RequestParam Double precioMaximo) {
        List<Producto> productos = productoService.obtenerProductosPorRangoPrecios(precioMinimo, precioMaximo);
        return ResponseEntity.ok(productos);
    }

    // Actualizar stock de producto
    @PutMapping("/{id}/stock")
    public ResponseEntity<String> actualizarStock(@PathVariable Long id, @RequestParam Double nuevoStock) {
        Producto producto = productoService.actualizarStock(id, nuevoStock);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Stock actualizado correctamente");
    }

    // Reducir stock de producto
    @PutMapping("/{id}/reducir-stock")
    public ResponseEntity<String> reducirStock(@PathVariable Long id, @RequestParam Double cantidad) {
        Producto producto = productoService.reducirStock(id, cantidad);
        if (producto == null) {
            return ResponseEntity.badRequest().body("No se pudo reducir el stock. Producto no encontrado o stock insuficiente");
        }
        return ResponseEntity.ok("Stock reducido correctamente");
    }

    // Aumentar stock de producto
    @PutMapping("/{id}/aumentar-stock")
    public ResponseEntity<String> aumentarStock(@PathVariable Long id, @RequestParam Double cantidad) {
        Producto producto = productoService.aumentarStock(id, cantidad);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Stock aumentado correctamente");
    }
}
