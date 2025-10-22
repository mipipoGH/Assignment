package poly.edu.assignment.controller;

import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.service.KhachHangService;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
@CrossOrigin("*") // Cho phép gọi API từ frontend khác domain (VD: localhost:3000)
public class KhachHangController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    /**
     * Lấy danh sách tất cả khách hàng
     */
    @GetMapping
    public List<KhachHang> getAll() {
        return khachHangService.findAll();
    }

    /**
     * Lấy thông tin khách hàng theo mã
     */
    @GetMapping("/{id}")
    public KhachHang getById(@PathVariable String id) {
        return khachHangService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng có mã: " + id));
    }

    /**
     * Tạo mới khách hàng
     */
    @PostMapping
    public KhachHang create(@RequestBody KhachHang kh) {
        return khachHangService.save(kh);
    }

    /**
     * Cập nhật thông tin khách hàng
     */
    @PutMapping("/{id}")
    public KhachHang update(@PathVariable String id, @RequestBody KhachHang kh) {
        kh.setMaKH(id);
        return khachHangService.update(kh);
    }

    /**
     * Xóa khách hàng theo mã
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        khachHangService.delete(id);
    }
}
