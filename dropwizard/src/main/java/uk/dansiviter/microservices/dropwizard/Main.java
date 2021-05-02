package uk.dansiviter.microservices.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<Configuration> {
	public static void main(String... args) throws Exception {
		new Main().run("server", "/config.yaml");
	}

	@Override
	public String getName() {
			return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
	}

	@Override
	public void run(Configuration configuration, Environment environment) {
		var jersey = environment.jersey();
		jersey.register(RestResource.class);
		jersey.register(CustomExceptionHandler.class);
	}
}
