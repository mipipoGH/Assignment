package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.LoaiSP;
import poly.edu.assignment.repository.LoaiSPRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoaiSPService {

    private final LoaiSPRepository repo;

    public LoaiSPService(LoaiSPRepository repo) {
        this.repo = repo;
    }

    public List<LoaiSP> findAll() {
        return repo.findAll();
    }

    public Optional<LoaiSP> findById(String id) {
        return repo.findById(id);
    }

    public LoaiSP save(LoaiSP loai) {
        return repo.save(loai);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
