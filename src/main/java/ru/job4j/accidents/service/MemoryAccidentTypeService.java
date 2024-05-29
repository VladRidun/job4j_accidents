package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repositry.MemoryAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemoryAccidentTypeService implements AccidentTypeService {
    private final MemoryAccidentTypeRepository accidentTypeRepository;

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
    public boolean delete(int id) {
        return accidentTypeRepository.delete(id);
    }
}
