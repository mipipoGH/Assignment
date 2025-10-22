package poly.edu.assignment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.service.HoaDonService;
import poly.edu.assignment.service.KhachHangService;
import poly.edu.assignment.service.NhanVienService;

@Controller
@RequestMapping("/admin/orders")
public class OrdersController {

    private final HoaDonService hoaDonService;
    private final KhachHangService khachHangService;
    private final NhanVienService nhanVienService;
    public OrdersController(HoaDonService hoaDonService, KhachHangService khachHangService, NhanVienService nhanVienService) {
        this.hoaDonService = hoaDonService;
        this.khachHangService = khachHangService;
        this.nhanVienService = nhanVienService;
    }
    @PostMapping("/create")
    public String createOrder(@ModelAttribute("order") HoaDon order) {
        if (order.getKhachHang() != null && order.getKhachHang().getMaKH() != null) {
            KhachHang kh = khachHangService.findById(order.getKhachHang().getMaKH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            order.setKhachHang(kh);
        }

        if (order.getNhanVien() != null && order.getNhanVien().getMaNV() != null) {
            NhanVien nv = nhanVienService.findById(order.getNhanVien().getMaNV())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
            order.setNhanVien(nv);
        }

        hoaDonService.save(order);
        return "redirect:/admin/orders";
    }


    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("title", "Quản lý Hóa đơn");
        model.addAttribute("orders", hoaDonService.findAll());
        // Trước: model.addAttribute("content", "admin/order :: content");
        // Trả về thẳng view admin/orders.html
        return "admin/orders";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm hóa đơn");
        model.addAttribute("order", new HoaDon());
        model.addAttribute("khachHangs", khachHangService.findAll());
        model.addAttribute("nhanViens", nhanVienService.findAll()); // <-- thêm dòng này
        return "admin/order-form";
    }


    @GetMapping("/edit/{maHD}")
    public String editForm(@PathVariable String maHD, Model model) {
        HoaDon order = hoaDonService.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        model.addAttribute("title", "Sửa hóa đơn");
        model.addAttribute("order", order);
        return "admin/order-form";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") HoaDon order) {
        hoaDonService.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{maHD}")
    public String deleteOrder(@PathVariable String maHD) {
        hoaDonService.delete(maHD);
        return "redirect:/admin/orders";
    }
}


