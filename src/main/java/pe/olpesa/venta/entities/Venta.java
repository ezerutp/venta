package pe.olpesa.venta.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.utils.EnumComprobante;
import pe.olpesa.venta.utils.EnumEstadoVenta;
import pe.olpesa.venta.utils.EnumMetodoPago;

@Entity
@Table(name = "ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull(message = "La cantidad no puede estar vacía")
    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante", nullable = false)
    private EnumComprobante tipoComprobante;

    @CreationTimestamp
    @Column(name = "fecha_emitido", nullable = false, updatable = false)
    private LocalDateTime fechaEmitido;

    @Builder.Default
    @Column(nullable = false, length = 3)
    private String moneda = "PEN";

    @NotNull(message = "El subtotal no puede estar vacio")
    @Column(nullable = false)
    private Double subtotal;

    @NotNull(message = "El impuesto total no puede estar vacio")
    @Column(name = "impuesto_total", nullable = false)
    private Double impuestoTotal;

    @NotNull(message = "El total no puede estar vacio")
    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private EnumMetodoPago metodoPago;
    
    @NotNull(message = "El monto pagado no puede estar vacio")
    @Column(name = "monto_pagado", nullable = false)
    private Double montoPagado;

    @Builder.Default
    @Column(nullable = false)
    private EnumEstadoVenta estado = EnumEstadoVenta.PENDIENTE;

    public String getNombreVendedor() {
        if (usuario != null) {
            return usuario.getNombre() + " " + usuario.getApellido();
        }
        return "";
    }

    public String getNombreProducto() {
        if (producto != null) {
            return producto.getNombre();
        }
        return "";
    }

    public String getSkuProducto() {
        if (producto != null) {
            return producto.getSku();
        }
        return "";
    }
    
    public Double calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            return cantidad * precioUnitario;
        }
        return 0.0;
    }
    
    public Double calcularImpuesto() {
        Double subtotalCalculado = calcularSubtotal();
        return subtotalCalculado * 0.18;
    }
    
    public Double calcularTotal() {
        return calcularSubtotal() + calcularImpuesto();
    }
    
    public Double getCambio() {
        if (montoPagado != null && total != null) {
            return montoPagado - total;
        }
        return 0.0;
    }

    public String getResumenVenta() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("=== DETALLE DE VENTA ===\n");
        resumen.append("ID Venta: ").append(id).append("\n");
        resumen.append("Fecha: ").append(fechaEmitido).append("\n");
        resumen.append("Vendedor: ").append(getNombreVendedor()).append("\n");
        resumen.append("Cliente: ").append(cliente != null ? cliente.getNombreRazon() : "").append("\n");
        resumen.append("\n--- PRODUCTO ---\n");
        resumen.append("Producto: ").append(getNombreProducto()).append("\n");
        resumen.append("SKU: ").append(getSkuProducto()).append("\n");
        resumen.append("Cantidad: ").append(cantidad).append("\n");
        resumen.append("Precio Unitario: ").append(moneda).append(" ").append(precioUnitario).append("\n");
        resumen.append("\n--- TOTALES ---\n");
        resumen.append("Subtotal: ").append(moneda).append(" ").append(String.format("%.2f", subtotal)).append("\n");
        resumen.append("Impuestos: ").append(moneda).append(" ").append(String.format("%.2f", impuestoTotal)).append("\n");
        resumen.append("Total: ").append(moneda).append(" ").append(String.format("%.2f", total)).append("\n");
        resumen.append("\n--- PAGO ---\n");
        resumen.append("Método de Pago: ").append(metodoPago).append("\n");
        resumen.append("Monto Pagado: ").append(moneda).append(" ").append(String.format("%.2f", montoPagado)).append("\n");
        resumen.append("Cambio: ").append(moneda).append(" ").append(String.format("%.2f", getCambio())).append("\n");
        resumen.append("\n--- OTROS ---\n");
        resumen.append("Tipo Comprobante: ").append(tipoComprobante).append("\n");
        resumen.append("Estado: ").append(estado).append("\n");
        resumen.append("Moneda: ").append(moneda).append("\n");
        
        return resumen.toString();
    }
}
