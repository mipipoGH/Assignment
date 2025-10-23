package poly.edu.assignment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.service.KhachHangService;

@Controller
public class TaiKhoanController {

    private final KhachHangService khachHangService;

    public TaiKhoanController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping("/tai-khoan")
    public String hienThiThongTin(HttpSession session, Model model) {
        KhachHang kh = (KhachHang) session.getAttribute("user");
        if (kh == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển về login
        }

        model.addAttribute("khachHang", kh);
        return "home/thong-tin"; // ✅ khớp với file src/main/resources/templates/home/thong-tin.html
    }
    @PostMapping("/tai-khoan/update")
    public String capNhatThongTin(@ModelAttribute("khachHang") KhachHang khMoi, HttpSession session) {
        KhachHang khCu = (KhachHang) session.getAttribute("user");
        if (khCu == null) {
            return "redirect:/login";
        }

        // Giữ lại thông tin không được cập nhật từ form
        khMoi.setMaKH(khCu.getMaKH());
        khMoi.setMatKhau(khCu.getMatKhau()); // Giữ lại mật khẩu cũ
        khMoi.setNgayTao(khCu.getNgayTao()); // Giữ lại ngày tạo

        // Cập nhật vào DB
        khachHangService.save(khMoi);

        // Cập nhật lại session
        session.setAttribute("user", khMoi);

        return "redirect:/tai-khoan";
    }


}
