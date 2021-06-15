package uk.dansiviter.microservices.dropwizard;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import uk.dansiviter.microservices.Person;

public class PersonRepo extends AbstractDAO<Person> {
	public PersonRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Person findById(String name) {
		return get(name);
	}
}
