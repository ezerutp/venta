package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.services.ClienteService;


@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    
    @GetMapping
    public String listarClientes(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {
        var clientes = clienteService.listarClientes();
        
        // Variables para el header
        model.addAttribute("pageTitle", "Clientes");
        model.addAttribute("pageSubtitle", "Gestión de clientes y contactos");
        
        // Datos de clientes
        model.addAttribute("clientes", clientes);
        
        model.addAttribute("totalClientes", clientes.size());
        model.addAttribute("clientesActivos", clienteService.contarClientesActivos());
        model.addAttribute("clientesNuevosMes", 47); // muy pronto jajajja
        model.addAttribute("clientesInactivos", clienteService.contarClientesInactivos());

        // Paginación ... luego implementar
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", clientes.size());
        model.addAttribute("totalPages", (int) Math.ceil((double) clientes.size() / size));
        
        return "clientes/index";
    }
    
}
