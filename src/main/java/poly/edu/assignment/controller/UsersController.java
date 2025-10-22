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
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm khách hàng");
        model.addAttribute("user", new KhachHang());
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
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") KhachHang user) {
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{maKH}")
    public String deleteUser(@PathVariable String maKH) {
        khachHangService.delete(maKH);
        return "redirect:/admin/users";
    }
}
