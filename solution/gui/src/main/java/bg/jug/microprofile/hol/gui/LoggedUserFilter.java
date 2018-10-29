package bg.jug.microprofile.hol.gui;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.List;

import static bg.jug.microprofile.hol.gui.GUIResource.AUTH_COOKIE;

@Provider
@PreMatching
public class LoggedUserFilter implements ContainerRequestFilter {

    private static final List<String> WHITE_LIST = Arrays.asList("login", "register");

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String reqURI = containerRequestContext.getUriInfo().getPath();
        Cookie authCookie = containerRequestContext.getCookies().get(AUTH_COOKIE);

        if (authCookie == null && WHITE_LIST.stream().noneMatch(reqURI.toLowerCase()::contains)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
