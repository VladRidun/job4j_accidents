package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor

public class HibernateTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public AccidentType add(AccidentType accidentType) {
        return accidentTypeRepository.save(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return StreamSupport.stream(accidentTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        accidentTypeRepository.deleteById(id);
    }
}
