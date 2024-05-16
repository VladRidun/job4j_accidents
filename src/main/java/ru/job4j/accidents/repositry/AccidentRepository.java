package ru.job4j.accidents.repositry;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentRepository {
    public Accident addAccident(Accident accident);

    public Collection<Accident> findAll();

    public Collection<Accident> findByName(String key);

    public Accident findById(int id);

    public boolean update(Accident accident);

    public boolean delete(int id);
}
