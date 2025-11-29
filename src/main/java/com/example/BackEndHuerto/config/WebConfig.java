package com.example.BackEndHuerto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**") // URL pública
                .addResourceLocations("file:uploads/"); // carpeta física en tu proyecto
    }
}

//Cada vez que alguien pida:
//http://localhost:8080/uploads/...
//devuelve los archivos de la carpeta:
//uploads en el proyecto.”

