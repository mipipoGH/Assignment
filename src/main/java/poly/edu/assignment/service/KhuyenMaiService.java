package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.KhuyenMai;
import poly.edu.assignment.repository.KhuyenMaiRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KhuyenMaiService {

    private final KhuyenMaiRepository repo;

    public KhuyenMaiService(KhuyenMaiRepository repo) {
        this.repo = repo;
    }

    public List<KhuyenMai> findAll() {
        return repo.findAll();
    }

    public Optional<KhuyenMai> findById(String id) {
        return repo.findById(id);
    }

    public KhuyenMai save(KhuyenMai km) {
        return repo.save(km);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}