package poly.edu.assignment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.HoaDonRepository;

import java.util.List;

@Controller
public class DonHangController {

    private final HoaDonRepository hoaDonRepository;

    public DonHangController(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    @GetMapping("/don-hang")
    public String xemDonHang(HttpSession session, Model model) {
        KhachHang kh = (KhachHang) session.getAttribute("user");
        if (kh == null) {
            return "redirect:/login";
        }

        List<HoaDon> danhSachDonHang = hoaDonRepository.findByKhachHangOrderByNgayLapDesc(kh);
        model.addAttribute("danhSachDonHang", danhSachDonHang);

        return "home/don-hang";
    }

    @GetMapping("/don-hang/{maHD}")
    public String chiTietDonHang(@PathVariable String maHD, HttpSession session, Model model) {
        KhachHang kh = (KhachHang) session.getAttribute("user");
        if (kh == null) {
            return "redirect:/login";
        }

        HoaDon hoaDon = hoaDonRepository.findByMaHDAndKhachHang(maHD, kh);
        if (hoaDon == null) {
            model.addAttribute("error", "Không tìm thấy đơn hàng");
            return "redirect:/don-hang";
        }

        model.addAttribute("hoaDon", hoaDon);
        return "home/chi-tiet-don-hang";
    }
}
