package pe.olpesa.venta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.olpesa.venta.utils.EnumRol;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioActualizacionDto {
    
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 3, message = "El apellido debe tener al menos 3 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "El usuario no puede estar vacio")
    @Size(min = 3, message = "El usuario debe tener al menos 3 caracteres")
    private String username;

    // La contraseña es opcional en actualizaciones
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private EnumRol rol;

    @NotNull(message = "El estado no puede estar vacio")
    private boolean estado;
}