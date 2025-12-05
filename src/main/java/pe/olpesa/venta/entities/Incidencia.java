package pe.olpesa.venta.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.utils.EnumCanalRecepcion;
import pe.olpesa.venta.utils.EnumEstadoIncidencia;
import pe.olpesa.venta.utils.EnumImpacto;
import pe.olpesa.venta.utils.EnumPrioridad;
import pe.olpesa.venta.utils.EnumTipoIncidencia;

@Entity
@Table(name = "incidencias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Información General
    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_incidencia", nullable = false)
    private EnumTipoIncidencia tipoIncidencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumPrioridad prioridad;

    // Cliente y Venta Relacionada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "usuario", "cliente", "producto"})
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    @Column(name = "numero_documento", length = 50)
    private String numeroDocumento;

    // Descripción del Problema
    @Column(nullable = false, length = 1000)
    private String motivo;

    @Enumerated(EnumType.STRING)
    private EnumImpacto impacto;

    @Column(name = "acciones_tomadas", length = 500)
    private String accionesTomadas;

    // Datos de Contacto
    @Column(name = "nombre_contacto", nullable = false, length = 100)
    private String nombreContacto;

    @Column(name = "telefono_contacto", nullable = false, length = 20)
    private String telefonoContacto;

    @Column(name = "email_contacto", length = 100)
    private String emailContacto;

    // Información Adicional
    @Column(name = "fecha_incidente")
    private LocalDate fechaIncidente;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_recepcion")
    private EnumCanalRecepcion canalRecepcion;

    @Column(name = "monto_involucrado", precision = 10, scale = 2)
    private BigDecimal montoInvolucrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignado_a")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Usuario asignadoA;

    @Column(name = "notas_adicionales", length = 500)
    private String notasAdicionales;

    // Estado y seguimiento
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EnumEstadoIncidencia estado = EnumEstadoIncidencia.ABIERTO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Usuario creadoPor;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "resolucion", length = 1000)
    private String resolucion;
}
