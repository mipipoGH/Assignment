package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.SanPham;
import poly.edu.assignment.repository.LoaiSPRepository;
import poly.edu.assignment.service.LoaiSPService;
import poly.edu.assignment.service.SanPhamService;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final SanPhamService sanPhamService;
    private final LoaiSPService loaiSPService;

    public ProductAdminController(SanPhamService sanPhamService, LoaiSPService loaiSPService) {
        this.sanPhamService = sanPhamService;
        this.loaiSPService = loaiSPService;
    }

    // Danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("title", "Quản lý sản phẩm");
        model.addAttribute("products", sanPhamService.findAll());
        return "admin/product"; // Trả về thẳng view admin/product.html
    }

    // Form thêm sản phẩm mới
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm sản phẩm");
        model.addAttribute("product", new SanPham());
        model.addAttribute("loaiSanPhams", loaiSPService.findAll()); // nhớ gọi service loại sản phẩm
        return "admin/product-form";
    }


    // Xử lý thêm sản phẩm
    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") SanPham product) {
        sanPhamService.save(product);
        return "redirect:/admin/products";
    }

    // Form sửa sản phẩm
    @GetMapping("/edit/{maSP}")
    public String editForm(@PathVariable String maSP, Model model) {
        SanPham product = sanPhamService.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("title", "Sửa sản phẩm");
        model.addAttribute("product", product);
        model.addAttribute("loaiSanPhams", loaiSPService.findAll());
        return "admin/product-form";
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") SanPham product) {
        sanPhamService.save(product);
        return "redirect:/admin/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{maSP}")
    public String deleteProduct(@PathVariable String maSP) {
        sanPhamService.delete(maSP);
        return "redirect:/admin/products";
    }
}
