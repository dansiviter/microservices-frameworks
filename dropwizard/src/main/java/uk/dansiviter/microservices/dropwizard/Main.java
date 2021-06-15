package uk.dansiviter.microservices.dropwizard;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.dansiviter.microservices.Person;

public class Main extends Application<BaseConfig> {
	private final HibernateBundle<BaseConfig> hibernate = new HibernateBundle<BaseConfig>(Person.class) {
    @Override
    public DataSourceFactory getDataSourceFactory(BaseConfig configuration) {
        return configuration.getDataSourceFactory();
    }
};

	public static void main(String... args) throws Exception {
		new Main().run("server", "/config.yaml");
	}

	@Override
	public String getName() {
			return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<BaseConfig> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(BaseConfig configuration, Environment environment) {
		var jersey = environment.jersey();
		var repo = new PersonRepo(hibernate.getSessionFactory());

		jersey.register(new AbstractBinder(){
			@Override
			protected void configure() {
					bind(repo).to(PersonRepo.class);
			}
		});
		jersey.register(RestResource.class);
		jersey.register(CustomExceptionHandler.class);
	}
}
