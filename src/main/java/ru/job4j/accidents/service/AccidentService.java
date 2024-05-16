package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.MemoryAccidentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.job4j.accidents.util.AccidentGenerator.generateAccidents;

@Service
public class AccidentService {
    private final MemoryAccidentRepository memoryAccidentRepository;
    private final AccidentReadMapper accidentReadMapper;

    public AccidentService(MemoryAccidentRepository memoryAccidentRepository, AccidentReadMapper accidentReadMapper) {
        this.memoryAccidentRepository = memoryAccidentRepository;

        this.accidentReadMapper = accidentReadMapper;
    }

    public Accident addAccident(Accident accident) {
        return memoryAccidentRepository.addAccident(accident);
    }

    public List<Accident> findAll() {
        generateAccidents().forEach(memoryAccidentRepository::addAccident);
        return memoryAccidentRepository.findAll().stream().toList();
    }

    public Optional<List<AccidentReadDto>> findByName(String key) {
        return Optional.of(memoryAccidentRepository.findByName(key).stream().map(accidentReadMapper::mapFrom).collect(Collectors.toList()));
    }

    public Optional<AccidentReadDto> findById(int id) {
        return Optional.of(accidentReadMapper.mapFrom(memoryAccidentRepository.findById(id)));
    }

    public boolean update(Accident accident) {
        return memoryAccidentRepository.update(accident);
    }

    public boolean delete(int id) {
        return memoryAccidentRepository.delete(id);
    }
}