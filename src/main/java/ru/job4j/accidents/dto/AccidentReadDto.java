package ru.job4j.accidents.dto;

public record AccidentReadDto(int id,
                              String name,
                              String text,
                              String address,
                              String type,
                              String rule) {
}
