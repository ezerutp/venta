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
}
