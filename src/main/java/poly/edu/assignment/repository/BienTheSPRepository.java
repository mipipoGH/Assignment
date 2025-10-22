package poly.edu.assignment.repository;

import poly.edu.assignment.entity.BienTheSP;
import poly.edu.assignment.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BienTheSPRepository extends JpaRepository<BienTheSP, String> {
    List<BienTheSP> findBySanPham(SanPham sanPham);
}
