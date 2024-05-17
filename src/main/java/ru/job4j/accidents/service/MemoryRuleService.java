package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repositry.MemoryRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemoryRuleService implements RuleService {
    private final MemoryRuleRepository memoryRuleRepository;

    @Override
    public Rule add(Rule rule) {
        return memoryRuleRepository.add(rule);
    }

    @Override
    public Optional<Collection<Rule>> findAll() {
        return memoryRuleRepository.findAll();
    }

    @Override
    public Optional<Collection<Rule>> findByName(String key) {
        return memoryRuleRepository.findByName(key);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return memoryRuleRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return memoryRuleRepository.delete(id);
    }
}
