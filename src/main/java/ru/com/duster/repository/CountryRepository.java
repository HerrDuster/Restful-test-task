package ru.com.duster.repository;

import org.springframework.data.repository.CrudRepository;
import ru.com.duster.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {
}
