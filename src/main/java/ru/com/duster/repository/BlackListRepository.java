package ru.com.duster.repository;

import org.springframework.data.repository.CrudRepository;
import ru.com.duster.domain.BlackList;
import ru.com.duster.domain.Person;

public interface BlackListRepository extends CrudRepository<BlackList, Integer> {

    BlackList findByPerson(Person person);
}
