package poly.edu.assignment.service;

import poly.edu.assignment.entity.ChiTietHD;
import poly.edu.assignment.entity.ChiTietHDKey;
import poly.edu.assignment.repository.ChiTietHDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietHDService {

    @Autowired
    private ChiTietHDRepository repository;

    public List<ChiTietHD> findAll() {
        return repository.findAll();
    }

    public ChiTietHD save(ChiTietHD chiTietHD) {
        return repository.save(chiTietHD);
    }

    public void deleteById(ChiTietHDKey id) {
        repository.deleteById(id);
    }
}