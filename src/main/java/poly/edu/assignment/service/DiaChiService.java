package poly.edu.assignment.service;

import poly.edu.assignment.entity.DiaChi;
import poly.edu.assignment.repository.DiaChiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiService {

    @Autowired
    private DiaChiRepository repository;

    public List<DiaChi> findAll() {
        return repository.findAll();
    }

    public DiaChi save(DiaChi entity) {
        return repository.save(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}