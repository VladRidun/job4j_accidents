package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repositry.JdbcRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JdbcRuleService implements RuleService {
    private final JdbcRuleRepository ruleRepository;

    @Override
    public Rule add(Rule rule) {
        return ruleRepository.add(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
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
    public boolean delete(int id) {
        return ruleRepository.delete(id);
    }
}
