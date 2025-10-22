package poly.edu.assignment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;

@Controller
public class LoginController {

    private final KhachHangRepository khRepo;

    public LoginController(KhachHangRepository khRepo) {
        this.khRepo = khRepo;
    }

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication, HttpSession session) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        KhachHang kh = khRepo.findByEmail(userDetails.getUsername()).orElse(null);

        if (kh != null) {
            session.setAttribute("user", kh);
        }

        return "redirect:/"; // quay về trang chủ
    }
}
