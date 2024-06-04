package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {
    private final CrudRepository crudRepository;
    private static final String HQL_DELETE = "DELETE Accident  where id = :fId";
    private static final String HQL_FIND_ALL = "select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules order by a.id";
    private static final String HQL_FIND_BY_NAME = "select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules where a.name like :fName order by a.id";
    private static final String HQL_FIND_BY_ID = " select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules where a.id = :fId";

    @Override
    public Accident add(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query(
                HQL_FIND_ALL, Accident.class);
    }

    @Override
    public Collection<Accident> findByName(String key) {
        return crudRepository.query(HQL_FIND_BY_NAME, Accident.class,
                Map.of("fName", key));
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(HQL_FIND_BY_ID, Accident.class,
                Map.of("fId", id));
    }

    @Override
    public void update(Accident accident) {
        crudRepository.run(session -> session.saveOrUpdate(accident));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                HQL_DELETE,
                Map.of("fId", id)
        );
    }
}
