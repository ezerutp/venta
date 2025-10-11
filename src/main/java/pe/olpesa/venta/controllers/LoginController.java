package pe.olpesa.venta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Sesión cerrada exitosamente");
        }
        return "login";
    }

    @PostMapping("/error")
    public String loginError(Model model) {
        model.addAttribute("errorMessage", "Error en la autenticación. Verifique sus credenciales.");
        return "login";
    }
}