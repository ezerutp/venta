package pe.olpesa.venta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.olpesa.venta.entities.Cliente;
import pe.olpesa.venta.utils.EnumDocumento;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByTipoDocumentoAndNumeroDocumento(EnumDocumento tipoDocumento, String numeroDocumento);
    public List<Cliente> findByNombreRazonContainingIgnoreCase(String nombreRazon);
}
