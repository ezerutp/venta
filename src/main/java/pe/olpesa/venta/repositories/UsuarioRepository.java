package pe.olpesa.venta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.utils.EnumRol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por username
    Usuario findByUsername(String username);
    
    // Buscar usuario por email
    Usuario findByEmail(String email);
    
    // Buscar usuarios por rol
    List<Usuario> findByRol(EnumRol rol);
    
    // Buscar usuarios por estado
    List<Usuario> findByEstado(boolean estado);
    
    // Buscar usuarios por nombre o apellido (conteniendo texto, ignorando mayúsculas)
    List<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
    
    // Contar usuarios por rol
    int countByRol(EnumRol rol);
}
