package pe.olpesa.venta.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.repositories.UsuarioRepository;
import pe.olpesa.venta.utils.EnumRol;

@Component
@RequiredArgsConstructor
public class UsuariosDataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUsuarios();
    }

    private void seedUsuarios() {
        System.out.println("Sembrando datos iniciales de usuarios...");
        Usuario user1 = usuarioRepository.findByUsername("admin");
        if (user1 == null) {
            user1 = new Usuario();
            user1.setNombre("Admin");
            user1.setApellido("Administrador");
            user1.setEmail("admin@admin.pe");
            user1.setUsername("admin");
            user1.setPassword(passwordEncoder.encode("123456"));
            user1.setRol(EnumRol.ADMIN);
            user1.setEstado(true);
            usuarioRepository.save(user1);
        }
        Usuario user2 = usuarioRepository.findByUsername("123456");
        if (user2 == null) {
            user2 = new Usuario();
            user2.setNombre("John");
            user2.setApellido("Doe");
            user2.setEmail("jdoe@example.com");
            user2.setUsername("jdoe");
            user2.setPassword(passwordEncoder.encode("123456"));
            user2.setRol(EnumRol.USER);
            user2.setEstado(true);
            usuarioRepository.save(user2);
        }

        Usuario user3 = usuarioRepository.findByUsername("msmith");
        if (user3 == null) {
            user3 = new Usuario();
            user3.setNombre("Mary");
            user3.setApellido("Smith");
            user3.setEmail("msmith@example.com");
            user3.setUsername("msmith");
            user3.setPassword(passwordEncoder.encode("123456"));
            user3.setRol(EnumRol.USER);
            user3.setEstado(true);
            usuarioRepository.save(user3);
        }

        Usuario user4 = usuarioRepository.findByUsername("jgarcia");
        if (user4 == null) {
            user4 = new Usuario();
            user4.setNombre("Juan");
            user4.setApellido("Garcia");
            user4.setEmail("jgarcia@example.com");
            user4.setUsername("jgarcia");
            user4.setPassword(passwordEncoder.encode("123456"));
            user4.setRol(EnumRol.USER);
            user4.setEstado(true);
            usuarioRepository.save(user4);
        }

        Usuario user5 = usuarioRepository.findByUsername("lrodriguez");
        if (user5 == null) {
            user5 = new Usuario();
            user5.setNombre("Laura");
            user5.setApellido("Rodriguez");
            user5.setEmail("lrodriguez@example.com");
            user5.setUsername("lrodriguez");
            user5.setPassword(passwordEncoder.encode("123456"));
            user5.setRol(EnumRol.USER);
            user5.setEstado(true);
            usuarioRepository.save(user5);
        }
    }
    
}
