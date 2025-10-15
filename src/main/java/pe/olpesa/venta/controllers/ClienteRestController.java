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
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteRestController {
    private final ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        // Verificar que el número de documento no esté en uso
        Cliente clienteExistente = clienteService.obtenerClientePorTipoYNumeroDocumento(
            cliente.getTipoDocumento(), 
            cliente.getNumeroDocumento()
        );
        
        if (clienteExistente != null) {
            return ResponseEntity.badRequest().body("Ya existe un cliente con ese número de documento");
        }
        
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(clienteGuardado);
    }

    // eliminar (desactivar) un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }

    // activar un cliente
    @PutMapping("/activar/{id}")
    public ResponseEntity<String> activarCliente(@PathVariable Long id) {
        clienteService.activarCliente(id);
        return ResponseEntity.ok("Cliente activado correctamente");
    }
    
    // Obtener datos de un cliente para edición
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClienteParaEdicion(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente clienteActualizado, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }
        
        Cliente clienteExistente = clienteService.obtenerClientePorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar que el número de documento no esté en uso por otro cliente
        Cliente clienteConMismoDocumento = clienteService.obtenerClientePorTipoYNumeroDocumento(
            clienteActualizado.getTipoDocumento(), 
            clienteActualizado.getNumeroDocumento()
        );
        
        if (clienteConMismoDocumento != null && !clienteConMismoDocumento.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un cliente con ese número de documento");
        }
        
        // Actualizar los campos
        clienteExistente.setTipoDocumento(clienteActualizado.getTipoDocumento());
        clienteExistente.setNumeroDocumento(clienteActualizado.getNumeroDocumento());
        clienteExistente.setNombreRazon(clienteActualizado.getNombreRazon());
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
        clienteExistente.setEmail(clienteActualizado.getEmail());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        
        Cliente clienteGuardado = clienteService.guardarCliente(clienteExistente);
        return ResponseEntity.ok(clienteGuardado);
    }
}
