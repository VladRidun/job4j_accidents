package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.MemoryAccidentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemoryAccidentService implements AccidentService {
    private final MemoryAccidentRepository memoryAccidentRepository;
    private final AccidentReadMapper accidentReadMapper;

    public MemoryAccidentService(MemoryAccidentRepository memoryAccidentRepository, AccidentReadMapper accidentReadMapper) {
        this.memoryAccidentRepository = memoryAccidentRepository;

        this.accidentReadMapper = accidentReadMapper;
    }

    public Accident add(Accident accident) {
        return memoryAccidentRepository.add(accident);
    }

    public Optional<List<AccidentReadDto>> findAll() {
        return memoryAccidentRepository.findAll().isPresent()
                ? Optional.of(memoryAccidentRepository.findAll()
                .get().stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList()))
                : Optional.empty();
    }

    public Optional<List<AccidentReadDto>> findByName(String key) {
        return memoryAccidentRepository.findByName(key).isPresent()
                ? Optional.of(memoryAccidentRepository.findByName(key)
                .get().stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList()))
                : Optional.empty();
    }

    public Optional<AccidentReadDto> findById(int id) {
        return memoryAccidentRepository.findById(id).isPresent()
                ? Optional.of(accidentReadMapper.mapFrom(memoryAccidentRepository.findById(id).get()))
                : Optional.empty();
    }

    public boolean update(Accident accident) {
        return memoryAccidentRepository.update(accident);
    }

    public boolean delete(int id) {
        var maybeAccident = memoryAccidentRepository.findById(id);
        maybeAccident.ifPresent(accident -> memoryAccidentRepository.delete(id));
        return maybeAccident.isPresent();
    }
}