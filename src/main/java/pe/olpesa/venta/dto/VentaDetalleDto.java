package pe.olpesa.venta.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.entities.Venta;
import pe.olpesa.venta.utils.EnumComprobante;
import pe.olpesa.venta.utils.EnumEstadoVenta;
import pe.olpesa.venta.utils.EnumMetodoPago;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetalleDto {
    
    private Long id;
    private LocalDateTime fechaEmitido;
    private EnumEstadoVenta estado;
    private EnumComprobante tipoComprobante;
    private EnumMetodoPago metodoPago;
    private String moneda;
    
    // Información del vendedor/usuario
    private Long usuarioId;
    private String nombreVendedor;
    
    // Información del cliente
    private Long clienteId;
    private String clienteNombreRazon;
    private String clienteNumeroDocumento;
    private String clienteEmail;
    private String clienteTelefono;
    
    // Información del producto
    private Long productoId;
    private String productoNombre;
    private String productoSku;
    private Integer cantidad;
    private Double precioUnitario;
    
    // Totales
    private Double subtotal;
    private Double impuestoTotal;
    private Double total;
    private Double montoPagado;
    private Double cambio;
    
    public static VentaDetalleDto fromEntity(Venta venta) {
        return VentaDetalleDto.builder()
            .id(venta.getId())
            .fechaEmitido(venta.getFechaEmitido())
            .estado(venta.getEstado())
            .tipoComprobante(venta.getTipoComprobante())
            .metodoPago(venta.getMetodoPago())
            .moneda(venta.getMoneda())
            
            // Usuario/Vendedor
            .usuarioId(venta.getUsuario().getId())
            .nombreVendedor(venta.getUsuario().getNombre() + " " + venta.getUsuario().getApellido())
            
            // Cliente
            .clienteId(venta.getCliente().getId())
            .clienteNombreRazon(venta.getCliente().getNombreRazon())
            .clienteNumeroDocumento(venta.getCliente().getNumeroDocumento())
            .clienteEmail(venta.getCliente().getEmail())
            .clienteTelefono(venta.getCliente().getTelefono())
            
            // Producto
            .productoId(venta.getProducto().getId())
            .productoNombre(venta.getProducto().getNombre())
            .productoSku(venta.getProducto().getSku())
            .cantidad(venta.getCantidad())
            .precioUnitario(venta.getPrecioUnitario())
            
            // Totales
            .subtotal(venta.getSubtotal())
            .impuestoTotal(venta.getImpuestoTotal())
            .total(venta.getTotal())
            .montoPagado(venta.getMontoPagado())
            .cambio(venta.getCambio())
            .build();
    }
}