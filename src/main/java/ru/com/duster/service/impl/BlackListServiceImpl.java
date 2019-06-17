package ru.com.duster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.com.duster.domain.Person;
import ru.com.duster.repository.BlackListRepository;
import ru.com.duster.service.BlackListService;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository repository;

    @Autowired
    public BlackListServiceImpl(final BlackListRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isBlackListPerson(int personId) {
        return this.repository.findByPerson(new Person(personId)) != null;
    }
}
