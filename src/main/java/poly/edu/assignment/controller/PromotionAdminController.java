package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.KhuyenMai;
import poly.edu.assignment.service.KhuyenMaiService;

@Controller
@RequestMapping("/admin/promotions")
public class PromotionAdminController {

    private final KhuyenMaiService khuyenMaiService;

    public PromotionAdminController(KhuyenMaiService khuyenMaiService) {
        this.khuyenMaiService = khuyenMaiService;
    }

    // Danh sách khuyến mãi
    @GetMapping
    public String listPromotions(Model model) {
        model.addAttribute("title", "Quản lý khuyến mãi");
        model.addAttribute("promotions", khuyenMaiService.findAll());
        return "admin/promotion";
    }

    // Form thêm khuyến mãi
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm khuyến mãi");
        model.addAttribute("promotion", new KhuyenMai());
        return "admin/promotion-form";
    }

    // Xử lý thêm
    @PostMapping("/create")
    public String createPromotion(@ModelAttribute("promotion") KhuyenMai promotion) {
        khuyenMaiService.save(promotion);
        return "redirect:/admin/promotions";
    }

    // Form sửa
    @GetMapping("/edit/{maKM}")
    public String editForm(@PathVariable String maKM, Model model) {
        KhuyenMai km = khuyenMaiService.findById(maKM)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));
        model.addAttribute("title", "Sửa khuyến mãi");
        model.addAttribute("promotion", km);
        return "admin/promotion-form";
    }

    // Cập nhật
    @PostMapping("/update")
    public String updatePromotion(@ModelAttribute("promotion") KhuyenMai promotion) {
        khuyenMaiService.save(promotion);
        return "redirect:/admin/promotions";
    }

    // Xóa
    @GetMapping("/delete/{maKM}")
    public String deletePromotion(@PathVariable String maKM) {
        khuyenMaiService.delete(maKM);
        return "redirect:/admin/promotions";
    }
}
