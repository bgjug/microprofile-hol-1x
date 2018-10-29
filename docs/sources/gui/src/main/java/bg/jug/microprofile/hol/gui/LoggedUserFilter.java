package bg.jug.microprofile.hol.gui;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Provider
@PreMatching
public class LoggedUserFilter implements ContainerRequestFilter {

    private static final List<String> WHITE_LIST = Arrays.asList("login", "register");

    public void filter(ContainerRequestContext containerRequestContext) {
        UserContext userContext = CDI.current().select(UserContext.class).get();
        String reqURI = containerRequestContext.getUriInfo().getPath();

        if (userContext.getLoggedUser() == null && WHITE_LIST.stream().noneMatch(reqURI.toLowerCase()::contains)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
