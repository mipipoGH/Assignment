package poly.edu.assignment.controller;

import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public List<NhanVien> getAll() {
        return nhanVienService.findAll();
    }

    @PostMapping
    public NhanVien create(@RequestBody NhanVien nv) {
        return nhanVienService.save(nv);
    }

    @PutMapping("/{id}")
    public NhanVien update(@PathVariable String id, @RequestBody NhanVien nv) {
        nv.setMaNV(id);
        return nhanVienService.save(nv);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        nhanVienService.deleteById(id);
    }
}
