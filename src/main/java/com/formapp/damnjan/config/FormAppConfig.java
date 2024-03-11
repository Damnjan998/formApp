package com.formapp.damnjan.config;

import com.formapp.damnjan.validators.FormValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormAppConfig {

    @Bean
    public FormValidator formValidator() {
        return new FormValidator();
    }
}
