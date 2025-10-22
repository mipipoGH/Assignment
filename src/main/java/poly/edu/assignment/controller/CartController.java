package poly.edu.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.service.GioHangService;
import poly.edu.assignment.service.SanPhamService;
import poly.edu.assignment.web.CartItem;
import poly.edu.assignment.entity.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final GioHangService gioHang;
    private final SanPhamService spService;

    public CartController(GioHangService gioHang, SanPhamService spService) {
        this.gioHang = gioHang;
        this.spService = spService;
    }

    @GetMapping
    public String view(Model model) {
        List<CartItem> list = new ArrayList<>();
        for (Map.Entry<String, Integer> e : gioHang.items().entrySet()) {
            String maSP = e.getKey();
            Integer qty = e.getValue();
            SanPham sp = spService.findById(maSP).orElse(null);
            if (sp != null) {
                list.add(new CartItem(maSP, sp.getTenSP(), sp.getDonGia(), qty));
            }
        }
        model.addAttribute("cart", list);
        model.addAttribute("total", gioHang.total());
        return "home/cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam String maSP, @RequestParam(defaultValue = "1") int qty) {
        gioHang.add(maSP, qty);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam String maSP) {
        gioHang.remove(maSP);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeById(@PathVariable("id") String id) {
        gioHang.remove(id);
        return "redirect:/cart";
    }
}
