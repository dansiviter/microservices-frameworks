package uk.dansiviter.microservices.micronaut;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.dansiviter.microservices.Person;

@Repository
public interface PersonRepo extends CrudRepository<Person, String> { }
