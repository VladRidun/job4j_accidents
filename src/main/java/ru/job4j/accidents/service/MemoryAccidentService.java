package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repositry.MemoryAccidentRepository;
import ru.job4j.accidents.repositry.MemoryAccidentTypeRepository;
import ru.job4j.accidents.repositry.MemoryRuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemoryAccidentService implements AccidentService {
    private final MemoryAccidentRepository memoryAccidentRepository;
    private final MemoryAccidentTypeRepository memoryAccidentTypeRepository;
    private final AccidentReadMapper accidentReadMapper;
    private final MemoryRuleRepository memoryRuleRepository;

    public MemoryAccidentService(MemoryAccidentRepository memoryAccidentRepository, MemoryAccidentTypeRepository memoryAccidentTypeRepository, AccidentReadMapper accidentReadMapper, MemoryRuleRepository memoryRuleRepository) {
        this.memoryAccidentRepository = memoryAccidentRepository;
        this.memoryAccidentTypeRepository = memoryAccidentTypeRepository;
        this.accidentReadMapper = accidentReadMapper;
        this.memoryRuleRepository = memoryRuleRepository;
    }

    public Accident add(Accident accident, List<Integer> rIds) {
        accident.setRules(memoryRuleRepository.findAllById(rIds));
        accident.setType(memoryAccidentTypeRepository.findById(accident.getType().getId()).get());
        return memoryAccidentRepository.add(accident);
    }

    public List<AccidentReadDto> findAll() {
        return memoryAccidentRepository.findAll()
                .stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<AccidentReadDto> findByName(String key) {
        return memoryAccidentRepository.findByName(key)
                .stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
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