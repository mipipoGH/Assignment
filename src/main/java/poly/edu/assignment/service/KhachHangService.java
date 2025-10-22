package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KhachHangService {

    private final KhachHangRepository repo;

    public KhachHangService(KhachHangRepository repo) {
        this.repo = repo;
    }

    public List<KhachHang> findAll() {
        return repo.findAll();
    }

    public Optional<KhachHang> findById(String id) {
        return repo.findById(id);
    }

    public KhachHang save(KhachHang khachHang) {
        if (khachHang.getNgayTao() == null) {
            khachHang.setNgayTao(LocalDate.now());
        }
        return repo.save(khachHang);
    }
    public KhachHang update(KhachHang khachHang) {
        Optional<KhachHang> existing = repo.findById(khachHang.getMaKH());
        if (existing.isPresent()) {
            KhachHang kh = existing.get();
            kh.setHoTen(khachHang.getHoTen());
            kh.setEmail(khachHang.getEmail());
            kh.setSdt(khachHang.getSdt());
            // Không cập nhật ngay ngàyTao, giữ nguyên
            return repo.save(kh);
        } else {
            throw new RuntimeException("Khách hàng không tồn tại: " + khachHang.getMaKH());
        }
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Optional<KhachHang> findByHoTen(String hoTen) {
        return repo.findByHoTen(hoTen);
    }
}
