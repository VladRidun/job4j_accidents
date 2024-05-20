package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private final ConcurrentHashMap<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public MemoryAccidentTypeRepository() {
        List<AccidentType> typesInit = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед"));
        typesInit.forEach(accidentType -> types.put(accidentType.getId(), accidentType));
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        types.put(accidentType.getId(), accidentType);
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
