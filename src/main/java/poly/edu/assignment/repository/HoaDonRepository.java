package poly.edu.assignment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.HoaDon;
import poly.edu.assignment.entity.KhachHang;

import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
}

