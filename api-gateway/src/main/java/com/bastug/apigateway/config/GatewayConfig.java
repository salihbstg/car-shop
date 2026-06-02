package com.bastug.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service",
                        r -> r.path("/auth/**")
                                .uri("http://localhost:8088"))
                .route("car-service",
                        r -> r.path("/api/v1/cars/**")
                                .uri("http://localhost:8082"))
                .route("customer-service",
                        r->r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081")
                )
                .route("media-service",
                        r->r.path("/api/media/**")
                                .uri("http://localhost:8083")
                )
                .build();
    }
}
