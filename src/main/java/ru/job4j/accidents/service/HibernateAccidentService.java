package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.AccidentRepository;
import ru.job4j.accidents.repositry.AccidentTypeRepository;
import ru.job4j.accidents.repositry.RuleRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class HibernateAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;
    private final AccidentReadMapper accidentReadMapper;

    @Override
    public Accident add(Accident accident, List<Integer> rIds) {
        var rules = new HashSet<>(ruleRepository.findAllById(rIds));
        accident.setRules(rules);
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        return accidentRepository.add(accident);
    }

    @Override
    public List<AccidentReadDto> findAll() {
        return accidentRepository.findAll().stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccidentReadDto> findByName(String key) {
        return accidentRepository.findByName(key)
                .stream()
                .map(accidentReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccidentReadDto> findById(int id) {
        return accidentRepository.findById(id).isPresent()
                ? Optional.of(accidentReadMapper.mapFrom(accidentRepository.findById(id).get()))
                : Optional.empty();
    }

    @Override
    public void update(Accident accident, List<Integer> rIds) {
        var rules = new HashSet<>(ruleRepository.findAllById(rIds));
        accident.setRules(rules);
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accidentRepository.update(accident);
    }

    @Override
    public boolean delete(int id) {
        var maybeAccident = accidentRepository.findById(id);
        maybeAccident.ifPresent(accident -> accidentRepository.delete(id));
        return maybeAccident.isPresent();
    }
}