package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private final List<AccidentType> types = new ArrayList<>();
    private final List<AccidentType> typesInit = List.of(
            new AccidentType(1, "Две машины"),
            new AccidentType(2, "Машина и человек"),
            new AccidentType(3, "Машина и велосипед"));

    public MemoryAccidentTypeRepository() {
        typesInit.forEach(this::add);
    }

    @Override
    public AccidentType add(AccidentType accidentType) {
        types.add(accidentType);
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types;
    }

    @Override
    public AccidentType findById(int id) {
        return types.get(id);
    }

    @Override
    public boolean delete(int id) {
        return types.remove(id) != null;
    }
}
