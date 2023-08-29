package org.ascending.training.filter;

import io.jsonwebtoken.Claims;
import org.ascending.training.model.SystemUser;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private static final Set<String> IGNORED_PATH = new HashSet<>(Arrays.asList("/auth", "/signup"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Check null or not before use. Initialize dependency first then inject.
        if (jwtService == null) {
            // Once injected, it will inject all dependencies required by various filter instance.
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletRequest.getServletContext());
        }

        logger.info("Start to do authorization");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        int statusCode = authorization(req);
        if (statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendError(statusCode);
        }
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        if (IGNORED_PATH.contains(uri)) {
            return HttpServletResponse.SC_ACCEPTED;
        }

        String verb = req.getMethod();

        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?)", "");
            if (token == null || token.isEmpty()){
                return statusCode;
            }

            Claims claims = jwtService.decryptToken(token);
            logger.info("===== after paring JWT token, claims.getId()={}", claims.getId());

            if (claims.getId() != null) {
                SystemUser u = systemUserService.getSystemUserById(Long.valueOf(claims.getId()));
                 if (u == null) {
                    return statusCode;
                 }
            }

            String allowedResources = "";
            switch (verb) {
                case "GET": allowedResources = (String) claims.get("allowedResources");
                    break;
                case "POST":
                case "PATCH": allowedResources = (String) claims.get("allowedCreateResources");
                    break;
                case "PUT": allowedResources = (String) claims.get("allowedUpdateResources");
                    break;
                case "DELETE": allowedResources = (String) claims.get("allowedDeleteResources");
                    break;
            }

            for(String s : allowedResources.split(",")) {
                String uri_trim = uri.trim().toLowerCase();
                String s_trim = s.trim().toLowerCase();
                logger.info("url - {}; s - {}; {} - {}", uri_trim, "\"" + s_trim + "\"", uri_trim.startsWith(s_trim), !s_trim.equals(""));
                if (!s_trim.equals("") && uri_trim.startsWith(s_trim)) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
        } catch (Exception e) {
            logger.info("Cannot get token");
            e.printStackTrace();
        }
        return statusCode;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
