package uk.dansiviter.microservices;

import java.util.Collections;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@ApplicationScoped
public class ApplicationInitialiser extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return Collections.singleton(RestResource.class);
	}
}
