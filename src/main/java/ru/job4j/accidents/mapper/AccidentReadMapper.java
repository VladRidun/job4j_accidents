package ru.job4j.accidents.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AccidentReadMapper implements Mapper<Accident, AccidentReadDto> {

    public AccidentReadDto mapFrom(Accident a) {
        return new AccidentReadDto(a.getId(),
                a.getName(),
                a.getText(),
                a.getAddress(),
                a.getType().getName(),
                a.getRules().stream().map(Rule::getName).collect(Collectors.joining("; ")));
    }
}
