package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.JdbcAccidentMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Accident add(Accident accident) {
        if (accident != null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final String insert_Sql = "insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)";
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insert_Sql, new String[]{"id"});
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            }, keyHolder);
            var accidentId = keyHolder.getKey();
            var rulesSet = accident.getRules();
            for (Rule rule : rulesSet) {
                jdbc.update("insert into accindents_rules (accindents_id, rule_id) VALUES (?,?)",
                        accidentId, rule.getId());
            }
        }
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(
                "select * from accidents a "
                        + " left join types t on a.type_id = t.id"
                        + " left join accindents_rules ar on a.id = ar.accindents_id"
                        + " left join rules r on ar.rule_id = r.id",
                new JdbcAccidentMapper(accidentMap));
        return accidentMap.values();
    }

    @Override
    public Collection<Accident> findByName(String key) {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        final String sql = "select * from accidents a "
                + " left join types t on a.type_id = t.id"
                + " left join accindents_rules ar on a.id = ar.accindents_id"
                + " left join public.rules r on ar.rule_id = r.id";
        jdbc.query(sql,
                new Object[]{key},
                new JdbcAccidentMapper(accidentMap));
        return accidentMap.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        final String sql = "select * from accidents a "
                + "left join types t on a.type_id = t.id"
                + " left join accindents_rules ar on a.id = ar.accindents_id"
                + " left join public.rules r on ar.rule_id = r.id"
                + " where a.id = ?";
        jdbc.query(sql,
                new Object[]{id},
                new JdbcAccidentMapper(accidentMap));
        return Optional.of(accidentMap.get(id));
    }

    @Override
    public boolean update(Accident accident) {
        boolean rs = false;
        if (accident != null) {
            final String update_Sql = "update accidents SET name = ?,  text = ?, address = ?, type_id = ? where id = ?";
            rs = 0 < jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(update_Sql);
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                ps.setInt(5, accident.getId());
                return ps;
            });
            jdbc.update(
                    "delete from  accindents_rules where accindents_id = ?", accident.getId());
            var rulesSet = accident.getRules();
            for (Rule rule : rulesSet) {
                jdbc.update("insert into accindents_rules (accindents_id, rule_id) VALUES (?,?)",
                        accident.getId(), rule.getId());
            }
        }
        return rs;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidents using accindents_rules where id = ?",
                id) > 0;
    }
}
