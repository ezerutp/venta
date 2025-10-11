package pe.olpesa.venta.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.utils.EnumDocumento;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumDocumento tipoDocumento;

    @NotBlank(message = "El numero de documento no puede estar vacio")
    @Size(min = 8, max = 11, message = "El numero de documento debe tener entre 8 y 11 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El numero de documento debe ser numerico")
    @Column(unique = true)
    private String numeroDocumento;

    @NotBlank(message = "El nombre o razon social no puede estar vacio")
    private String nombreRazon;

    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(min = 5, message = "La direccion debe tener al menos 5 caracteres")
    private String direccion;

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "El correo debe ser valido")
    private String email;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El telefono debe tener 9 digitos")
    private String telefono;

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
