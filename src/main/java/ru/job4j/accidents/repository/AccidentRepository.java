package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {

    Accident add(Accident accident);

    Collection<Accident> findAll();

    Collection<Accident> findByName(String key);

    Optional<Accident> findById(int id);

    void update(Accident accident);

    void delete(int id);
}
