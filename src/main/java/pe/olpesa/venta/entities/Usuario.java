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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.utils.EnumRol;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser valido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El usuario no puede estar vacio")
    @Size(min = 3, message = "El usuario debe tener al menos 3 caracteres")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private EnumRol rol;

    @NotNull(message = "El estado no puede estar vacio")
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
