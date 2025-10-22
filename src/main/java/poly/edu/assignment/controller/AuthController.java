package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.entity.KhachHang;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Controller
public class AuthController {

    private final KhachHangRepository khRepo;
    private final PasswordEncoder encoder;

    public AuthController(KhachHangRepository khRepo, PasswordEncoder encoder) {
        this.khRepo = khRepo;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "home/login"; // đường dẫn tới templates/home/login.html
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
