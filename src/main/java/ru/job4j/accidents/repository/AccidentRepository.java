package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Query("select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules order by a.id")
    Collection<Accident> getAll();

    @Query(" select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules where a.id = :id")
    Optional<Accident> findById(int id);

    @Query("delete FROM Accident a where a.id = :id")
    void deleteById(int id);
}
