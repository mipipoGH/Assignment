package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.ChiTietHD;
import poly.edu.assignment.entity.ChiTietHDKey;

import java.util.List;

@Repository
public interface ChiTietHDRepository extends JpaRepository<ChiTietHD, ChiTietHDKey> {
    List<ChiTietHD> findByHoaDon_MaHD(String maHD);
}
