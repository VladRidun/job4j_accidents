package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.JdbcTypeMapper;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Rule add(Rule rule) {
        jdbc.update("insert into rules (name) values (?)",
                rule.getName());
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("select id, name from rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return jdbc.query("select id, name from rules where name = ?", new Object[]{key},
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return jdbc.query("select id, name from rules where name = ?", new Object[]{rulesId},
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Optional<Rule> findById(int id) {
        return jdbc.query("select id, name from rules where id = ?", new Object[]{id}, new BeanPropertyRowMapper(Rule.class)).stream().findFirst();
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update("delete from rules where id = ?", id) > 0;
    }
}
