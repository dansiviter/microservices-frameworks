package uk.dansiviter.microservices.quarkus;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import uk.dansiviter.microservices.Person;

@ApplicationScoped
public class PersonRepo implements PanacheRepositoryBase<Person, String> { }
