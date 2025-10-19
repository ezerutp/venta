package pe.olpesa.venta.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Venta;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.repositories.VentaRepository;
import pe.olpesa.venta.utils.EnumEstadoVenta;
import pe.olpesa.venta.utils.EnumComprobante;
import pe.olpesa.venta.utils.EnumMetodoPago;

@Service
@RequiredArgsConstructor
public class VentaService {
    
    private final VentaRepository ventaRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAllWithDetails();
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }
    
    public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta crearVenta(Usuario usuario, Cliente cliente, Producto producto, 
                           Integer cantidad, EnumComprobante tipoComprobante, 
                           EnumMetodoPago metodoPago, Double montoPagado) {
        
        // Validar datos de entrada
        if (usuario == null || cliente == null || producto == null) {
            throw new IllegalArgumentException("Usuario, cliente y producto son obligatorios");
        }
        
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        if (montoPagado == null || montoPagado <= 0) {
            throw new IllegalArgumentException("El monto pagado debe ser mayor a 0");
        }
        
        // Calcular valores
        Double precioUnitario = producto.getPrecioUnitario();
        Double subtotal = cantidad * precioUnitario;
        boolean afectoImpuesto = producto.isAfectoImpuesto();
        Double impuestoTotal;
        if (!afectoImpuesto) {
            impuestoTotal = 0.0;
        } else {
            impuestoTotal = subtotal * 0.18;
        }
        
        Double total = subtotal + impuestoTotal;
        
        // Validar que el monto pagado sea suficiente
        if (montoPagado < total) {
            throw new IllegalArgumentException("El monto pagado es insuficiente. Total: " + total);
        }
        
        // Crear la venta
        Venta venta = Venta.builder()
                .usuario(usuario)
                .cliente(cliente)
                .producto(producto)
                .cantidad(cantidad)
                .precioUnitario(precioUnitario)
                .tipoComprobante(tipoComprobante)
                .subtotal(subtotal)
                .impuestoTotal(impuestoTotal)
                .total(total)
                .metodoPago(metodoPago)
                .montoPagado(montoPagado)
                .estado(EnumEstadoVenta.PENDIENTE)
                .build();
        
        return ventaRepository.save(venta);
    }

    public Venta actualizarVenta(Long id, Usuario usuario, Cliente cliente, Producto producto,
                                Integer cantidad, EnumComprobante tipoComprobante,
                                EnumMetodoPago metodoPago, Double montoPagado) {
        
        Venta ventaExistente = buscarVentaPorId(id);
        if (ventaExistente == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + id);
        }
        
        // Solo permitir actualizar ventas en estado PENDIENTE
        if (ventaExistente.getEstado() != EnumEstadoVenta.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden actualizar ventas en estado PENDIENTE");
        }
        
        // Validar datos de entrada
        if (usuario == null || cliente == null || producto == null) {
            throw new IllegalArgumentException("Usuario, cliente y producto son obligatorios");
        }
        
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        if (montoPagado == null || montoPagado <= 0) {
            throw new IllegalArgumentException("El monto pagado debe ser mayor a 0");
        }
        
        // Calcular nuevos valores
        Double precioUnitario = producto.getPrecioUnitario();
        Double subtotal = cantidad * precioUnitario;
        Double impuestoTotal = subtotal * 0.18;
        Double total = subtotal + impuestoTotal;
        
        // Validar que el monto pagado sea suficiente
        if (montoPagado < total) {
            throw new IllegalArgumentException("El monto pagado es insuficiente. Total: " + total);
        }
        
        // Actualizar la venta
        ventaExistente.setUsuario(usuario);
        ventaExistente.setCliente(cliente);
        ventaExistente.setProducto(producto);
        ventaExistente.setCantidad(cantidad);
        ventaExistente.setPrecioUnitario(precioUnitario);
        ventaExistente.setTipoComprobante(tipoComprobante);
        ventaExistente.setSubtotal(subtotal);
        ventaExistente.setImpuestoTotal(impuestoTotal);
        ventaExistente.setTotal(total);
        ventaExistente.setMetodoPago(metodoPago);
        ventaExistente.setMontoPagado(montoPagado);
        
        return ventaRepository.save(ventaExistente);
    }

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public boolean eliminarVenta(Long id) {
        Optional<Venta> venta = obtenerVentaPorId(id);
        if (venta.isPresent()) {
            // Solo permitir eliminar ventas en estado PENDIENTE
            if (venta.get().getEstado() == EnumEstadoVenta.PENDIENTE) {
                ventaRepository.deleteById(id);
                return true;
            } else {
                throw new IllegalArgumentException("Solo se pueden eliminar ventas en estado PENDIENTE");
            }
        }
        return false;
    }

    public Venta confirmarVenta(Long id) {
        Venta venta = buscarVentaPorId(id);
        if (venta == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + id);
        }
        
        if (venta.getEstado() != EnumEstadoVenta.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden confirmar ventas en estado PENDIENTE");
        }
        
        venta.setEstado(EnumEstadoVenta.CONFIRMADA);
        return ventaRepository.save(venta);
    }

    public Venta cancelarVenta(Long id) {
        Venta venta = buscarVentaPorId(id);
        if (venta == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + id);
        }
        
        if (venta.getEstado() == EnumEstadoVenta.CANCELADA) {
            throw new IllegalArgumentException("La venta ya está cancelada");
        }
        
        venta.setEstado(EnumEstadoVenta.CANCELADA);
        return ventaRepository.save(venta);
    }

    public List<Venta> obtenerVentasPorEstado(EnumEstadoVenta estado) {
        return listarVentas().stream()
                .filter(venta -> venta.getEstado() == estado)
                .toList();
    }

    public List<Venta> obtenerVentasPorCliente(Long clienteId) {
        return listarVentas().stream()
                .filter(venta -> venta.getCliente().getId().equals(clienteId))
                .toList();
    }

    public List<Venta> obtenerVentasPorUsuario(Long usuarioId) {
        return listarVentas().stream()
                .filter(venta -> venta.getUsuario().getId().equals(usuarioId))
                .toList();
    }

    public List<Venta> obtenerVentasPorProducto(Long productoId) {
        return listarVentas().stream()
                .filter(venta -> venta.getProducto().getId().equals(productoId))
                .toList();
    }

    public List<Venta> obtenerVentasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return listarVentas().stream()
                .filter(venta -> {
                    LocalDateTime fechaVenta = venta.getFechaEmitido();
                    return fechaVenta.isAfter(fechaInicio) && fechaVenta.isBefore(fechaFin);
                })
                .toList();
    }

    public long contarTotalVentas() {
        return ventaRepository.count();
    }

    public long contarVentasPorEstado(EnumEstadoVenta estado) {
        return obtenerVentasPorEstado(estado).size();
    }

    public Double calcularTotalIngresos() {
        return obtenerVentasPorEstado(EnumEstadoVenta.CONFIRMADA).stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Double calcularIngresosPorCliente(Long clienteId) {
        return obtenerVentasPorCliente(clienteId).stream()
                .filter(venta -> venta.getEstado() == EnumEstadoVenta.CONFIRMADA)
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Double calcularIngresosPorUsuario(Long usuarioId) {
        return obtenerVentasPorUsuario(usuarioId).stream()
                .filter(venta -> venta.getEstado() == EnumEstadoVenta.CONFIRMADA)
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public List<Venta> obtenerVentasDelDia() {
        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return obtenerVentasPorFecha(inicioDia, finDia);
    }

    public Double calcularIngresosDelDia() {
        return obtenerVentasDelDia().stream()
                .filter(venta -> venta.getEstado() == EnumEstadoVenta.CONFIRMADA)
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public boolean existeVenta(Long id) {
        return ventaRepository.existsById(id);
    }
    
    public boolean puedeModificarVenta(Long id) {
        Venta venta = buscarVentaPorId(id);
        return venta != null && venta.getEstado() == EnumEstadoVenta.PENDIENTE;
    }

    public boolean puedeEliminarVenta(Long id) {
        return puedeModificarVenta(id);
    }

    // Ultima 3 ventas
    public List<Venta> obtenerUltimasVentas(int cantidad) {
        Pageable pageable = PageRequest.of(0, cantidad);
        return ventaRepository.findTopVentasWithDetails(pageable);
    }
}