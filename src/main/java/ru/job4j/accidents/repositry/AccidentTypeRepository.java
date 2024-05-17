package ru.job4j.accidents.repositry;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public interface AccidentTypeRepository {
    public AccidentType add(AccidentType accidentType);

    public Optional<Collection<AccidentType>> findAll();

    public Optional<AccidentType> findById(int id);

    public boolean delete(int id);
}
