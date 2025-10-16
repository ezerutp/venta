package pe.olpesa.venta.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.dto.UsuarioActualizacionDto;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {
    private final UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        // Verificar que el email no esté en uso
        Usuario usuarioExistentePorEmail = usuarioService.obtenerUsuarioPorEmail(usuario.getEmail());
        if (usuarioExistentePorEmail != null) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese email");
        }
        
        // Establecer estado activo por defecto
        usuario.setEstado(true);
        
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    // Activar un usuario
    @PutMapping("/activar/{id}")
    public ResponseEntity<String> activarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.cambiarEstadoUsuario(id, true);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuario activado correctamente");
    }

    // Desactivar un usuario
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.cambiarEstadoUsuario(id, false);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuario desactivado correctamente");
    }
    
    // Obtener datos de un usuario para edición
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioParaEdicion(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioActualizacionDto usuarioDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar que el email no esté en uso por otro usuario
        Usuario usuarioConMismoEmail = usuarioService.obtenerUsuarioPorEmail(usuarioDto.getEmail());
        if (usuarioConMismoEmail != null && !usuarioConMismoEmail.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese email");
        }
        
        // Actualizar los campos (excepto la contraseña si está vacía)
        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setApellido(usuarioDto.getApellido());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setUsername(usuarioDto.getUsername());
        usuarioExistente.setRol(usuarioDto.getRol());
        usuarioExistente.setEstado(usuarioDto.isEstado());
        
        Usuario usuarioGuardado;
        // Solo actualizar la contraseña si se proporciona una nueva
        if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().trim().isEmpty()) {
            usuarioGuardado = usuarioService.actualizarUsuarioConNuevaPassword(usuarioExistente, usuarioDto.getPassword());
        } else {
            // Actualizar sin cambiar la contraseña
            usuarioGuardado = usuarioService.actualizarUsuario(usuarioExistente);
        }
        
        return ResponseEntity.ok(usuarioGuardado);
    }

    // Obtener usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener usuarios inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<List<Usuario>> obtenerUsuariosInactivos() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosInactivos();
        return ResponseEntity.ok(usuarios);
    }
}
