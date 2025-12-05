package pe.olpesa.venta.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.dto.IncidenciaDto;
import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.entities.Incidencia;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.entities.Usuario;
import pe.olpesa.venta.entities.Venta;
import pe.olpesa.venta.repositories.ClienteRepository;
import pe.olpesa.venta.repositories.IncidenciaRepository;
import pe.olpesa.venta.repositories.ProductoRepository;
import pe.olpesa.venta.repositories.UsuarioRepository;
import pe.olpesa.venta.repositories.VentaRepository;
import pe.olpesa.venta.utils.EnumCanalRecepcion;
import pe.olpesa.venta.utils.EnumEstadoIncidencia;
import pe.olpesa.venta.utils.EnumImpacto;
import pe.olpesa.venta.utils.EnumPrioridad;
import pe.olpesa.venta.utils.EnumTipoIncidencia;

@Service
@RequiredArgsConstructor
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final ClienteRepository clienteRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    public Incidencia obtenerPorId(Long id) {
        return incidenciaRepository.findById(id).orElse(null);
    }

    public List<Incidencia> listarAbiertas() {
        return incidenciaRepository.findByEstadoIn(List.of(
            EnumEstadoIncidencia.ABIERTO,
            EnumEstadoIncidencia.EN_PROCESO,
            EnumEstadoIncidencia.PENDIENTE
        ));
    }

    public List<Incidencia> listarPorEstado(EnumEstadoIncidencia estado) {
        return incidenciaRepository.findByEstado(estado);
    }

    public long contarPorEstado(EnumEstadoIncidencia estado) {
        return incidenciaRepository.countByEstado(estado);
    }

    public List<Incidencia> listarAbiertasPorUsuario(Usuario usuario) {
        List<EnumEstadoIncidencia> estadosAbiertos = List.of(
            EnumEstadoIncidencia.ABIERTO,
            EnumEstadoIncidencia.EN_PROCESO,
            EnumEstadoIncidencia.PENDIENTE
        );
        return incidenciaRepository.findByAsignadoAAndEstadoInWithRelations(usuario, estadosAbiertos);
    }

    public List<Incidencia> listarCreadasPorUsuario(Usuario usuario) {
        List<EnumEstadoIncidencia> estadosAbiertos = List.of(
            EnumEstadoIncidencia.ABIERTO,
            EnumEstadoIncidencia.EN_PROCESO,
            EnumEstadoIncidencia.PENDIENTE
        );
        return incidenciaRepository.findByCreadoPorAndEstadoInWithRelations(usuario, estadosAbiertos);
    }

    public long contarAbiertasPorUsuario(Usuario usuario) {
        List<EnumEstadoIncidencia> estadosAbiertos = List.of(
            EnumEstadoIncidencia.ABIERTO,
            EnumEstadoIncidencia.EN_PROCESO,
            EnumEstadoIncidencia.PENDIENTE
        );
        return incidenciaRepository.countByAsignadoAAndEstadoIn(usuario, estadosAbiertos);
    }

    public Incidencia crearIncidencia(IncidenciaDto dto, Usuario creadoPor) {
        // Buscar cliente (obligatorio)
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Buscar venta (opcional)
        Venta venta = null;
        if (dto.getVentaId() != null) {
            venta = ventaRepository.findById(dto.getVentaId()).orElse(null);
        }

        // Buscar producto (opcional)
        Producto producto = null;
        if (dto.getProductoId() != null) {
            producto = productoRepository.findById(dto.getProductoId()).orElse(null);
        }

        // Buscar usuario asignado (opcional)
        Usuario asignadoA = null;
        if (dto.getAsignadoA() != null) {
            asignadoA = usuarioRepository.findById(dto.getAsignadoA()).orElse(null);
        }

        // Construir la incidencia
        Incidencia incidencia = Incidencia.builder()
            .titulo(dto.getTitulo())
            .tipoIncidencia(EnumTipoIncidencia.valueOf(dto.getTipoIncidencia()))
            .prioridad(EnumPrioridad.valueOf(dto.getPrioridad()))
            .cliente(cliente)
            .venta(venta)
            .producto(producto)
            .numeroDocumento(dto.getNumeroDocumento())
            .motivo(dto.getMotivo())
            .impacto(dto.getImpacto() != null && !dto.getImpacto().isEmpty() 
                ? EnumImpacto.valueOf(dto.getImpacto()) : null)
            .accionesTomadas(dto.getAccionesTomadas())
            .nombreContacto(dto.getNombreContacto())
            .telefonoContacto(dto.getTelefonoContacto())
            .emailContacto(dto.getEmailContacto())
            .fechaIncidente(dto.getFechaIncidente())
            .canalRecepcion(dto.getCanalRecepcion() != null && !dto.getCanalRecepcion().isEmpty() 
                ? EnumCanalRecepcion.valueOf(dto.getCanalRecepcion()) : null)
            .montoInvolucrado(dto.getMontoInvolucrado())
            .asignadoA(asignadoA)
            .notasAdicionales(dto.getNotasAdicionales())
            .estado(EnumEstadoIncidencia.ABIERTO)
            .creadoPor(creadoPor)
            .build();

        return incidenciaRepository.save(incidencia);
    }

    public Incidencia actualizarEstado(Long id, EnumEstadoIncidencia nuevoEstado) {
        Incidencia incidencia = obtenerPorId(id);
        if (incidencia != null) {
            incidencia.setEstado(nuevoEstado);
            return incidenciaRepository.save(incidencia);
        }
        return null;
    }

    public Incidencia guardar(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }
}
