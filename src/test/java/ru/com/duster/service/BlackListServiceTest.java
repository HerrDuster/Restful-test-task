package ru.com.duster.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.duster.domain.BlackList;
import ru.com.duster.domain.Person;
import ru.com.duster.repository.BlackListRepository;
import ru.com.duster.repository.PersonRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackListServiceTest {

    @Autowired
    private PersonRepository persons;

    @Autowired
    private BlackListRepository blacklists;

    @Autowired
    private BlackListService service;

    @Test
    public void whenPersonInBlackListThenReturnTrue() {
        Person person = this.persons.save(new Person("Petr", "Arsentev"));
        this.blacklists.save(new BlackList(person));
        boolean result = this.service.isBlackListPerson(person.getId());
        assertTrue(result);
    }

    @Test
    public void whenBlackListEmptyThenAnyPersonNotIn() {
        Person person = this.persons.save(new Person("Petr", "Arsentev"));
        boolean result = this.service.isBlackListPerson(person.getId());
        assertFalse(result);
    }
}