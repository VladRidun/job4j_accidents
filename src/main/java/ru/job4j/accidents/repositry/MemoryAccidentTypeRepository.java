package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private final AtomicInteger accidentTypeId = new AtomicInteger(1);
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public MemoryAccidentTypeRepository() {
        List<AccidentType> typesInit = List.of(
                new AccidentType("Две машины"),
                new AccidentType("Машина и человек"),
                new AccidentType("Машина и велосипед"));
        typesInit.forEach(this::add);
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        accidentType.setId(accidentTypeId.getAndIncrement());
        types.putIfAbsent(accidentType.getId(), accidentType);
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return new ArrayList<>(types.values());
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.of(types.get(id));
    }

    @Override
    public boolean delete(int id) {
        return types.remove(id) != null;
    }
}
