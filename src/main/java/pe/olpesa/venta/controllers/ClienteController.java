package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.services.ClienteService;


@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes/listar";
    }
    
}
