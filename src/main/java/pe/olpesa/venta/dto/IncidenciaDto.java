package pe.olpesa.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidenciaDto {
    
    // Información General
    private String titulo;
    private String tipoIncidencia;
    private String prioridad;
    
    // Cliente y Venta Relacionada
    private Long clienteId;
    private Long ventaId;
    private Long productoId;
    private String numeroDocumento;
    
    // Descripción del Problema
    private String motivo;
    private String impacto;
    private String accionesTomadas;
    
    // Datos de Contacto
    private String nombreContacto;
    private String telefonoContacto;
    private String emailContacto;
    
    // Información Adicional
    private LocalDate fechaIncidente;
    private String canalRecepcion;
    private BigDecimal montoInvolucrado;
    private Long asignadoA;
    private String notasAdicionales;
}
