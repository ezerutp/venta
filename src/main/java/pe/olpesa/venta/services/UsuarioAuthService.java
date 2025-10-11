package pe.olpesa.venta.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.config.UsuarioDetalle;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioAuthService implements UserDetailsService {
    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario user = repository.findByUsername(usuario);
        if (user == null) { 
            throw new UsernameNotFoundException("Usuario no encontrado"); 
        }
        return new UsuarioDetalle(user);
    }
}
