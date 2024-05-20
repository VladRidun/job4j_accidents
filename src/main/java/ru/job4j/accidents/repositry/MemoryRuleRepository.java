package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    private final ConcurrentHashMap<Integer, Rule> rules = new ConcurrentHashMap();

    public MemoryRuleRepository() {
        List<Rule> typesInit = List.of(new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3"));
        typesInit.forEach(this::add);
    }

    @Override
    public Rule add(Rule rule) {
        rules.put(rule.getId(), rule);
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return rules.values().stream()
                .filter(rule -> key.equals(rule.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.of(rules.get(id));
    }

    @Override
    public boolean delete(int id) {
        return rules.remove(id) != null;
    }
}
