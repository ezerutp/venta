package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pe.olpesa.venta.services.ProductoService;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {
        var productos = productoService.listarProductos();

        // Variables para el header
        model.addAttribute("pageTitle", "Productos");
        model.addAttribute("pageSubtitle", "Gestión de productos");

        // Datos de productos
        model.addAttribute("productos", productos);

        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("productosActivos", productoService.contarProductosActivos());
        model.addAttribute("productosInactivos", productoService.contarProductosInactivos());
        model.addAttribute("productosStockBajo", productoService.contarProductosStockBajo());

        // Paginación ... luego implementar
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", productos.size());
        model.addAttribute("totalPages", (int) Math.ceil((double) productos.size() / size));

        return "productos/index";
    }
    
}
