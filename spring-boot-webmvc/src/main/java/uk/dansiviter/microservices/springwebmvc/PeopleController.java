package uk.dansiviter.microservices.springwebmvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.dansiviter.microservices.Person;

@RestController
public class PeopleController {
	@Autowired
	private PersonRepo repo;

	@GetMapping(path = "/people/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  Optional<Person> people(@PathVariable("name") String name) {
		return repo.findById(name);
	}
}
