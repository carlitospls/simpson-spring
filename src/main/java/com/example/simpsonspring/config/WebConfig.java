package com.example.simpsonspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración extra de Spring MVC.
 * Aquí solo la usamos para que "/" lleve a la pantalla principal.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Cualquiera que entre en localhost:8080/ es redirigido a /personajes,
        // sin necesidad de escribir un controlador para eso.
        registry.addRedirectViewController("/", "/personajes");
    }
}