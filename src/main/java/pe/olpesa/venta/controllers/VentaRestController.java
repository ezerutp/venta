package pe.olpesa.venta.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Venta;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.dto.VentaDetalleDto;
import pe.olpesa.venta.services.VentaService;
import pe.olpesa.venta.services.ClienteService;
import pe.olpesa.venta.services.ProductoService;
import pe.olpesa.venta.services.UsuarioService;
import pe.olpesa.venta.utils.EnumComprobante;
import pe.olpesa.venta.utils.EnumEstadoVenta;
import pe.olpesa.venta.utils.EnumMetodoPago;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaRestController {
    
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        try {
            List<Venta> ventas = ventaService.listarVentas();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDetalleDto> obtenerVenta(@PathVariable Long id) {
        try {
            Venta venta = ventaService.buscarVentaPorId(id);
            if (venta != null) {
                VentaDetalleDto ventaDto = VentaDetalleDto.fromEntity(venta);
                return ResponseEntity.ok(ventaDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearVenta(@RequestBody Map<String, Object> ventaData) {
        try {
            // Obtener datos del request
            Long usuarioId = Long.valueOf(ventaData.get("usuarioId").toString());
            Long clienteId = Long.valueOf(ventaData.get("clienteId").toString());
            Long productoId = Long.valueOf(ventaData.get("productoId").toString());
            Integer cantidad = Integer.valueOf(ventaData.get("cantidad").toString());
            String tipoComprobanteStr = ventaData.get("tipoComprobante").toString();
            String metodoPagoStr = ventaData.get("metodoPago").toString();
            Double montoPagado = Double.valueOf(ventaData.get("montoPagado").toString());

            // Obtener entidades
            Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
            Cliente cliente = clienteService.obtenerClientePorId(clienteId);
            Producto producto = productoService.obtenerProductoPorId(productoId);

            if (usuario == null || cliente == null || producto == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Usuario, cliente o producto no encontrado"));
            }

            // Validar stock
            if (producto.getStockActual() < cantidad) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Stock insuficiente"));
            }

            // Convertir enums
            EnumComprobante tipoComprobante = EnumComprobante.valueOf(tipoComprobanteStr);
            EnumMetodoPago metodoPago = EnumMetodoPago.valueOf(metodoPagoStr);

            // Crear la venta
            Venta venta = ventaService.crearVenta(
                usuario, cliente, producto, cantidad, 
                tipoComprobante, metodoPago, montoPagado
            );

            // Actualizar stock del producto
            producto.setStockActual(producto.getStockActual() - cantidad);
            productoService.guardarProducto(producto);

            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Venta creada exitosamente",
                "venta", venta
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error al crear la venta: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarVenta(@PathVariable Long id, @RequestBody Map<String, Object> ventaData) {
        try {
            // Verificar si la venta existe
            Venta ventaExistente = ventaService.buscarVentaPorId(id);
            if (ventaExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // Solo permitir actualizar ventas pendientes
            if (ventaExistente.getEstado() != EnumEstadoVenta.PENDIENTE) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Solo se pueden actualizar ventas pendientes"));
            }

            // Restaurar stock del producto anterior
            Producto productoAnterior = ventaExistente.getProducto();
            productoAnterior.setStockActual(productoAnterior.getStockActual() + ventaExistente.getCantidad());
            productoService.guardarProducto(productoAnterior);

            // Obtener datos del request
            Long usuarioId = Long.valueOf(ventaData.get("usuarioId").toString());
            Long clienteId = Long.valueOf(ventaData.get("clienteId").toString());
            Long productoId = Long.valueOf(ventaData.get("productoId").toString());
            Integer cantidad = Integer.valueOf(ventaData.get("cantidad").toString());
            String tipoComprobanteStr = ventaData.get("tipoComprobante").toString();
            String metodoPagoStr = ventaData.get("metodoPago").toString();
            Double montoPagado = Double.valueOf(ventaData.get("montoPagado").toString());

            // Obtener entidades
            Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
            Cliente cliente = clienteService.obtenerClientePorId(clienteId);
            Producto producto = productoService.obtenerProductoPorId(productoId);

            if (usuario == null || cliente == null || producto == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Usuario, cliente o producto no encontrado"));
            }

            // Validar stock del nuevo producto
            if (producto.getStockActual() < cantidad) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Stock insuficiente"));
            }

            // Convertir enums
            EnumComprobante tipoComprobante = EnumComprobante.valueOf(tipoComprobanteStr);
            EnumMetodoPago metodoPago = EnumMetodoPago.valueOf(metodoPagoStr);

            // Actualizar la venta
            Venta venta = ventaService.actualizarVenta(
                id, usuario, cliente, producto, cantidad, 
                tipoComprobante, metodoPago, montoPagado
            );

            // Actualizar stock del nuevo producto
            producto.setStockActual(producto.getStockActual() - cantidad);
            productoService.guardarProducto(producto);

            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Venta actualizada exitosamente",
                "venta", venta
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error al actualizar la venta: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Map<String, Object>> confirmarVenta(@PathVariable Long id) {
        try {
            Venta venta = ventaService.confirmarVenta(id);
            if (venta == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Venta confirmada exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error al confirmar la venta: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Map<String, Object>> cancelarVenta(@PathVariable Long id) {
        try {
            // Obtener la venta antes de cancelar para restaurar stock
            Venta venta = ventaService.buscarVentaPorId(id);
            if (venta != null && venta.getEstado() != EnumEstadoVenta.CANCELADA) {
                // Restaurar stock
                Producto producto = venta.getProducto();
                producto.setStockActual(producto.getStockActual() + venta.getCantidad());
                productoService.guardarProducto(producto);
            }

            Venta ventaCancelada = ventaService.cancelarVenta(id);
            VentaDetalleDto ventaDto = VentaDetalleDto.fromEntity(ventaCancelada);
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Venta cancelada exitosamente",
                "venta", ventaDto
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error al cancelar la venta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarVenta(@PathVariable Long id) {
        try {
            // Obtener la venta antes de eliminar para restaurar stock
            Venta venta = ventaService.buscarVentaPorId(id);
            if (venta != null && venta.getEstado() != EnumEstadoVenta.CANCELADA) {
                // Restaurar stock
                Producto producto = venta.getProducto();
                producto.setStockActual(producto.getStockActual() + venta.getCantidad());
                productoService.guardarProducto(producto);
            }

            boolean eliminado = ventaService.eliminarVenta(id);
            if (eliminado) {
                return ResponseEntity.ok(Map.of(
                    "success", true, 
                    "message", "Venta eliminada exitosamente"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Error al eliminar la venta: " + e.getMessage()));
        }
    }

    // Endpoints adicionales para obtener datos relacionados
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> obtenerClientes() {
        try {
            List<Cliente> clientes = clienteService.listarClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        try {
            List<Producto> productos = productoService.listarProductos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            Map<String, Object> estadisticas = Map.of(
                "totalVentas", ventaService.contarTotalVentas(),
                "ventasPendientes", ventaService.contarVentasPorEstado(EnumEstadoVenta.PENDIENTE),
                "ventasConfirmadas", ventaService.contarVentasPorEstado(EnumEstadoVenta.CONFIRMADA),
                "ventasCanceladas", ventaService.contarVentasPorEstado(EnumEstadoVenta.CANCELADA),
                "totalIngresos", ventaService.calcularTotalIngresos(),
                "ingresosDelDia", ventaService.calcularIngresosDelDia()
            );
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
