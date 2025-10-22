package poly.edu.assignment.service;

import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NhanVienService {

    private final NhanVienRepository repo;
    public NhanVienService(NhanVienRepository repo) {
        this.repo = repo;
    }
    @Autowired
    private NhanVienRepository repository;

    public List<NhanVien> findAll() {
        return repository.findAll();
    }

    public NhanVien save(NhanVien nv) {
        return repository.save(nv);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
    public Optional<NhanVien> findById(String id) {
        return repo.findById(id);
    }
}