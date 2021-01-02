package uk.dansiviter.microservices;

public enum ResponseUtil { ;
	public static String create(String name) {
		return new StringBuilder("Hello ").append(name).append('!').toString();
	}
}
