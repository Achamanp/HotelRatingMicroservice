package com.lcwd.hotel.HotelService.config;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip JWT authentication for public endpoints
        String requestPath = request.getRequestURI();
        if (shouldSkipAuthentication(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwtToken = null;
        String header = request.getHeader("Authorization");
        
        // Check if we have the loggedInUser header from the gateway
        String loggedInUser = request.getHeader("loggedInUser");
        
        try {
            if (loggedInUser != null && !loggedInUser.isEmpty()) {
                // User authenticated via gateway, use the header
                username = loggedInUser;
                
                // Create authentication token without password
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
                
            } else if (header != null && header.startsWith("Bearer ")) {
                // Direct access with JWT token
                jwtToken = header.substring(7);
                jwtUtil.validateToken(jwtToken);
                username = jwtUtil.extractUsernameFromToken(jwtToken);
                
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Check if the request path should skip JWT authentication
     */
    private boolean shouldSkipAuthentication(String requestPath) {
        // List of endpoints that don't require authentication
        String[] publicEndpoints = {
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/error"
        };

        for (String endpoint : publicEndpoints) {
            if (endpoint.endsWith("**")) {
                // Handle wildcard patterns
                String basePattern = endpoint.substring(0, endpoint.length() - 2);
                if (requestPath.startsWith(basePattern)) {
                    return true;
                }
            } else if (requestPath.equals(endpoint)) {
                return true;
            }
        }
        
        return false;
    }
}
