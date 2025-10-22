package poly.edu.assignment.service;

import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {

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
}