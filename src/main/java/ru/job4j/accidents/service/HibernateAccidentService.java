package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.mapper.AccidentReadMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;
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
        accidentRepository.save(accident);
        return accident;
    }

    @Override
    public List<AccidentReadDto> findAll() {
        List<Accident> accidents = new ArrayList<>(accidentRepository.getAll());
        return accidents.stream().map(accidentReadMapper::mapFrom).collect(Collectors.toList());
    }

    @Override
    public Optional<AccidentReadDto> findById(int id) {
        return accidentRepository.findById(id).map(accidentReadMapper::mapFrom);
    }

    @Override
    public void update(Accident accident, List<Integer> rIds) {
        Set<Rule> rules = new HashSet<>(ruleRepository.findAllById(rIds));
        accident.setRules(rules);
        var typeOptional = accidentTypeRepository.findById(accident.getType().getId());
        typeOptional.ifPresent(accident::setType);
        accidentRepository.save(accident);
    }

    @Override
    public boolean delete(int id) {
        var maybeAccident = accidentRepository.findById(id);
        maybeAccident.ifPresent(accident -> accidentRepository.deleteById(id));
        return maybeAccident.isPresent();
    }
}