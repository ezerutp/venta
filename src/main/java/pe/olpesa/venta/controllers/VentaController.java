package pe.olpesa.venta.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.config.UsuarioDetalle;
import pe.olpesa.venta.services.VentaService;
import pe.olpesa.venta.services.ClienteService;
import pe.olpesa.venta.services.ProductoService;
import pe.olpesa.venta.utils.EnumEstadoVenta;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {
    
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @GetMapping
    public String listarVentas(Model model,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Authentication authentication) {

        // Obtener usuario autenticado
        UsuarioDetalle userDetails = (UsuarioDetalle) authentication.getPrincipal();

        var ventas = ventaService.listarVentas();
        
        // Variables para el header
        model.addAttribute("pageTitle", "Ventas");
        model.addAttribute("pageSubtitle", "Punto de venta y gestión");

        // Datos de ventas
        model.addAttribute("ventas", ventas);
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("productos", productoService.listarProductos());
        
        // Usuario autenticado (vendedor)
        Long userId = userDetails.getId();
        String userName = userDetails.getName();
        
        model.addAttribute("usuarioAutenticadoId", userId);
        model.addAttribute("usuarioAutenticadoNombre", userName);

        // Estadísticas
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("ventasPendientes", ventaService.contarVentasPorEstado(EnumEstadoVenta.PENDIENTE));
        model.addAttribute("ventasConfirmadas", ventaService.contarVentasPorEstado(EnumEstadoVenta.CONFIRMADA));
        model.addAttribute("ventasCanceladas", ventaService.contarVentasPorEstado(EnumEstadoVenta.CANCELADA));
        model.addAttribute("totalIngresos", ventaService.calcularTotalIngresos());
        model.addAttribute("ingresosDelDia", ventaService.calcularIngresosDelDia());

        // Paginación
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", ventas.size());
        model.addAttribute("totalPages", (int) Math.ceil((double) ventas.size() / size));

        return "ventas/index";
    }
}
