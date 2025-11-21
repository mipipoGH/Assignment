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


    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("title", "Thêm hóa đơn");
        model.addAttribute("order", new HoaDon());
        model.addAttribute("khachHangs", khachHangService.findAll());
        model.addAttribute("nhanViens", nhanVienService.findAll()); // <-- thêm dòng này
        return "admin/order-form";
    }



    @PostMapping("/change-status")
    public String updateOrderStatus(@RequestParam String maHD, @RequestParam String trangThai,
                                    org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            HoaDon order = hoaDonService.findById(maHD)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

            String oldStatus = order.getTrangThai();
            order.setTrangThai(trangThai);
            hoaDonService.save(order);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Đã cập nhật trạng thái đơn hàng " + maHD + " từ '" + oldStatus + "' thành '" + trangThai + "'");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Có lỗi xảy ra khi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/orders";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") HoaDon order) {
        // Lấy hóa đơn hiện tại từ database
        HoaDon existingOrder = hoaDonService.findById(order.getMaHD())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // Cập nhật các thông tin có thể thay đổi
        existingOrder.setTrangThai(order.getTrangThai());
        existingOrder.setTongTien(order.getTongTien());
        existingOrder.setNgayLap(order.getNgayLap());

        // Cập nhật khách hàng nếu có thay đổi
        if (order.getKhachHang() != null && order.getKhachHang().getMaKH() != null) {
            KhachHang kh = khachHangService.findById(order.getKhachHang().getMaKH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            existingOrder.setKhachHang(kh);
        }

        // Cập nhật nhân viên nếu có thay đổi
        if (order.getNhanVien() != null && order.getNhanVien().getMaNV() != null) {
            NhanVien nv = nhanVienService.findById(order.getNhanVien().getMaNV())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
            existingOrder.setNhanVien(nv);
        }

        hoaDonService.save(existingOrder);
        return "redirect:/admin/orders";
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("title", "Quản lý Hóa đơn");
        model.addAttribute("orders", hoaDonService.findAll());
        model.addAttribute("statusStats", hoaDonService.getOrderStatusStatistics());
        return "admin/orders";
    }

    @GetMapping("/edit/{maHD}")
    public String editForm(@PathVariable String maHD, Model model) {
        HoaDon order = hoaDonService.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        model.addAttribute("title", "Sửa hóa đơn");
        model.addAttribute("order", order);
        model.addAttribute("khachHangs", khachHangService.findAll());
        model.addAttribute("nhanViens", nhanVienService.findAll());
        return "admin/order-form";
    }

    @GetMapping("/delete/{maHD}")
    public String deleteOrder(@PathVariable String maHD) {
        hoaDonService.delete(maHD);
        return "redirect:/admin/orders";
    }
}


