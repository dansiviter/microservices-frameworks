package uk.dansiviter.microservices;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/hello")
public class ApplicationInitialiser extends Application {
//....
}
