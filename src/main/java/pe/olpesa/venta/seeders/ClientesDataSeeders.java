package pe.olpesa.venta.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.repositories.ClienteRepository;
import pe.olpesa.venta.utils.EnumDocumento;

@Component
@RequiredArgsConstructor
public class ClientesDataSeeders implements CommandLineRunner{
    private final ClienteRepository clienteRepository;
    
    @Override
    public void run(String... args) throws Exception {
        seedClientes();
    }

    private void seedClientes() {
        System.out.println("Sembrando datos iniciales de clientes...");
        Cliente cliente1 = clienteRepository.findByNumeroDocumento("123456789");
        if (cliente1 == null) {
            cliente1 = new Cliente();
            cliente1.setTipoDocumento(EnumDocumento.DNI);
            cliente1.setNumeroDocumento("123456789");
            cliente1.setNombreRazon("Juan Perez");
            cliente1.setDireccion("Av. Siempre Viva 123");
            cliente1.setTelefono("987654321");
            cliente1.setEmail("juan.perez@example.com");
            cliente1.setEstado(true);
            clienteRepository.save(cliente1);
        }
        Cliente cliente2 = clienteRepository.findByNumeroDocumento("987654321");
        if (cliente2 == null) {
            cliente2 = new Cliente();
            cliente2.setTipoDocumento(EnumDocumento.DNI);
            cliente2.setNumeroDocumento("987654321");
            cliente2.setNombreRazon("Maria Lopez");
            cliente2.setDireccion("Calle Falsa 456");
            cliente2.setTelefono("912345678");
            cliente2.setEmail("maria.lopez@example.com");
            cliente2.setEstado(false);
            clienteRepository.save(cliente2);
        }

        Cliente cliente3 = clienteRepository.findByNumeroDocumento("456789123");
        if (cliente3 == null) {
            cliente3 = new Cliente();
            cliente3.setTipoDocumento(EnumDocumento.DNI);
            cliente3.setNumeroDocumento("456789123");
            cliente3.setNombreRazon("Carlos Ruiz");
            cliente3.setDireccion("Jr. Los Olivos 789");
            cliente3.setTelefono("934567890");
            cliente3.setEmail("carlos.ruiz@example.com");
            cliente3.setEstado(true);
            clienteRepository.save(cliente3);
        }

        Cliente cliente4 = clienteRepository.findByNumeroDocumento("321654987");
        if (cliente4 == null) {
            cliente4 = new Cliente();
            cliente4.setTipoDocumento(EnumDocumento.DNI);
            cliente4.setNumeroDocumento("321654987");
            cliente4.setNombreRazon("Ana Torres");
            cliente4.setDireccion("Av. Las Flores 321");
            cliente4.setTelefono("945678123");
            cliente4.setEmail("ana.torres@example.com");
            cliente4.setEstado(true);
            clienteRepository.save(cliente4);
        }

        Cliente cliente5 = clienteRepository.findByNumeroDocumento("789123456");
        if (cliente5 == null) {
            cliente5 = new Cliente();
            cliente5.setTipoDocumento(EnumDocumento.DNI);
            cliente5.setNumeroDocumento("789123456");
            cliente5.setNombreRazon("Luis Mendoza");
            cliente5.setDireccion("Calle Sol 654");
            cliente5.setTelefono("956789432");
            cliente5.setEmail("luis.mendoza@example.com");
            cliente5.setEstado(true);
            clienteRepository.save(cliente5);
        }
    }
}
