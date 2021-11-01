package uk.dansiviter.microservices.micronaut;

import javax.persistence.Entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.runtime.Micronaut;

@Introspected(packages = "uk.dansiviter.microservices", includedAnnotations = Entity.class)
public class Main {
	public static void main(String... args) {
		Micronaut.run(Main.class, args);
	}
}
