package uk.dansiviter.microservices.micronaut;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import uk.dansiviter.microservices.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, String> { }
