package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.AccidentMem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.job4j.accidents.util.AccidentGenerator.generateAccidents;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentReadMapper accidentReadMapper;

    public AccidentService(AccidentMem accidentMem, AccidentReadMapper accidentReadMapper) {
        this.accidentMem = accidentMem;

        this.accidentReadMapper = accidentReadMapper;
    }

    public Accident addAccident(Accident accident) {
        return accidentMem.addAccident(accident);
    }

    public List<Accident> findAll() {
        generateAccidents().forEach(accidentMem::addAccident);
        return accidentMem.findAll();
    }

    public Optional<List<AccidentReadDto>> findByName(String key) {
        return Optional.of(accidentMem.findByName(key).stream().map(accidentReadMapper::mapFrom).collect(Collectors.toList()));
    }

    public Optional<AccidentReadDto> findById(int id) {
        return Optional.of(accidentReadMapper.mapFrom(accidentMem.findById(id)));
    }

    public boolean replace(int id, Accident accident) {
        return accidentMem.replace(id, accident);
    }

    public boolean delete(int id) {
        return accidentMem.delete(id);
    }
}