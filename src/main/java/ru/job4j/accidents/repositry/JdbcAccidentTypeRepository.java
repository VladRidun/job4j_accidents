package ru.job4j.accidents.repositry;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.JdbcTypeMapper;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcAccidentTypeRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public AccidentType add(AccidentType accidentType) {
        jdbc.update("insert into types (type_name) values (?)",
                accidentType.getName());
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query("select id, type_name from types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("type_name"));
                    return accidentType;
                });
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return jdbc.query("select id, type_name from types where id = ?", new Object[]{id}, new JdbcTypeMapper()).stream().findAny();
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update("delete from types where id = ?", id) > 0;
    }
}
