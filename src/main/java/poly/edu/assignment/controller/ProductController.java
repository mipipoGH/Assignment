package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.service.SanPhamService;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final SanPhamService spService;

    public ProductController(SanPhamService spService) {
        this.spService = spService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", spService.findAll());
        return "home/product";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        spService.findById(id).ifPresent(p -> model.addAttribute("product", p));
        return "home/product-detail";
    }
}

