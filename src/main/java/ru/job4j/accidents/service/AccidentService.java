package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccidentService {
    public Accident add(Accident accident);

    public Optional<List<AccidentReadDto>> findAll();

    public Optional<List<AccidentReadDto>> findByName(String key);

    Optional<AccidentReadDto> findById(int id);

    public boolean update(Accident accident);

    public boolean delete(int id);
}
