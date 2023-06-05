package com.enigma.configuration;

import com.enigma.utils.AdminOnlyEndpointsConfig;
import com.enigma.utils.JwtUtil;
import com.enigma.utils.constants.Role;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class SimpleInterceptor implements HandlerInterceptor {
    private final Map<String, List<String>> ADMIN_ONLY_URIS;
    private static final List<String> OPEN_ENDPOINTS = Arrays.asList("/api/v1/login", "/api/v1/login-admin", "/api/v1/register", "/swagger-ui/index.html", "/api/v1/payment/checkout");

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public SimpleInterceptor(AdminOnlyEndpointsConfig config) {
        this.ADMIN_ONLY_URIS = config.getAdminOnlyUris();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        System.out.println(requestUrl);

        if (OPEN_ENDPOINTS.contains(requestUrl) || requestUrl.contains("/swagger-ui/") || requestUrl.contains("/v3")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null) throw new RuntimeException("Token Needed");
        String[] bearerToken = token.split(" ");
        if (!jwtUtil.validateToken(bearerToken[1])) {
            throw new RuntimeException("Invalid Token");
        }

        Role role = jwtUtil.getRoleFromToken(bearerToken[1]);

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        for (String adminOnlyURI : ADMIN_ONLY_URIS.keySet()) {
            if (requestURI.startsWith(adminOnlyURI) && ADMIN_ONLY_URIS.get(adminOnlyURI).contains(requestMethod) && !role.equals(Role.Admin)) {
                throw new RuntimeException("Not authorized");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("Post handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("complete");
    }
}
