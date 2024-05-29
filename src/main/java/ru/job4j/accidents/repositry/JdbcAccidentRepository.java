package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.JdbcAccidentMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Accident add(Accident accident) {
        jdbc.update("insert into accidents (name, text, address, type_id) values (?, ?, ?,?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId());
        var rulesSet = accident.getRules();
        for (Rule rule : rulesSet) {
            jdbc.update("insert into accindents_rules (accindent_id, rule_id) VALUES (?,?)",
                    accident.getId(), rule.getId());
        }
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(
                "select * from accidents a "
                        + "left join types t on a.type_id = t.id"
                        + " join accindents_rules ar on a.id = ar.accindent_id"
                        + " join public.rules r on ar.rule_id = r.id",
                new JdbcAccidentMapper(accidentMap));

        return accidentMap.values();
    }

    @Override
    public Collection<Accident> findByName(String key) {
        return null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Accident accident) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
