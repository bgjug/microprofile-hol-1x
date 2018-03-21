package bg.jug.microprofile.hol.authors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/authors")
@ApplicationScoped
public class Application extends javax.ws.rs.core.Application {
}
