package uk.dansiviter.microservices.helidonse;

import java.lang.management.ManagementFactory;

import io.helidon.common.http.Http;
import io.helidon.common.reactive.Single;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.WebServer;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

public enum Main { ;
	public static void main(String... args) {
		startServer(8080);
	}

	static Single<WebServer> startServer(int port) {
		var routing = Routing.builder()
				.get("/hello/error", Main::error)
				.get("/hello/{name}", Main::hello)
				.error(CustomException.class, Main::errorHandler)
				.build();
		var server = WebServer
			.builder(routing)
			.port(port)
			.build();
		var webserver = server.start();
		webserver.thenAccept(ws -> {
			long initializationElapsedTime = ManagementFactory.getRuntimeMXBean().getUptime();
			System.out.printf("Server started on http://localhost:%d in %d milliseconds (since JVM startup).%n", ws.port(), initializationElapsedTime);
			ws.whenShutdown().thenRun(() -> System.out.println("WEB server is DOWN. Good bye!"));
		}).exceptionallyAccept(t -> {
			System.err.printf("Startup failed: %s%n", t.getMessage());
			t.printStackTrace(System.err);
		});

		return webserver;
	}

	static void hello(ServerRequest req, ServerResponse res) {
		res.send(ResponseUtil.create(req.path().param("name")));
	}

	static void error(ServerRequest req, ServerResponse res) {
		throw new CustomException();
	}

	static void errorHandler(ServerRequest req, ServerResponse res, CustomException ex) {
		res.status(Http.Status.BAD_REQUEST_400).send(ex.getMessage());
	}
}
