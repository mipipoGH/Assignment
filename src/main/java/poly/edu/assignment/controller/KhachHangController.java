package poly.edu.assignment.controller;

import org.springframework.web.bind.annotation.*;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.service.KhachHangService;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {


    @GetMapping
    public List<KhachHang> getAll() {
    }

    @PostMapping
    public KhachHang create(@RequestBody KhachHang kh) {
    }

    @PutMapping("/{id}")
    public KhachHang update(@PathVariable String id, @RequestBody KhachHang kh) {
        kh.setMaKH(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
    }
}
