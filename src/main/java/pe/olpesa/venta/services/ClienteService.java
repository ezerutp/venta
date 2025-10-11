package pe.olpesa.venta.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.repositories.ClienteRepository;
import pe.olpesa.venta.utils.EnumDocumento;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Buscar cliente por ID
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    // Buscar Cliente por tipoDocumento y numeroDocumento
    public Cliente obtenerClientePorTipoYNumeroDocumento(EnumDocumento tipoDocumento, String numeroDocumento) {
        return clienteRepository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }

    // Buscar Cliente por nombreRazon
    public List<Cliente> obtenerClientesPorNombreRazon(String nombreRazon) {
        return clienteRepository.findByNombreRazonContainingIgnoreCase(nombreRazon);
    }

    // Crear o actualizar cliente
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Eliminar cliente por ID
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
