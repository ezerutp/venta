package pe.olpesa.venta.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El SKU no puede estar vacio")
    @Size(max = 50, message = "El SKU no puede tener más de 50 caracteres")
    private String sku;

    @NotBlank(message = "El código de barras no puede estar vacío")
    @Pattern(regexp = "^[0-9]+$", message = "El código de barras debe contener solo números")
    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio unitario no puede estar vacío")
    private Double precioUnitario;

    @NotNull(message = "El campo afectoImpuesto no puede estar vacío")
    private boolean afectoImpuesto;

    @NotNull(message = "El stock actual no puede estar vacío")
    private Double stockActual;

    @NotNull(message = "El estado no puede estar vacío")
    private boolean estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
