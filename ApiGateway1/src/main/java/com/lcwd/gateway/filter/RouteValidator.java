package com.lcwd.gateway.filter;

import java.util.List;
import org.springframework.http.server.reactive.ServerHttpRequest; // CHANGED: Added 'reactive'
import org.springframework.stereotype.Component;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
        "/api/users/register",
        "/api/users/login",
        "/api/users/home",
        "/auth/token",
        "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().startsWith(uri)); // Also changed to startsWith
}