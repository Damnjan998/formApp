package com.formapp.damnjan.config;

import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormAppConfig {

    @Bean
    public PropertyValidator formValidator() {
        return new PropertyValidator();
    }
}
