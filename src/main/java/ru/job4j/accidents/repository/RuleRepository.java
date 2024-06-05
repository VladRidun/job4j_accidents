package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository extends CrudRepository<Rule, Integer> {

    @Query("select id, name from Rule where name like :name")
    public Collection<Rule> findByName(String name);

    @Query("from Rule where id in :rulesId")
    Collection<Rule> findAllById(Collection<Integer> rulesId);

    @Query("select id, name from Rule where id = :id")
    public Optional<Rule> findById(int id);

    @Query("delete from Rule where id = :id")
    public void deleteById(int id);
}
