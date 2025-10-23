package poly.edu.assignment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogOutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa session user
        session.invalidate();

        // Chuyển về trang chủ
        return "redirect:/";
    }

}