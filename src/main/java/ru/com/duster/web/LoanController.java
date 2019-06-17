package ru.com.duster.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.com.duster.domain.Loan;
import ru.com.duster.service.BlackListService;
import ru.com.duster.service.LoanService;
import ru.com.duster.web.forms.Error;
import ru.com.duster.web.forms.Result;
import ru.com.duster.web.forms.Success;

import java.util.List;


@RestController
public class LoanController {

    private final LoanService loans;

    private final BlackListService blacklists;

    @Autowired
    public LoanController(final LoanService loans, final BlackListService blacklists) {
        this.loans = loans;
        this.blacklists = blacklists;
    }

    @PostMapping("/")
    public Result apply(@RequestBody Loan loan) {
        final Result result;
        if (!this.blacklists.isBlackListPerson(loan.getPerson().getId())) {
            result = new Success<Loan>(
                    this.loans.apply(loan)
            );
        } else {
            result = new Error(String.format("User %s in blacklist", loan.getPerson().getId()));
        }
        return result;
    }

    @GetMapping("/")
    public List<Loan> getAll() {
        return this.loans.getAll();
    }

    @GetMapping("/{personId}")
    public List<Loan> findByPersonId(@PathVariable int personId) {
        return this.loans.getByPerson(personId);
    }
}