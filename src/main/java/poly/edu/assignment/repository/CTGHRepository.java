package poly.edu.assignment.repository;

import poly.edu.assignment.entity.CTGH;
import poly.edu.assignment.entity.CTGHKey;
import poly.edu.assignment.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTGHRepository extends JpaRepository<CTGH, CTGHKey> {
    List<CTGH> findByGioHang(GioHang gioHang);
}
