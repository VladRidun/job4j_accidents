package ru.job4j.accidents.repositry;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

public interface AccidentTypeRepository {
    public AccidentType add(AccidentType accidentType);

    public Collection<AccidentType> findAll();

    public AccidentType findById(int id);

    public boolean delete(int id);
}
