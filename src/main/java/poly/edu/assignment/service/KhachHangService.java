package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KhachHangService {

    private final KhachHangRepository repo;

    public KhachHangService(KhachHangRepository repo) {
        this.repo = repo;
    }

    public List<KhachHang> findAll() {
        return repo.findAll();
    }

    public Optional<KhachHang> findById(String id) {
        return repo.findById(id);
    }

    public KhachHang save(KhachHang khachHang) {
        return repo.save(khachHang);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
