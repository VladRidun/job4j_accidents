package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import ru.job4j.accidents.model.AccidentType;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
    @Query("FROM AccidentType")
    Collection<AccidentType> getAll();

    @Query("FROM AccidentType where id = :id")
    Optional<AccidentType> findById(int id);

    @Query("DELETE AccidentType where id = :id")
    void deleteById(int id);
}
