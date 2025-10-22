package poly.edu.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.assignment.entity.LoaiSP;

@Repository
public interface LoaiSPRepository extends JpaRepository<LoaiSP, String> {
}
