package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.service.KhachHangService;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    private final KhachHangService khachHangService;

    public UsersController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("title", "Quản lý Khách hàng");
        model.addAttribute("users", khachHangService.findAll());
        return "admin/users";  // Trả về file template đầy đủ
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm khách hàng");
        model.addAttribute("user", new KhachHang());
        return "admin/user-form";  // Trả về form đầy đủ
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") KhachHang user) {
        khachHangService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{maKH}")
    public String editForm(@PathVariable String maKH, Model model) {
        KhachHang user = khachHangService.findById(maKH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        model.addAttribute("title", "Sửa khách hàng");
        model.addAttribute("user", user);
        return "admin/user-form";  // Trả về form đầy đủ
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") KhachHang user) {
        KhachHang existingUser = khachHangService.findById(user.getMaKH())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        // Nếu mật khẩu từ form là null hoặc rỗng thì giữ lại mật khẩu cũ
        if (user.getMatKhau() == null || user.getMatKhau().isEmpty()) {
            user.setMatKhau(existingUser.getMatKhau());
        }
        // Hoặc có thể thêm logic mã hóa mật khẩu nếu có

        khachHangService.save(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/delete/{maKH}")
    public String deleteUser(@PathVariable String maKH) {
        khachHangService.delete(maKH);
        return "redirect:/admin/users";
    }
}
