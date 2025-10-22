package poly.edu.assignment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.ChiTietHD;
import poly.edu.assignment.entity.ChiTietHDKey;

@Repository
public interface ChiTietHDRepository extends JpaRepository<ChiTietHD, ChiTietHDKey> {
}
