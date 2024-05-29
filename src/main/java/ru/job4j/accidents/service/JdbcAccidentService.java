package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repositry.JdbcAccidentRepository;
import ru.job4j.accidents.repositry.JdbcAccidentTypeRepository;
import ru.job4j.accidents.repositry.JdbcRuleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JdbcAccidentService implements AccidentService {
    private final JdbcAccidentRepository jdbcAccidentRepository;
    private final JdbcAccidentTypeRepository jdbcAccidentTypeRepository;
    private final JdbcRuleRepository jdbcRuleRepository;
    private final AccidentReadMapper accidentReadMapper;

    @Override
    public Accident add(Accident accident, List<Integer> rIds) {
        var rules = new HashSet<>(jdbcRuleRepository.findAllById(rIds));
        accident.setRules(rules);
        accident.setType(jdbcAccidentTypeRepository.findById(accident.getType().getId()).get());
        return jdbcAccidentRepository.add(accident);
    }

    @Override
    public List<AccidentReadDto> findAll() {
        return jdbcAccidentRepository.findAll()
                .stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccidentReadDto> findByName(String key) {
        return jdbcAccidentRepository.findByName(key)
                .stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccidentReadDto> findById(int id) {
        return jdbcAccidentRepository.findById(id).isPresent()
                ? Optional.of(accidentReadMapper.mapFrom(jdbcAccidentRepository.findById(id).get()))
                : Optional.empty();
    }

    @Override
    public boolean update(Accident accident) {
        return jdbcAccidentRepository.update(accident);
    }

    @Override
    public boolean delete(int id) {
        var maybeAccident = jdbcAccidentRepository.findById(id);
        maybeAccident.ifPresent(accident -> jdbcAccidentRepository.delete(id));
        return maybeAccident.isPresent();
    }
}
