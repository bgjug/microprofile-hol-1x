package bg.jug.microprofile.hol.gui;

import javax.ws.rs.ApplicationPath;

@ApplicationPath(Application.RESOURCE_PATH)
public class Application extends javax.ws.rs.core.Application {

    static final String RESOURCE_PATH = "/gui";
}
