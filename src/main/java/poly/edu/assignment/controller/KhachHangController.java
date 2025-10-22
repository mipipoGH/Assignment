package poly.edu.assignment.controller;

import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService service;

    @GetMapping
    public List<KhachHang> getAll() {
        return service.findAll();
    }

    @PostMapping
    public KhachHang create(@RequestBody KhachHang kh) {
        return service.save(kh);
    }

    @PutMapping("/{id}")
    public KhachHang update(@PathVariable String id, @RequestBody KhachHang kh) {
        kh.setMaKH(id);
        return service.save(kh);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
