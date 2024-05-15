package ru.job4j.accidents;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.job4j.accidents.controller.IndexController;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.AccidentMem;
import ru.job4j.accidents.service.AccidentService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Accident.class);
        context.register(AccidentMem.class);
        context.register(AccidentService.class);
        context.register(IndexController.class);
        context.refresh();
        var indexCD = context.getBean(IndexController.class);
    }
}