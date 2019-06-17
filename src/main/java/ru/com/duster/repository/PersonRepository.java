package ru.com.duster.repository;

import org.springframework.data.repository.CrudRepository;
import ru.com.duster.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
