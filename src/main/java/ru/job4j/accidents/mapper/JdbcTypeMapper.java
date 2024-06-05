package ru.job4j.accidents.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.AccidentType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTypeMapper implements RowMapper<AccidentType> {
    @Override
    public AccidentType mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("id"));
        type.setName(rs.getString("type_name"));
        return type;
    }
}
