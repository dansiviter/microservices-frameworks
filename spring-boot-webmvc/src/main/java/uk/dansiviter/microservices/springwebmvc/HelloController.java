package uk.dansiviter.microservices.springwebmvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

@RestController
public class HelloController {
	@GetMapping(path = "/hello/error")
	public void error() {
		throw new CustomException();
	}

	@GetMapping(path = "/hello/{name}")
	public String hello(@PathVariable("name") String name) {
		return ResponseUtil.create(name);
	}
}
