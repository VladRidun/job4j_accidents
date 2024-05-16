package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.job4j.accidents.util.AccidentGenerator.generateAccidents;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {
    private final AtomicInteger accidentId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();

    private MemoryAccidentRepository() {
        generateAccidents().forEach(this::add);
    }

    @Override
    public Accident add(Accident accident) {
        accident.setId(accidentId.getAndIncrement());
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Collection<Accident> findByName(String key) {
        return accidents.values().stream()
                .filter(accident -> key.equals(accident.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Accident findById(int id) {
        return accidents.get(id);
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(),
                (id, oldVacancy) -> new Accident(
                        oldVacancy.getId(),
                        accident.getName(),
                        accident.getText(),
                        accident.getAddress())) != null;
    }

    @Override
    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }
}