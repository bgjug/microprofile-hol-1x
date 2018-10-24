package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/content")
@LoginConfig(authMethod = "MP-JWT", realmName = "mp-hol")
public class Application extends javax.ws.rs.core.Application {
}
