package com.TFG.app.backend.type_periodic.service;
import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
import com.TFG.app.backend.type_periodic.repository.Type_PeriodicRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Type_PeriodicService {
    private final Type_PeriodicRepository typePeriodicRepository;

    public Type_PeriodicService(Type_PeriodicRepository typePeriodicRepository) {
        this.typePeriodicRepository = typePeriodicRepository;
    }

    public List<Type_Periodic> getAllTypePeriodics() {
        return typePeriodicRepository.findAll();
    }

    public Type_Periodic getTypePeriodicById(Integer id) {
        return typePeriodicRepository.findById(id).orElse(null);
    }
}
