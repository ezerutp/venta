package pe.olpesa.venta.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.entities.Producto;
import pe.olpesa.venta.repositories.ProductoRepository;

@Component
@RequiredArgsConstructor
public class ProductoDataSeeder implements CommandLineRunner{
    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        seedProductos();
    }

    private void seedProductos() {
        System.out.println("Sembrando datos iniciales de productos...");
        Producto producto1 = productoRepository.findByCodigoBarras("1234567890123");
        if (producto1 == null) {
            producto1 = new Producto();
            producto1.setSku("PROD-001");
            producto1.setCodigoBarras("1234567890123");
            producto1.setNombre("Producto de Ejemplo 1");
            producto1.setDescripcion("Descripción del Producto de Ejemplo 1");
            producto1.setPrecioUnitario(19.99);
            producto1.setStockActual(100.0);
            producto1.setAfectoImpuesto(true);
            producto1.setEstado(true);
            productoRepository.save(producto1);
        }
        Producto producto2 = productoRepository.findByCodigoBarras("2345678901234");
        if (producto2 == null) {
            producto2 = new Producto();
            producto2.setSku("PROD-002");
            producto2.setCodigoBarras("2345678901234");
            producto2.setNombre("Producto de Ejemplo 2");
            producto2.setDescripcion("Descripción del Producto de Ejemplo 2");
            producto2.setPrecioUnitario(29.99);
            producto2.setStockActual(50.0);
            producto2.setAfectoImpuesto(true);
            producto2.setEstado(true);
            productoRepository.save(producto2);
        }

        Producto producto3 = productoRepository.findByCodigoBarras("3456789012345");
        if (producto3 == null) {
            producto3 = new Producto();
            producto3.setSku("PROD-003");
            producto3.setCodigoBarras("3456789012345");
            producto3.setNombre("Producto de Ejemplo 3");
            producto3.setDescripcion("Descripción del Producto de Ejemplo 3");
            producto3.setPrecioUnitario(9.99);
            producto3.setStockActual(200.0);
            producto3.setAfectoImpuesto(false);
            producto3.setEstado(true);
            productoRepository.save(producto3);
        }

        Producto producto4 = productoRepository.findByCodigoBarras("4567890123456");
        if (producto4 == null) {
            producto4 = new Producto();
            producto4.setSku("PROD-004");
            producto4.setCodigoBarras("4567890123456");
            producto4.setNombre("Producto de Ejemplo 4");
            producto4.setDescripcion("Descripción del Producto de Ejemplo 4");
            producto4.setPrecioUnitario(49.99);
            producto4.setStockActual(30.0);
            producto4.setAfectoImpuesto(true);
            producto4.setEstado(true);
            productoRepository.save(producto4);
        }

        Producto producto5 = productoRepository.findByCodigoBarras("5678901234567");
        if (producto5 == null) {
            producto5 = new Producto();
            producto5.setSku("PROD-005");
            producto5.setCodigoBarras("5678901234567");
            producto5.setNombre("Producto de Ejemplo 5");
            producto5.setDescripcion("Descripción del Producto de Ejemplo 5");
            producto5.setPrecioUnitario(15.50);
            producto5.setStockActual(80.0);
            producto5.setAfectoImpuesto(false);
            producto5.setEstado(true);
            productoRepository.save(producto5);
        }
    }

}
