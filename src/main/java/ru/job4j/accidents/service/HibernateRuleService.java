package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor

public class HibernateRuleService implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Rule add(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return StreamSupport.stream(ruleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return ruleRepository.findByName(key);
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return ruleRepository.findAllById(rulesId);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        ruleRepository.deleteById(id);
    }
}
