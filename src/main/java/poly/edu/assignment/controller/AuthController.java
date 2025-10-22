package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.entity.KhachHang;
import org.springframework.security.crypto.password.PasswordEncoder;
import poly.edu.assignment.repository.NhanVienRepository;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class AuthController {

    private final KhachHangRepository khRepo;
    private final NhanVienRepository nvRepo;
    private final PasswordEncoder encoder;

    public AuthController(KhachHangRepository khRepo, NhanVienRepository nvRepo, PasswordEncoder encoder) {
        this.khRepo = khRepo;
        this.nvRepo = nvRepo;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "home/login"; // đường dẫn tới templates/home/login.html
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String matKhau) {
        System.out.println("========== POST /login ĐƯỢC GỌI ==========");
        System.out.println("Email: " + email);
        System.out.println("Password: " + matKhau);
        System.out.println(">>> Đang đăng nhập với email: " + email);

        // 1️⃣ Kiểm tra Khách hàng
        Optional<KhachHang> khOpt = khRepo.findByEmail(email);
        if (khOpt.isPresent()) {
            System.out.println(">>> Tìm thấy khách hàng");
            if (matKhau.equals(khOpt.get().getMatKhau())) {
                return "redirect:/home?role=khachhang";
            }
        }

        System.out.println(">>> Không phải khách hàng, kiểm tra nhân viên...");

        // 2️⃣ Kiểm tra Nhân viên
        Optional<NhanVien> nvOpt = nvRepo.findByEmail(email);
        System.out.println(">>> Tìm nhân viên: " + nvOpt.isPresent()); // ← DÒNG NÀY CÓ CHẠY KHÔNG?

        if (nvOpt.isPresent()) {
            NhanVien nv = nvOpt.get();
            if (matKhau.equals(nv.getMatKhau())) {
                String role = nv.getVaiTro();
                if (role.equalsIgnoreCase("Admin")) {
                    return "redirect:/admin/dashboard";
                }
                // ... các role khác
            }
        }

        return "redirect:/login?error=invalid";
    }

    @GetMapping("/register")
    public String register() {
        return "home/register"; // file templates/home/register.html
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String hoTen,
                             @RequestParam String email,
                             @RequestParam(required = false) String sdt,
                             @RequestParam String matKhau) {
        // if email already exists, simply redirect back to register (could add error handling)
        if (khRepo.findByEmail(email).isPresent()) {
            return "redirect:/register?error=exists";
        }

        String maKH = "KH" + System.currentTimeMillis();
        KhachHang kh = new KhachHang();
        kh.setMaKH(maKH);
        kh.setHoTen(hoTen);
        kh.setEmail(email);
        kh.setSdt(sdt);
        kh.setMatKhau(encoder.encode(matKhau));
        kh.setNgayTao(LocalDate.now());
        khRepo.save(kh);

        return "redirect:/login?registered";
    }
}
