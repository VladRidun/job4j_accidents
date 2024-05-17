package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repositry.MemoryAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;
@Service
public class MemoryAccidentTypeService implements AccidentTypeService {
    private final MemoryAccidentTypeRepository accidentTypeRepository;

    public MemoryAccidentTypeService(MemoryAccidentTypeRepository accidentTypeRepository) {
        this.accidentTypeRepository = accidentTypeRepository;
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        return accidentTypeRepository.add(accidentType);
    }

    @Override
    public Optional<Collection<AccidentType>> findAll() {
        return Optional.of(accidentTypeRepository.findAll());
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.of(accidentTypeRepository.findById(id));
    }

    @Override
    public boolean delete(int id) {
        return accidentTypeRepository.delete(id);
    }
}
