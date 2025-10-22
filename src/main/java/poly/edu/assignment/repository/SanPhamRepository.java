package poly.edu.assignment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.SanPham;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
}
