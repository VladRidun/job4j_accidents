package ru.job4j.accidents.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JdbcAccidentMapper implements RowMapper<Accident> {

    Map<Integer, Accident> accidentMap;

    public JdbcAccidentMapper(Map<Integer, Accident> accidentMap) {
        this.accidentMap = accidentMap;
    }

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getAccidentByResultSet(accidentMap, rs);
    }

    /**
     * Метод собирает модель Accident из ResultSet
     * Не содержит Set<Rule>
     *
     * @param resultSet ResultSet
     * @return Accident
     * @throws SQLException exception
     */
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
                    resultSet.getString("name"));
            accident.setType(accidentType);
        }

        int ruleId = resultSet.getInt("rule_id");
        setRuleToAccidentByResultSet(ruleId, resultSet, accident);

        accidentMap.put(accidentId, accident);

        return accident;
    }

    /**
     * Метод добавляет модель Rule в Accident из общего запроса ResultSet
     *
     * @param ruleId    int Role ID
     * @param resultSet ResultSet
     * @param accident  Accident
     * @throws SQLException Exception
     */
    private void setRuleToAccidentByResultSet(int ruleId, ResultSet resultSet, Accident accident) throws SQLException {
        Set<Rule> ruleSet = new HashSet<>();
        if (ruleId != 0) {
            Rule rule = new Rule();
            rule.setId(ruleId);
            rule.setName(resultSet.getString("name"));
         ruleSet.add(rule);
        }
        accident.setRules(ruleSet);
    }
}
