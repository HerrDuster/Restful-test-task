package ru.com.duster.repository;

import org.springframework.data.repository.CrudRepository;
import ru.com.duster.domain.Loan;
import ru.com.duster.domain.Person;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
    List<Loan> findByPerson(Person person);
}
