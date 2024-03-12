package com.formapp.damnjan.config;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.utils.CustomAuditAware;
import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class FormAppConfig {

    @Bean
    public PropertyValidator formValidator() {
        return new PropertyValidator();
    }

    @Bean
    public AuditorAware<UserEntity> auditorAware(){
        return new CustomAuditAware();
    }
}
