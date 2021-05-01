package uk.dansiviter.microservices.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {
	public static void main(String... args) {
		var vertx = Vertx.vertx(new VertxOptions());
		vertx.deployVerticle(new Server());
	}
}
