package ru.job4j.accidents.util;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public class AccidentGenerator {
   public static List<Accident> generateAccidents() {
        return List.of(
                new Accident("Первое нарушение", "наезд на сплошную", "Ульяновск", new AccidentType(1, "Две машины")),
                new Accident("Второе нарушение", "проезд на красный свет", "Самара", new AccidentType(1, "Две машины")),
                new Accident("Третье нарушение", "наезд на газон", "Казань", new AccidentType(1, "Две машины"))
        );
    }

}
