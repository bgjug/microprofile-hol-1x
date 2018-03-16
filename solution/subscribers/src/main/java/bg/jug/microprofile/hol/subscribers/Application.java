package bg.jug.microprofile.hol.subscribers;

import javax.ws.rs.ApplicationPath;

import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/")
@LoginConfig(authMethod = "MP-JWT")
public class Application extends javax.ws.rs.core.Application {
}
