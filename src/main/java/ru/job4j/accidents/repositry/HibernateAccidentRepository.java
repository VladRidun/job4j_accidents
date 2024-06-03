package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {
    private final SessionFactory sf;
    private static final String HQL_DELETE = "DELETE Accident  where id = :fId";
    private static final String HQL_FIND_ALL = "select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules order by a.id";
    private static final String HQL_FIND_BY_NAME = "select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules where a.name like :fName order by a.id";
    private static final String HQL_FIND_BY_ID = " select distinct a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules where a.id = :fId";

    @Override
    public Accident add(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.persist(accident);
            session.getTransaction().commit();
            session.close();
        }
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(HQL_FIND_ALL, Accident.class)
                    .list();
        }
    }

    @Override
    public Collection<Accident> findByName(String key) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_BY_NAME, Accident.class).setParameter("fName", key)
                    .list();
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_BY_ID, Accident.class).setParameter("fId", id).stream().findFirst();
        }
    }

    @Override
    public void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
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
