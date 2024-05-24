package ru.job4j.accidents.repositry;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {
    public Accident add(Accident accident);

    public Collection<Accident> findAll();

    public Collection<Accident> findByName(String key);

    public Optional<Accident> findById(int id);

    public boolean update(Accident accident);

    public boolean delete(int id);
}
