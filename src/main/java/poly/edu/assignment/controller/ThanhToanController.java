package poly.edu.assignment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.entity.SanPham;
import poly.edu.assignment.service.HoaDonService;
import poly.edu.assignment.service.GioHangService;
import poly.edu.assignment.service.SanPhamService;
import poly.edu.assignment.web.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ThanhToanController {

    private final HoaDonService hoaDonService;
    private final GioHangService gioHangService;
    private final SanPhamService sanPhamService;

    public ThanhToanController(HoaDonService hoaDonService,
                               GioHangService gioHangService,
                               SanPhamService sanPhamService) {
        this.hoaDonService = hoaDonService;
        this.gioHangService = gioHangService;
        this.sanPhamService = sanPhamService;
    }

    // ✅ Hiển thị trang thanh toán
    @GetMapping("/checkout")
    public String hienThiTrangThanhToan(HttpSession session, Model model) {
        // Kiểm tra đăng nhập
        KhachHang kh = (KhachHang) session.getAttribute("user");
        if (kh == null) {
            return "redirect:/login";
        }

        // Lấy giỏ hàng từ service
        Map<String, Integer> cartItems = gioHangService.items();
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        // Chuyển đổi sang CartItem để hiển thị
        List<CartItem> cart = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String maSP = entry.getKey();
            Integer qty = entry.getValue();
            SanPham sp = sanPhamService.findById(maSP).orElse(null);
            if (sp != null) {
                cart.add(new CartItem(maSP, sp.getTenSP(), sp.getDonGia(), qty));
            }
        }

        model.addAttribute("cart", cart);
        model.addAttribute("total", gioHangService.total());

        return "home/checkout";
    }

    // ✅ Xử lý thanh toán
    @PostMapping("/checkout")
    public String xuLyThanhToan(HttpSession session, Model model) {
        KhachHang kh = (KhachHang) session.getAttribute("user");
        if (kh == null) {
            return "redirect:/login";
        }

        // Lấy giỏ hàng từ service
        Map<String, Integer> cartMap = gioHangService.items();
        if (cartMap.isEmpty()) {
            return "redirect:/cart";
        }

        try {
            HoaDon hd = hoaDonService.placeOrder(kh, cartMap);

            // ✅ Xóa giỏ hàng sau khi thanh toán
            gioHangService.clear();
// ✅ Lưu hóa đơn vào session để hiển thị ở trang thankyou
            session.setAttribute("hoaDon", hd);

            return "redirect:/thankyou";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

            // Tạo lại cart để hiển thị
            List<CartItem> cart = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                String maSP = entry.getKey();
                Integer qty = entry.getValue();
                SanPham sp = sanPhamService.findById(maSP).orElse(null);
                if (sp != null) {
                    cart.add(new CartItem(maSP, sp.getTenSP(), sp.getDonGia(), qty));
                }
            }

            model.addAttribute("cart", cart);
            model.addAttribute("total", gioHangService.total());

            return "home/checkout";
        }
    }

    // ✅ Trang cảm ơn
    @GetMapping("/thankyou")
    public String hienThiTrangCamOn(HttpSession session, Model model) {
        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDon");
        if (hoaDon != null) {
            model.addAttribute("hoaDon", hoaDon);
            // Xóa khỏi session sau khi hiển thị
            session.removeAttribute("hoaDon");
        }
        return "home/thankyou";
    }
}
