package pe.olpesa.venta.controllers.Tickets;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.config.UsuarioDetalle;
import pe.olpesa.venta.dto.IncidenciaDto;
import pe.olpesa.venta.entities.Incidencia;
import pe.olpesa.venta.services.IncidenciaService;
import pe.olpesa.venta.utils.EnumEstadoIncidencia;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketsRestController {

    private final IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<Incidencia>> listarTodos() {
        return ResponseEntity.ok(incidenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtenerPorId(@PathVariable Long id) {
        Incidencia incidencia = incidenciaService.obtenerPorId(id);
        if (incidencia != null) {
            return ResponseEntity.ok(incidencia);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/abiertos")
    public ResponseEntity<List<Incidencia>> listarAbiertos() {
        return ResponseEntity.ok(incidenciaService.listarAbiertas());
    }

    @PostMapping
    public ResponseEntity<?> crearTicket(
            @RequestBody IncidenciaDto dto,
            @AuthenticationPrincipal UsuarioDetalle usuarioDetalle) {
        try {
            Incidencia incidencia = incidenciaService.crearIncidencia(dto, usuarioDetalle.getUsuario());
            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @PathVariable String estado) {
        try {
            EnumEstadoIncidencia nuevoEstado = EnumEstadoIncidencia.valueOf(estado);
            Incidencia incidencia = incidenciaService.actualizarEstado(id, nuevoEstado);
            if (incidencia != null) {
                return ResponseEntity.ok(incidencia);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido");
        }
    }
}
