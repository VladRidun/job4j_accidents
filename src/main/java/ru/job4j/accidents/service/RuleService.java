package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {
    public Rule add(Rule rule);

    public Optional<Collection<Rule>> findAll();

    public Optional<Collection<Rule>> findByName(String key);

    public Optional<Rule> findById(int id);

    public boolean delete(int id);
}
