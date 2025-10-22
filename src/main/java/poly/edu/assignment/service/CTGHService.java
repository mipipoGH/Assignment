package poly.edu.assignment.service;

import poly.edu.assignment.entity.CTGH;
import poly.edu.assignment.entity.CTGHKey;
import poly.edu.assignment.repository.CTGHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTGHService {

    @Autowired
    private CTGHRepository repository;

    public List<CTGH> findAll() {
        return repository.findAll();
    }

    public CTGH save(CTGH entity) {
        return repository.save(entity);
    }

    public void deleteById(CTGHKey key) {
        repository.deleteById(key);
    }
}
