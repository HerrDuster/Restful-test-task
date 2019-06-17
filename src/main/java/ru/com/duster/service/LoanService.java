package ru.com.duster.service;

import ru.com.duster.domain.Loan;

import java.util.List;

public interface LoanService {
    Loan apply(Loan loan);

    List<Loan> getAll();

    List<Loan> getByPerson(int personId);
}
