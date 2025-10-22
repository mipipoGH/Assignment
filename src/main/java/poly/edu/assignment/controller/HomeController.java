package poly.edu.assignment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.assignment.service.SanPhamService;

@Controller
public class HomeController {
    private final SanPhamService spService;

    public HomeController(SanPhamService spService) {
        this.spService = spService;
    }

    @GetMapping({"/","/home","/index"})
    public String index(Model model) {
        model.addAttribute("products", spService.findAll());
        return "home/index";
    }
}
