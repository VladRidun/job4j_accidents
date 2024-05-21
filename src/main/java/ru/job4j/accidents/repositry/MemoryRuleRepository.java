package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    private final AtomicInteger ruleId = new AtomicInteger(1);
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public MemoryRuleRepository() {
        List<Rule> typesInit = List.of(new Rule("Статья. 1"),
                new Rule("Статья. 2"),
                new Rule("Статья. 3"));
        typesInit.forEach(this::add);
    }

    @Override
    public Rule add(Rule rule) {
        rule.setId(ruleId.getAndIncrement());
        rules.putIfAbsent(rule.getId(), rule);
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
