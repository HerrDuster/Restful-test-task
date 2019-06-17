package ru.com.duster.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.com.duster.domain.Country;
import ru.com.duster.domain.Loan;
import ru.com.duster.domain.Person;
import ru.com.duster.service.BlackListService;
import ru.com.duster.service.LimitService;
import ru.com.duster.service.LoanService;
import ru.com.duster.web.forms.Error;
import ru.com.duster.web.forms.Success;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlackListService blacks;

    @MockBean
    private LoanService loans;

    @MockBean
    private LimitService limit;

    @Test
    public void whenPersonNotInBlackListThenApplyLoan() throws Exception {
        List<Loan> list = Collections.singletonList(
                new Loan("test", 1D, new Country("Russia"), new Person("Petr", "Arsentev"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getAll()).willReturn(list);
        this.mvc.perform(
                get("/").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    public void whenLoadThenApplyLoan() throws Exception {
        List<Loan> list = Collections.singletonList(
                new Loan("test", 1D, new Country("Russia"), new Person("Petr", "Arsentev"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getByPerson(0)).willReturn(list);
        this.mvc.perform(
                get("/0").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    public void whenApplyThenSave() throws Exception {
        Loan loan = new Loan("test", 1D, new Country("Russia"), new Person("Petr", "Arsentev"));
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(false);
        given(this.loans.apply(loan)).willReturn(loan);
        this.mvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                loan
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Success<Loan>(loan)))
        );
    }

    @Test
    public void whenInBlacklistThenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(true);
        this.mvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                new Loan("test", 1D, new Country("Russia"), new Person("Petr", "Arsentev"))
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Error("User 0 in blacklist")))
        );
    }

}