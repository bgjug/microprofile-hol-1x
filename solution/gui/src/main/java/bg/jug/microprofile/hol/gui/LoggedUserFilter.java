package bg.jug.microprofile.hol.gui;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static bg.jug.microprofile.hol.gui.GUIResource.AUTH_COOKIE;

@WebFilter(urlPatterns = Application.RESOURCE_PATH + "/*")
public class LoggedUserFilter implements Filter {

    private static final List<String> WHITE_LIST = Arrays.asList("login", "register");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String reqURI = httpRequest.getRequestURI();

        Cookie[] cookies = httpRequest.getCookies();
        Optional<Cookie> authCookie =
                cookies == null ?
                        Optional.empty() :
                        Stream.of(cookies)
                              .filter(c -> c.getName().equals(AUTH_COOKIE))
                              .findAny();
        if (WHITE_LIST.stream().anyMatch(reqURI.toLowerCase()::contains) || authCookie.isPresent()) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {

    }
}
