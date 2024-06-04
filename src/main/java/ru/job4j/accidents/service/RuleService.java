package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {
    public Rule add(Rule rule);

    public Collection<Rule> findAll();

    public Collection<Rule> findByName(String key);

    Collection<Rule> findAllById(Collection<Integer> rulesId);

    public Optional<Rule> findById(int id);

    public void delete(int id);
}
