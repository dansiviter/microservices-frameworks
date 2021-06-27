package uk.dansiviter.microservices.benchmark;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class RestBenchmarks {
	private static final URI baseUri = URI.create("http://host.docker.internal:8080/");

	@State(Scope.Thread)
	public static class RestState {
		HttpClient client;
		HttpRequest helloRequest;
		HttpRequest peopleRequest;

		@Setup
    public void setupTrial() {
			client = HttpClient.newHttpClient();
		}

		@Setup(Level.Iteration)
    public void setupItr() {
			helloRequest = HttpRequest.newBuilder()
						.uri(baseUri.resolve("/hello/bob"))
						.build();
			peopleRequest = HttpRequest.newBuilder()
						.uri(baseUri.resolve("/people/Lois"))
						.build();
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Threads(Threads.MAX)
	@Measurement(timeUnit = TimeUnit.MICROSECONDS)
	public void hello(RestState state, Blackhole blackhole)
	throws IOException, InterruptedException
	{
		var response = state.client.send(state.helloRequest, BodyHandlers.ofString());
		assert response.statusCode() == 200;
		blackhole.consume(response.body());
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Threads(Threads.MAX)
	@Measurement(timeUnit = TimeUnit.MICROSECONDS)
	public void testMethod(RestState state, Blackhole blackhole)
	throws IOException, InterruptedException
	{
		var response = state.client.send(state.peopleRequest, BodyHandlers.ofByteArray());
		assert response.statusCode() == 200;
		blackhole.consume(response.body());
	}
}
