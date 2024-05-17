package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    private final List<Rule> rules = new ArrayList<>();

    public MemoryRuleRepository() {
        List<Rule> typesInit = List.of(new Rule(1, "Статья. 1"),
                        new Rule(2, "Статья. 2"),
                        new Rule(3, "Статья. 3"));
        typesInit.forEach(this::add);
    }

    @Override
    public Rule add(Rule rule) {
        rules.add(rule);
        return rule;
    }

    @Override
    public Optional<Collection<Rule>> findAll() {
        return Optional.of(rules);
    }

    @Override
    public Optional<Collection<Rule>> findByName(String key) {
        return Optional.of(rules.stream()
                .filter(rule -> key.equals(rule.getName()))
                .collect(Collectors.toList()));
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
