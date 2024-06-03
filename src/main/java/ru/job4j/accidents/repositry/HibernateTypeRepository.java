package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTypeRepository implements AccidentTypeRepository {

    private final SessionFactory sf;
    private static final String HQL_DELETE = "DELETE AccidentType where id = :fId";
    private static final String HQL_FIND_ALL = "FROM AccidentType";
    private static final String HQL_FIND_BY_ID = "FROM AccidentType where id = :fId";

    @Override
    public AccidentType add(AccidentType accidentType) {
        try (Session session = sf.openSession()) {
            session.save(accidentType);
            return accidentType;
        }
    }

    @Override
    public Collection<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(HQL_FIND_ALL, AccidentType.class)
                    .list();
        }
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_BY_ID, AccidentType.class)
                    .setParameter("fId", id).stream().findFirst();
        }
    }

    @Override
    public boolean delete(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_DELETE)
                    .setParameter("fId", id)
                    .executeUpdate() > 0;
        }
    }
}
