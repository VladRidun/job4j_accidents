package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {
    public AccidentType add(AccidentType accidentType);

    public Collection<AccidentType> findAll();

    public Optional<AccidentType> findById(int id);

    public void delete(int id);
}
