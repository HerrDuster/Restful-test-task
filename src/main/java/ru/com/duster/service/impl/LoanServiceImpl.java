package ru.com.duster.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.com.duster.repository.LoanRepository;
import ru.com.duster.domain.Loan;
import ru.com.duster.domain.Person;
import ru.com.duster.service.LoanService;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository repository;

    @Autowired
    public LoanServiceImpl(final LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan apply(final Loan loan) {
        return this.repository.save(loan);
    }

    @Override
    public List<Loan> getAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    @Override
    public List<Loan> getByPerson(int personId) {
        return this.repository.findByPerson(new Person(personId));
    }
}
