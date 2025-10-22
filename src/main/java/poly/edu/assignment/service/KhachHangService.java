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

    private final KhachHangRepository khachHangRepository;

    public KhachHangService(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    /**
     * Lấy danh sách tất cả khách hàng
     */
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }

    /**
     * Tìm khách hàng theo mã
     */
    public Optional<KhachHang> findById(String id) {
        return khachHangRepository.findById(id);
    }

    /**
     * Lưu khách hàng mới (tự động set ngày tạo nếu chưa có)
     */
    public KhachHang save(KhachHang khachHang) {
        if (khachHang.getNgayTao() == null) {
            khachHang.setNgayTao(LocalDate.now());
        }
        return khachHangRepository.save(khachHang);
    }

    /**
     * Cập nhật thông tin khách hàng (không thay đổi ngày tạo)
     */
    public KhachHang update(KhachHang khachHang) {
        return khachHangRepository.findById(khachHang.getMaKH())
                .map(existing -> {
                    existing.setHoTen(khachHang.getHoTen());
                    existing.setEmail(khachHang.getEmail());
                    existing.setSdt(khachHang.getSdt());
                    // Giữ nguyên ngày tạo
                    return khachHangRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại: " + khachHang.getMaKH()));
    }

    /**
     * Xóa khách hàng theo mã
     */
    public void delete(String id) {
        if (!khachHangRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khách hàng để xóa: " + id);
        }
        khachHangRepository.deleteById(id);
    }
}
