package ru.job4j.accidents.repositry;


import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {
    public Rule add(Rule rule);

    public Collection<Rule> findAll();

    public Collection<Rule> findByName(String key);

    public Optional<Rule> findById(int id);

    public boolean delete(int id);
}