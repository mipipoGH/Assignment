package poly.edu.assignment.service;

import poly.edu.assignment.entity.ThanhToan;
import poly.edu.assignment.repository.ThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanService {

    @Autowired
    private ThanhToanRepository repository;

    public List<ThanhToan> findAll() {
        return repository.findAll();
    }

    public ThanhToan save(ThanhToan entity) {
        return repository.save(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}