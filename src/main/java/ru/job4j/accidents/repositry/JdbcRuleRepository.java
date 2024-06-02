package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.JdbcTypeMapper;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Rule add(Rule rule) {
        jdbc.update("insert into rules (id, rule_name) values (?)",
                rule.getName());
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("select id, rule_name from rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("rule_name"));
                    return rule;
                });
    }

    @Override
    public Collection<Rule> findByName(String key) {
        return jdbc.query("select id, rule_name from rules where rule_name = ?", new Object[]{key},
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("rule_name"));
                    return rule;
                });
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        Set<Rule> ruleSet = new HashSet<>();
        for (int id: rulesId) {
            ruleSet.addAll(jdbc.query("select id, rule_name from rules where id in (?)", new Object[]{id},
                    (rs, row) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("rule_name"));
                        return rule;
                    }).stream().collect(Collectors.toSet()));
        }
        return ruleSet;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return jdbc.query("select id, rule_name from rules where id = ?", new Object[]{id}, new BeanPropertyRowMapper(Rule.class)).stream().findFirst();
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update("delete from rules where id = ?", id) > 0;
    }
}
