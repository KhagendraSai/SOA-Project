package com.school.gateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtGatewayFilter
        extends AbstractGatewayFilterFactory<Object> {

    private static final Logger log = LoggerFactory.getLogger(JwtGatewayFilter.class);

    @Value("${jwt.secret:12345678901234567890123456789012345678901234567890}")
    private String secret;

    public JwtGatewayFilter() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            String path =
                    exchange.getRequest()
                            .getURI()
                            .getPath();

            if (path.startsWith("/api/auth")) {
                return chain.filter(exchange);
            }

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("Authorization");

            if (authHeader == null || !authHeader.trim().startsWith("Bearer ")) {
                log.warn("Unauthorized access attempt to {}: Missing or invalid Authorization header", path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.trim().substring(7).trim();

            if (token.isEmpty() || token.equalsIgnoreCase("null") || token.equals("{{token}}")) {
                log.warn("Unauthorized access attempt to {}: Token is empty or uninitialized placeholder", path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try {
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token);

                return chain.filter(exchange);

            } catch (Exception e) {
                log.error("JWT validation error on {}: {}", path, e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
}
