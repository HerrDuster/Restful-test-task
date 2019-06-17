package ru.com.duster.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.duster.domain.Country;
import ru.com.duster.domain.Loan;
import ru.com.duster.domain.Person;
import ru.com.duster.repository.CountryRepository;
import ru.com.duster.repository.PersonRepository;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private PersonRepository persons;

    @Autowired
    private CountryRepository countries;

    @Autowired
    private LoanService service;

    @Test
    public void whenApplyLoadThenSaveInDb() {
        Person person = this.persons.save(new Person("Petr", "Arsentev"));
        Country country = this.countries.save(new Country("Russia"));
        Loan loan = this.service.apply(new Loan("", 0D, country, person));
        List<Loan> result = this.service.getAll();
        assertTrue(result.contains(loan));
    }

    @Test
    public void whenFindByPersonThenReturnListOnlyForRerson() {
        Person person = this.persons.save(new Person("Petr", "Arsentev"));
        Country country = this.countries.save(new Country("Russia"));
        Loan loan = this.service.apply(new Loan("", 0D, country, person));
        List<Loan> result = this.service.getByPerson(person.getId());
        assertThat(result.iterator().next(), is(loan));
    }

}