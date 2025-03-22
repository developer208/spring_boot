package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper ModelMapperInstance(){
        return new ModelMapper();
    }
}