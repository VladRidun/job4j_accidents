package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    Accident add(Accident accident, List<Integer> rIds);

    List<AccidentReadDto> findAll();

     Optional<AccidentReadDto> findById(int id);

    void update(Accident accident, List<Integer> rIds);

    boolean delete(int id);
}
