package poly.edu.assignment.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.SanPham;
import poly.edu.assignment.repository.SanPhamRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SanPhamService {
    private final SanPhamRepository repo;

    public SanPhamService(SanPhamRepository repo) {
        this.repo = repo;
    }

    public List<SanPham> findAll() { return repo.findAll(); }
    public Optional<SanPham> findById(String id) { return repo.findById(id); }
    public SanPham save(SanPham sp) { return repo.save(sp); }
    public void delete(String id) { repo.deleteById(id); }
}
