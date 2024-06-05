package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {
    Rule add(Rule rule);

    Collection<Rule> findAll();

    Collection<Rule> findAllById(Collection<Integer> rulesId);

    Optional<Rule> findById(int id);

    void delete(int id);
}
