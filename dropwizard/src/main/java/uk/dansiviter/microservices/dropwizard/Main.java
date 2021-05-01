package uk.dansiviter.microservices.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<Configuration> {
	public static void main(String... args) throws Exception {
		new Main().run("server");
	}

	@Override
	public String getName() {
			return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
			// nothing to do yet
	}

	@Override
	public void run(Configuration configuration, Environment environment) {
		var jersey = environment.jersey();
		jersey.register(RestResource.class);
		jersey.register(CustomExceptionHandler.class);
	}
}
