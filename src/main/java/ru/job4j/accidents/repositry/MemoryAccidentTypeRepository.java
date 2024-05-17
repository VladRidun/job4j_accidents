package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private final List<AccidentType> types = new ArrayList<>();

    public MemoryAccidentTypeRepository() {
        List<AccidentType> typesInit = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед"));
        typesInit.forEach(this::add);
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        types.add(accidentType);
        return accidentType;
    }

    @Override
    public Optional<Collection<AccidentType>> findAll() {
        return Optional.of(types);
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
