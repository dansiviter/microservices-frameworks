package uk.dansiviter.microservices.helidonmp;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uk.dansiviter.microservices.Person;

@ApplicationScoped
public class PersonRepo {
	@PersistenceContext
	private EntityManager em;

	public Optional<Person> get(String name) {
		return Optional.ofNullable(this.em.find(Person.class, name));
	}
}
