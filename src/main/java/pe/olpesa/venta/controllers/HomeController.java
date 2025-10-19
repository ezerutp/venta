package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.services.ClienteService;
import pe.olpesa.venta.services.ProductoService;
import pe.olpesa.venta.services.UsuarioService;
import pe.olpesa.venta.services.VentaService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final UsuarioService usuarioService;
    
    @GetMapping
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model) {
        // Agregar variables para el dashboard
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "Resumen general del sistema");

        // Dashboard data - Estadísticas principales
        model.addAttribute("totalClientes", clienteService.listarClientes().size());
        model.addAttribute("totalProductos", productoService.listarProductos().size());
        model.addAttribute("ventasHoy", ventaService.calcularIngresosDelDia());
        model.addAttribute("totalAdministradores", usuarioService.contarUsuariosPorRol("ADMIN"));

        // Datos adicionales para el dashboard
        model.addAttribute("crecimientoClientes", "+12%"); // Este mes
        model.addAttribute("productosNuevos", "+5"); // Nuevos productos
        model.addAttribute("crecimientoVentas", "+8%"); // Vs ayer

        // Últimas transacciones para mostrar en la tabla
        model.addAttribute("ultimasVentas", ventaService.obtenerUltimasVentas(5));

        return "home";
    }
}
