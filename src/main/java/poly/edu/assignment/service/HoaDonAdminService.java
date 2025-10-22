package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.repository.HoaDonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonAdminService {

    private final HoaDonRepository hoaDonRepository;

    public HoaDonAdminService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    public Optional<HoaDon> findById(String maHD) {
        return hoaDonRepository.findById(maHD);
    }

    public HoaDon updateStatus(String maHD, String newStatus) {
        HoaDon hd = hoaDonRepository.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        hd.setTrangThai(newStatus);
        return hoaDonRepository.save(hd);
    }
}
