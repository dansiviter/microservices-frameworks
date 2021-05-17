package uk.dansiviter.microservices.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

public class JmhMain {
	public static void main(String... args) throws RunnerException {
		var opt = new OptionsBuilder()
				.include(JmhMain.class.getPackage().getName())
				.verbosity(VerboseMode.EXTRA)
				.shouldFailOnError(true)
				.resultFormat(ResultFormatType.JSON)
				.result("results.json")
				.build();
		new Runner(opt).run();
	}
}
