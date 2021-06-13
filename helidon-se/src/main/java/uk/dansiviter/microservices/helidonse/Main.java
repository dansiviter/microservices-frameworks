package uk.dansiviter.microservices.helidonse;

import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.helidon.common.http.Http;
import io.helidon.common.reactive.Single;
import io.helidon.config.Config;
import io.helidon.media.jsonb.JsonbSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.WebServer;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

public enum Main { ;
	public static void main(String... args) {
		startServer(8080);
	}

	static Single<WebServer> startServer(int port) {
		var config = Config.create();
		var dataSource = getDataSource(config.get("dataSource"));

		// Build server with JSONP support
		var routing = Routing.builder()
				.get("/hello/error", Main::error)
				.get("/hello/{name}", Main::hello)
				.get("/people/{name}", (req, res) -> people(req, res, dataSource))
				.error(CustomException.class, Main::errorHandler)
				.build();
		var server = WebServer
			.builder(routing)
			.addMediaSupport(JsonbSupport.create())
			.config(config.get("server"))
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

	private static DataSource getDataSource(Config config) {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(config.get("jdbcUrl").asString().get());
		hikariConfig.setUsername(config.get("username").asString().get());
		hikariConfig.setPassword(config.get("password").asString().get());
		hikariConfig.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2);

		return new HikariDataSource(hikariConfig);
	}

	static void hello(ServerRequest req, ServerResponse res) {
		res.send(ResponseUtil.create(req.path().param("name")));
	}

	static void error(ServerRequest req, ServerResponse res) {
		throw new CustomException();
	}

	static void people(ServerRequest req, ServerResponse res, DataSource dataSource) {
		var name = req.path().param("name");
		person(name, dataSource).thenAccept(res::send);
	}

	private static CompletionStage<Person> person(String name, DataSource dataSource) {
		return CompletableFuture.supplyAsync(() -> {
			try (var connection = dataSource.getConnection()) {
				var statement = connection.prepareStatement("SELECT * FROM person WHERE name = ?");
				statement.setString(1, name);
				try (var rs = statement.executeQuery()) {
					rs.next();
					return new Person(rs.getString(1), rs.getInt(2));
				}
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		});
	}

	static void errorHandler(ServerRequest req, ServerResponse res, CustomException ex) {
		res.status(Http.Status.BAD_REQUEST_400).send(ex.getMessage());
	}
}
