package uk.dansiviter.microservices.helidon;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutionException;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import uk.dansiviter.microservices.ResponseUtil;

public class Main {
	public static void main(String... args) throws InterruptedException, ExecutionException {
		Routing routing = Routing.builder()
			.get("/hello/{name}", (req, res) -> res.send(ResponseUtil.create(req.path().param("name")))).build();
		WebServer server = WebServer.builder()
			  .port(8080)
			  .routing(routing)
			  .build()
			  .start()
			  .toCompletableFuture()
			  .get();

		long initializationElapsedTime = ManagementFactory.getRuntimeMXBean().getUptime();
		System.out.println("Server started on http://localhost:" + server.port() + " (and all other host addresses)"
			  + " in " + initializationElapsedTime + " milliseconds (since JVM startup).");
	}
}
