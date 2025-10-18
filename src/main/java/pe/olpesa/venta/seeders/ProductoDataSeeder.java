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
        Producto producto1 = productoRepository.findByCodigoBarras("7751271000123");
        if (producto1 == null) {
            producto1 = new Producto();
            producto1.setSku("ACE-001");
            producto1.setCodigoBarras("7751271000123");
            producto1.setNombre("Aceite Vegetal Primor 1L");
            producto1.setDescripcion("Aceite vegetal comestible marca Primor, botella de 1 litro");
            producto1.setPrecioUnitario(12.50);
            producto1.setStockActual(150.0);
            producto1.setAfectoImpuesto(true);
            producto1.setEstado(true);
            productoRepository.save(producto1);
        }
        Producto producto2 = productoRepository.findByCodigoBarras("7754800000234");
        if (producto2 == null) {
            producto2 = new Producto();
            producto2.setSku("ARR-002");
            producto2.setCodigoBarras("7754800000234");
            producto2.setNombre("Arroz Superior Extra 5kg");
            producto2.setDescripcion("Arroz blanco extra superior, saco de 5 kilogramos");
            producto2.setPrecioUnitario(18.90);
            producto2.setStockActual(75.0);
            producto2.setAfectoImpuesto(true);
            producto2.setEstado(true);
            productoRepository.save(producto2);
        }

        Producto producto3 = productoRepository.findByCodigoBarras("7759227000345");
        if (producto3 == null) {
            producto3 = new Producto();
            producto3.setSku("AZU-003");
            producto3.setCodigoBarras("7759227000345");
            producto3.setNombre("Azúcar Rubia Cartavio 1kg");
            producto3.setDescripcion("Azúcar rubia refinada Cartavio, bolsa de 1 kilogramo");
            producto3.setPrecioUnitario(4.20);
            producto3.setStockActual(200.0);
            producto3.setAfectoImpuesto(false);
            producto3.setEstado(true);
            productoRepository.save(producto3);
        }

        Producto producto4 = productoRepository.findByCodigoBarras("7751271001456");
        if (producto4 == null) {
            producto4 = new Producto();
            producto4.setSku("LEA-004");
            producto4.setCodigoBarras("7751271001456");
            producto4.setNombre("Leche Evaporada Ideal 400g");
            producto4.setDescripcion("Leche evaporada entera Ideal, lata de 400 gramos");
            producto4.setPrecioUnitario(3.80);
            producto4.setStockActual(120.0);
            producto4.setAfectoImpuesto(true);
            producto4.setEstado(true);
            productoRepository.save(producto4);
        }

        Producto producto5 = productoRepository.findByCodigoBarras("7751271002567");
        if (producto5 == null) {
            producto5 = new Producto();
            producto5.setSku("FID-005");
            producto5.setCodigoBarras("7751271002567");
            producto5.setNombre("Fideos Espagueti Don Vittorio 500g");
            producto5.setDescripcion("Fideos espagueti marca Don Vittorio, paquete de 500 gramos");
            producto5.setPrecioUnitario(2.90);
            producto5.setStockActual(180.0);
            producto5.setAfectoImpuesto(false);
            producto5.setEstado(true);
            productoRepository.save(producto5);
        }
    }

}
