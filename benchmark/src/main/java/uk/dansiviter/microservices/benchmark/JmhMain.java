package uk.dansiviter.microservices.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhMain {
	public static void main(String... args) throws RunnerException {
		var opt = new OptionsBuilder()
				.include(JmhMain.class.getPackage().getName())
				.build();
		new Runner(opt).run();
	}
}
