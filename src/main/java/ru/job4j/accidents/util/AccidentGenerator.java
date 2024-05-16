package ru.job4j.accidents.util;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public class AccidentGenerator {
    public static List<Accident> generateAccidents() {
        return List.of(
                new Accident("Первое нарушение", "наезд на сплошную", "Ульяновск", new AccidentType(1, "один автомобиль")),
                new Accident("Второе нарушение", "проезд на красный свет", "Самара", new AccidentType(1, "один автомобиль")),
                new Accident("Третье нарушение", "наезд на газон", "Казань", new AccidentType(1, "один автомобиль"))
        );
    }

}
