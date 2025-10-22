package poly.edu.assignment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.service.GioHangService;
import poly.edu.assignment.service.HoaDonService;

@Controller
public class CheckoutController {
    private final GioHangService gioHang;
    private final KhachHangRepository khRepo;
    private final HoaDonService hdService;

    public CheckoutController(GioHangService gioHang, KhachHangRepository khRepo, HoaDonService hdService) {
        this.gioHang = gioHang;
        this.khRepo = khRepo;
        this.hdService = hdService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("total", gioHang.total());
        return "home/checkout";
    }

    @PostMapping("/order/confirm")
    public String confirm(@RequestParam String email, Model model) {
        // For demo: find KhachHang by email; if not exist, create a quick guest
        KhachHang kh = khRepo.findByEmail(email).orElseGet(() -> {
            KhachHang g = KhachHang.builder()
                    .maKH("GUEST-" + System.currentTimeMillis())
                    .hoTen("Khách vãng lai")
                    .email(email)
                    .matKhau("") // no login
                    .ngayTao(null)
                    .build();
            return khRepo.save(g);
        });

        HoaDon hd = hdService.placeOrder(kh, gioHang.items());
        gioHang.clear();
        model.addAttribute("order", hd);
        return "home/order-success";
    }
}
