package pe.olpesa.venta.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Usuario;

@RequiredArgsConstructor
public class UsuarioDetalle implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority sAuthority = new SimpleGrantedAuthority(usuario.getRol().name());
        return Arrays.asList(sAuthority);
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }
    
}