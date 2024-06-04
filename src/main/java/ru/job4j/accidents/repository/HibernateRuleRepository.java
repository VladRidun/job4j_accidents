package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateRuleRepository implements RuleRepository {
    private final CrudRepository crudRepository;
    private static final String HQL_DELETE = "DELETE Rule where id = :fId";
    private static final String HQL_FIND_ALL = "FROM Rule";
    private static final String HQL_FIND_ALL_BY_ID = "from Rule where id in :fRulesId";
    private static final String HQL_FIND_BY_NAME = "FROM Rule where name like ?";
    private static final String HQL_FIND_BY_ID = "FROM Rule where id = :fId";

    @Override
    public Rule add(Rule rule) {
        crudRepository.run(session -> session.save(rule));
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query(HQL_FIND_ALL, Rule.class);
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return crudRepository.query(HQL_FIND_BY_NAME, Rule.class);
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return crudRepository.query(HQL_FIND_ALL_BY_ID, Rule.class, Map.of("fRulesId", rulesId));
    }

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional(HQL_FIND_BY_ID, Rule.class, Map.of("fId", id));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(HQL_DELETE, Map.of("fId", id));
    }
}
