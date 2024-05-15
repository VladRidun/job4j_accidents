package ru.job4j.accidents.repositry;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {
    private final AtomicInteger accidentId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();

    public Accident addAccident(Accident accident) {
        accident.setId(accidentId.getAndIncrement());
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public List<Accident> findByName(String key) {
        List<Accident> rsl = new ArrayList<>();
        for (Accident accident : accidents.values()) {
            if (key.equals(accident.getName())) {
                rsl.add(accident);
            }
        }
        return rsl;
    }

    public Accident findById(int id) {
        int index = indexOf(id);
        return index != -1 ? accidents.get(index) : null;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < accidents.size(); index++) {
            if (accidents.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public boolean replace(int id, Accident accident) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            accident.setId(id);
            accidents.put(index, accident);
        }
        return rsl;
    }

    public boolean delete(int id) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            accidents.remove(index);
        }
        return rsl;
    }

}
