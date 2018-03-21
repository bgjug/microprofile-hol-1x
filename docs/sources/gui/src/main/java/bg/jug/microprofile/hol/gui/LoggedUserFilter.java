package bg.jug.microprofile.hol.gui;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class LoggedUserFilter implements Filter {

    private static final List<String> WHITE_LIST = Arrays.asList("/css/", "/js/", "/img/", "login", "register");

    @Inject
    private UserContext userContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String reqURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (userContext.getLoggedUser() != null || WHITE_LIST.stream().anyMatch(reqURI.toLowerCase()::contains)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.html");
        }
    }

    @Override
    public void destroy() {

    }
}
