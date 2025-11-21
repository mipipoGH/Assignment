package poly.edu.assignment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.entity.KhachHang;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
    // Tìm đơn hàng theo khách hàng, sắp xếp theo ngày mới nhất
    List<HoaDon> findByKhachHangOrderByNgayLapDesc(KhachHang khachHang);

    // Tìm đơn hàng theo mã và khách hàng
    HoaDon findByMaHDAndKhachHang(String maHD, KhachHang khachHang);
}

