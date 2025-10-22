package poly.edu.assignment.repository;

import poly.edu.assignment.entity.DiaChi;
import poly.edu.assignment.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaChiRepository extends JpaRepository<DiaChi, String> {
    List<DiaChi> findByKhachHang(KhachHang khachHang);
    List<DiaChi> findByMacDinhTrue();
}
