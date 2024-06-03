package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateRuleRepository implements RuleRepository {
    private final SessionFactory sf;
    private static final String HQL_DELETE = "DELETE Rule where id = :fId";
    private static final String HQL_FIND_ALL = "FROM Rule";
    private static final String HQL_FIND_ALL_BY_ID = "from Rule where id in :fRulesId";
    private static final String HQL_FIND_BY_NAME = "FROM Rule where name like ?";
    private static final String HQL_FIND_BY_ID = "FROM Rule where id = :fId";

    @Override
    public Rule add(Rule rule) {
        try (Session session = sf.openSession()) {
            session.save(rule);
            return rule;
        }
    }

    @Override
    public Collection<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(HQL_FIND_ALL, Rule.class)
                    .list();
        }
    }

    @Override
    public Collection<Rule> findByName(String key) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_BY_NAME, Rule.class)
                    .list();
        }
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_ALL_BY_ID, Rule.class)
                    .setParameter("fRulesId", rulesId)
                    .stream().toList();
        }
    }

    @Override
    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(HQL_FIND_BY_ID, Rule.class).stream().findFirst();
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
