package pe.olpesa.venta.controllers.Tickets;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.config.UsuarioDetalle;
import pe.olpesa.venta.entities.Incidencia;
import pe.olpesa.venta.services.ClienteService;
import pe.olpesa.venta.services.IncidenciaService;
import pe.olpesa.venta.services.ProductoService;
import pe.olpesa.venta.services.UsuarioService;
import pe.olpesa.venta.services.VentaService;
import pe.olpesa.venta.utils.EnumEstadoIncidencia;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketsController {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final VentaService ventaService;
    private final IncidenciaService incidenciaService;

    @GetMapping("crear")
    public String crearTicket(Model model, @AuthenticationPrincipal UsuarioDetalle usuarioDetalle) {
        var usuario = usuarioDetalle.getUsuario();
        boolean esAdmin = usuario.getId() == 1L;
        
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("productos", productoService.obtenerProductosActivos());
        model.addAttribute("usuarios", usuarioService.obtenerUsuariosActivos());
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("esAdmin", esAdmin);
        model.addAttribute("usuarioActual", usuario);
        return "tickets/crear";
    }

    @GetMapping("abiertos")
    public String ticketsAbiertos(Model model, @AuthenticationPrincipal UsuarioDetalle usuarioDetalle) {
        var usuario = usuarioDetalle.getUsuario();
        List<Incidencia> ticketsAsignados;

        if (usuario.getId() == 1L) {
            ticketsAsignados = incidenciaService.listarTodas();
        } else {
            ticketsAsignados = incidenciaService.listarTodas().stream()
                .filter(incidencia -> incidencia.getAsignadoA() != null && incidencia.getAsignadoA().getId().equals(usuario.getId()))
                .toList();
        }

        int totalIncidencias = incidenciaService.listarTodas().size();
        int incidenciasAbiertas = (int) incidenciaService.listarTodas().stream()
            .filter(incidencia -> incidencia.getEstado() == EnumEstadoIncidencia.ABIERTO)
            .count();

        int incidenciasResueltas = (int) incidenciaService.listarTodas().stream()
            .filter(incidencia -> incidencia.getEstado() == EnumEstadoIncidencia.RESUELTO)
            .count();

        int incidenciasCanceladas = (int) incidenciaService.listarTodas().stream()
            .filter(incidencia -> incidencia.getEstado() == EnumEstadoIncidencia.CANCELADO)
            .count();

        int incidenciasCriticas = (int) incidenciaService.listarTodas().stream()
            .filter(incidencia -> incidencia.getPrioridad() == pe.olpesa.venta.utils.EnumPrioridad.URGENTE)
            .count();

        int incidenciasAltas = (int) incidenciaService.listarTodas().stream()   
            .filter(incidencia -> incidencia.getPrioridad() == pe.olpesa.venta.utils.EnumPrioridad.ALTA)
            .count();

        int incidenciasMedias = (int) incidenciaService.listarTodas().stream()
                .filter(incidencia -> incidencia.getPrioridad() == pe.olpesa.venta.utils.EnumPrioridad.MEDIA)
                .count();
        
        int incidenciasBajas = (int) incidenciaService.listarTodas().stream()
            .filter(incidencia -> incidencia.getPrioridad() == pe.olpesa.venta.utils.EnumPrioridad.BAJA)
            .count();
        
        model.addAttribute("totalIncidencias", totalIncidencias);
        model.addAttribute("incidenciasAbiertas", incidenciasAbiertas);
        model.addAttribute("incidenciasResueltas", incidenciasResueltas);
        model.addAttribute("incidenciasCanceladas", incidenciasCanceladas);
        model.addAttribute("incidenciasCriticas", incidenciasCriticas);
        model.addAttribute("incidenciasAltas", incidenciasAltas);
        model.addAttribute("incidenciasMedias", incidenciasMedias);
        model.addAttribute("incidenciasBajas", incidenciasBajas);
        model.addAttribute("ticketsAsignados", ticketsAsignados);
        
        return "tickets/abiertos";
    }

    @GetMapping("gestion")
    public String gestionTickets() {
        return "tickets/gestion";
    }
}
