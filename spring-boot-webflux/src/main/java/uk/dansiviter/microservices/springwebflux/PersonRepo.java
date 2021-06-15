package uk.dansiviter.microservices.springwebflux;

import org.springframework.data.repository.CrudRepository;

import uk.dansiviter.microservices.Person;

public interface PersonRepo extends CrudRepository<Person, String> { }
