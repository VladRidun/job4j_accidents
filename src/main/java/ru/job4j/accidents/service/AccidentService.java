package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repositry.AccidentMem;

import java.util.List;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Accident addAccident(Accident accident) {
        return accidentMem.addAccident(accident);
    }

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public List<Accident> findByName(String key) {
        return accidentMem.findByName(key);
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public boolean replace(int id, Accident accident) {
        return accidentMem.replace(id, accident);
    }

    public boolean delete(int id) {
        return accidentMem.delete(id);
    }

}
