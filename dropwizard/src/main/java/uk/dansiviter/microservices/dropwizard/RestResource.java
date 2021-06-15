package uk.dansiviter.microservices.dropwizard;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.dropwizard.hibernate.UnitOfWork;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

@Path("/")
public class RestResource {
	@Inject
	private PersonRepo repo;

	@GET
	@Path("/hello/{name}")
	public String hello(@PathParam("name") String name) {
		return ResponseUtil.create(name);
	}

	@GET
	@Path("/hello/error")
	public String error() {
		throw new CustomException();
	}

	@GET
	@Path("/people/{name}")
	@UnitOfWork
	@Produces(APPLICATION_JSON)
	public Person people(@PathParam("name") String name) {
		return this.repo.findById(name);
	}
}
