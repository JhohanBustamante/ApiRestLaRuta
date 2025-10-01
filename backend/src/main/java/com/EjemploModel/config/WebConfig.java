package com.EjemploModel.config;

// WebConfig.java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sirve http://<host>/images/** desde la carpeta local "uploads/"
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/") // ruta relativa al working dir
                .setCachePeriod(3600);
    }
}