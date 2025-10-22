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

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", spService.findAll());
        return "admin/product";
    }
    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", loaiSPService.findAll());
        return "admin/category";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", hoaDonAdminService.findAll());
        return "admin/orders";
    }

    @GetMapping("/orders/detail/{id}")
    public String orderDetail(@PathVariable("id") String maHD, Model model) {
        var hoaDon = hoaDonAdminService.findById(maHD).orElse(null);
        if (hoaDon == null) {
            return "redirect:/admin/orders";
        }
        model.addAttribute("order", hoaDon);
        model.addAttribute("details", hoaDon.getChiTiet());
        return "admin/order-detail";
    }

    // 🌀 Cập nhật trạng thái đơn hàng (Admin đổi trạng thái)
    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam String maHD, @RequestParam String trangThai) {
        hoaDonAdminService.updateStatus(maHD, trangThai);
        return "redirect:/admin/orders/detail/" + maHD;
    }

}
