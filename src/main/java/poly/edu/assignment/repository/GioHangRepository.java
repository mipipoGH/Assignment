package poly.edu.assignment.repository;

import poly.edu.assignment.entity.GioHang;
import poly.edu.assignment.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, String> {
    Optional<GioHang> findByKhachHang(KhachHang khachHang);
}
