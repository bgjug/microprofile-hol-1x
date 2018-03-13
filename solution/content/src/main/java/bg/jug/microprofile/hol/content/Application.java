package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/content")
@LoginConfig(authMethod = "MP-JWT", realmName = "MP-HOL-JWT")
public class Application extends javax.ws.rs.core.Application {
}
