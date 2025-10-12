package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public String index(Model model) {
        // Agregar variables para el dashboard
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "Resumen general del sistema");
        return "home";
    }

    @GetMapping("home")
    public String home(Model model) {
        // Agregar variables para el dashboard
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "Resumen general del sistema");
        return "home";
    }
}
