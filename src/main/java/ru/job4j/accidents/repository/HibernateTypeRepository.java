package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTypeRepository implements AccidentTypeRepository {

    private final CrudRepository crudRepository;
    private static final String HQL_DELETE = "DELETE AccidentType where id = :fId";
    private static final String HQL_FIND_ALL = "FROM AccidentType";
    private static final String HQL_FIND_BY_ID = "FROM AccidentType where id = :fId";

    @Override
    public AccidentType add(AccidentType accidentType) {
        crudRepository.run(session -> session.save(accidentType));
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository
                .query(HQL_FIND_ALL, AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(HQL_FIND_BY_ID, AccidentType.class, Map.of("fId", id));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(HQL_DELETE, Map.of("fId", id));
    }
}
