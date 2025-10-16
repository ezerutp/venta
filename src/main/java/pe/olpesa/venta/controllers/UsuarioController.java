package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.services.UsuarioService;
import pe.olpesa.venta.utils.EnumRol;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping
    public String listarClientes(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {
        var usuarios = usuarioService.listarUsuarios();
        
        // Variables para el header
        model.addAttribute("pageTitle", "Usuarios");
        model.addAttribute("pageSubtitle", "Gestión de usuarios");

        // Datos de usuarios
        model.addAttribute("usuarios", usuarios);

        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("usuariosActivos", usuarioService.contarUsuariosActivos());
        model.addAttribute("usuariosAdmin", usuarioService.obtenerUsuariosPorRol(EnumRol.ADMIN).size());
        model.addAttribute("usuariosInactivos", usuarioService.contarUsuariosInactivos());

        // Paginación ... luego implementar
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", usuarios.size());
        model.addAttribute("totalPages", (int) Math.ceil((double) usuarios.size() / size));

        return "usuarios/index";
    }
    
}