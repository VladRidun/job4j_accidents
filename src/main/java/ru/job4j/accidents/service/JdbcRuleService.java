package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JdbcRuleService implements RuleService {
    private final JdbcRuleService jdbcRuleService;

    @Override
    public Rule add(Rule rule) {
        return jdbcRuleService.add(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbcRuleService.findAll();
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return jdbcRuleService.findByName(key);
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return jdbcRuleService.findAllById(rulesId);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return jdbcRuleService.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return jdbcRuleService.delete(id);
    }
}
