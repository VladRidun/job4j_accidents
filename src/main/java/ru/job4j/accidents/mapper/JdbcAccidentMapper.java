package ru.job4j.accidents.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class JdbcAccidentMapper implements RowMapper<Accident> {
    Map<Integer, Accident> accidentMap;

    public JdbcAccidentMapper() {
    }

    public JdbcAccidentMapper(Map<Integer, Accident> accidentMap) {
        this.accidentMap = accidentMap;
    }

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getAccidentByResultSet(accidentMap, rs);
    }

    private Accident getAccidentByResultSet(Map<Integer, Accident> accidentMap, ResultSet resultSet) throws SQLException {
        int accidentId = resultSet.getInt("id");
        Accident accident = accidentMap.get(accidentId);
        if (accident == null) {
            accident = new Accident();
            accident.setId(accidentId);
            accident.setName(resultSet.getString("name"));
            accident.setText(resultSet.getString("text"));
            accident.setAddress(resultSet.getString("address"));
            AccidentType accidentType = new AccidentType(
                    resultSet.getInt("type_id"),
                    resultSet.getString("type_name"));
            accident.setType(accidentType);
        }
        int ruleId = resultSet.getInt("rule_id");
        setRuleToAccidentByResultSet(ruleId, resultSet, accident);
        accidentMap.put(accidentId, accident);
        return accident;
    }

    private void setRuleToAccidentByResultSet(int ruleId, ResultSet resultSet, Accident accident) throws SQLException {
        if (ruleId != 0) {
            Rule rule = new Rule();
            rule.setId(ruleId);
            rule.setName(resultSet.getString("rule_name"));
            accident.addRule(rule);
        }
    }
}
