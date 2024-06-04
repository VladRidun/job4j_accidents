package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor

public class HibernateTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public AccidentType add(AccidentType accidentType) {
        return accidentTypeRepository.add(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        accidentTypeRepository.delete(id);
    }
}
