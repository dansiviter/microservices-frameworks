package uk.dansiviter.microservices.helidon;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import uk.dansiviter.microservices.ResponseUtil;

public class Main {
	public static void main(String... args) throws InterruptedException, ExecutionException, TimeoutException {
		ServerConfiguration configuration = ServerConfiguration.builder()
                                                   //    .bindAddress(InetAddress.getLocalHost())
                                                       .port(8080)
                                                       .build();
		Routing routing = Routing.builder()
			.get("/hello/{name}", (req, res) -> res.send(ResponseUtil.create(req.path().param("name")))).build();

		WebServer server = WebServer
                .create(configuration, routing)
                .start()
                .toCompletableFuture()
                .get(10, TimeUnit.SECONDS);

		long initializationElapsedTime = ManagementFactory.getRuntimeMXBean().getUptime();
		System.out.println("Server started on http://localhost:" + server.port() + " (and all other host addresses)"
			  + " in " + initializationElapsedTime + " milliseconds (since JVM startup).");
	}
}
