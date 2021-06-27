package uk.dansiviter.microservices.springwebflux;

import static java.nio.charset.StandardCharsets.UTF_8;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.server.WebFilter;

import reactor.core.publisher.Flux;
import uk.dansiviter.microservices.CustomException;

@Configuration
@EnableJpaRepositories("uk.dansiviter.microservices.springwebflux")
public class Config {
	@Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.H2).addScript("META-INF/create.sql").build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("uk.dansiviter.microservices");
    factory.setDataSource(dataSource());
    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

	@Bean
	WebFilter customExceptionHandler() {
		return (exchange, next) -> next.filter(exchange).onErrorResume(CustomException.class, ex -> {
			var response = exchange.getResponse();
			response.setStatusCode(HttpStatus.BAD_REQUEST);
			var buffer = response.bufferFactory().wrap(ex.getMessage().getBytes(UTF_8));
			return response.writeWith(Flux.just(buffer));
		});
	}
}
