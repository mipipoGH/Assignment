package poly.edu.assignment.repository;

import poly.edu.assignment.entity.ThanhToan;
import poly.edu.assignment.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, String> {
    Optional<ThanhToan> findByHoaDon(HoaDon hoaDon);
}
