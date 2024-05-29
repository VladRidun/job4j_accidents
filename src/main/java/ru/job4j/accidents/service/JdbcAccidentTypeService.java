package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repositry.JdbcAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JdbcAccidentTypeService implements AccidentTypeService {
    private final JdbcAccidentTypeRepository jdbcAccidentTypeRepository;

    @Override
    public AccidentType add(AccidentType accidentType) {
        return jdbcAccidentTypeRepository.add(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbcAccidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return jdbcAccidentTypeRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return jdbcAccidentTypeRepository.delete(id);
    }
}
