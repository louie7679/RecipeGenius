package org.ascending.training.filter;

import org.ascending.training.service.JWTService;
import org.ascending.training.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private SystemUserService systemUserService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Set<String> ALLOWED_PATHS = new HashSet<>(Arrays.asList("", "/login", "logout", "register"));
    private static final Set<String> IGNORED_PATH = new HashSet<>(Arrays.asList("/auth"));
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
