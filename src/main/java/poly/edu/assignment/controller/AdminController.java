package poly.edu.assignment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SanPhamService spService;
    private final LoaiSPService loaiSPService;
    private final KhachHangService khachHangService;
    private final HoaDonAdminService hoaDonAdminService;

    public AdminController(SanPhamService spService, LoaiSPService loaiSPService, KhachHangService khachHangService, HoaDonAdminService hoaDonAdminService) {
        this.spService = spService;
        this.loaiSPService = loaiSPService;
        this.khachHangService = khachHangService;
        this.hoaDonAdminService = hoaDonAdminService;
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("products", spService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", loaiSPService.findAll());
        return "admin/category";
    }



}
