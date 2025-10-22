package poly.edu.assignment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.assignment.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SanPhamService spService;
    private final LoaiSPService loaiSPService;
    private final KhachHangService khachHangService;

    public AdminController(SanPhamService spService, LoaiSPService loaiSPService, KhachHangService khachHangService) {
        this.spService = spService;
        this.loaiSPService = loaiSPService;
        this.khachHangService = khachHangService;
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("products", spService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", spService.findAll());
        return "admin/products";
    }
    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", loaiSPService.findAll());
        return "admin/category";
    }
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", khachHangService.findAll());
        return "admin/category";
    }
}
