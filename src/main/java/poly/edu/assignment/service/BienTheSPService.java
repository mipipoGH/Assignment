package poly.edu.assignment.service;

import poly.edu.assignment.entity.BienTheSP;
import poly.edu.assignment.repository.BienTheSPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BienTheSPService {

    @Autowired
    private BienTheSPRepository repository;

    public List<BienTheSP> findAll() {
        return repository.findAll();
    }

    public BienTheSP save(BienTheSP entity) {
        return repository.save(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
