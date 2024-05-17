package ru.job4j.accidents.util;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public class AccidentGenerator {

    private  final Set<Rule> rules = Set.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3"));

    public static List<Accident> generateAccidents() {
        return List.of(
                new Accident("Первое нарушение", "наезд на сплошную", "Ульяновск", new AccidentType(1, "Две машины")),
                new Accident("Второе нарушение", "проезд на красный свет", "Самара", new AccidentType(1, "Две машины")),
                new Accident("Третье нарушение", "наезд на газон", "Казань", new AccidentType(1, "Две машины"))
        );
    }

}
