package pe.olpesa.venta.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.repositories.UsuarioRepository;
import pe.olpesa.venta.utils.EnumRol;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Buscar Usuario por email
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Buscar Usuarios por rol
    public List<Usuario> obtenerUsuariosPorRol(EnumRol rol) {
        return usuarioRepository.findByRol(rol);
    }

    // Buscar Usuarios por nombre o apellido
    public List<Usuario> obtenerUsuariosPorNombreOApellido(String termino) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(termino, termino);
    }

    // Buscar usuarios activos
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByEstado(true);
    }

    // Buscar usuarios inactivos
    public List<Usuario> obtenerUsuariosInactivos() {
        return usuarioRepository.findByEstado(false);
    }

    // Crear o actualizar usuario
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // Eliminar (desactivar) usuario por ID
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Activar/Desactivar usuario
    public Usuario cambiarEstadoUsuario(Long id, boolean estado) {
        Usuario usuario = obtenerUsuarioPorId(id);
        if (usuario != null) {
            usuario.setEstado(estado);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Contar usuarios activos
    public int contarUsuariosActivos() {
        return (int) listarUsuarios().stream()
                .filter(usuario -> usuario.isEstado())
                .count();
    }

    // Contar usuarios inactivos
    public int contarUsuariosInactivos() {
        return (int) listarUsuarios().stream()
                .filter(usuario -> usuario.isEstado() == false)
                .count();
    }
}
